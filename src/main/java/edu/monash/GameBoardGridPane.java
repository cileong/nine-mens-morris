package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.PieceColour;
import edu.monash.game.Position;
import edu.monash.game.actions.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

/**
 * A grid pane view representing the game board.
 */
public class GameBoardGridPane extends GridPane {

    /**
     * The game model.
     */
    private Game game;

    /**
     * The image used to represent a black piece.
     */
    private Image blackImage;

    /**
     * The image used to represent a white piece.
     */
    private Image whiteImage;

    /**
     * A mapping from the board position to the position ID in the game model.
     */
    private static final Integer[][] boardMapping = {
            {    0, null, null,    1, null, null,    2 },
            { null,    8, null,    9, null,   10, null },
            { null, null,   16,   17,   18, null, null },
            {    7,   15,   23, null,   19,   11,    3 },
            { null, null,   22,   21,   20, null, null },
            { null,   14, null,   13, null,   12, null },
            {    6, null, null,    5, null, null,    4 }
    };

    /**
     * Initialize the game board grid pane.
     * @param game The game model.
     * @param viewController The view controller (parent).
     */
    void initialize(Game game, ViewController viewController) {
        this.game = game;

        for (Node node : this.getChildren()) {
            ImageView imageView = (ImageView) node;

            EventHandler<MouseEvent> mouseEventHandler = new MouseEventHandler(game, imageView, viewController);
            imageView.setOnMouseClicked(mouseEventHandler);
            imageView.setOnDragDetected(mouseEventHandler);

            EventHandler<DragEvent> dragEventHandler = new DragEventHandler(game, imageView);
            imageView.setOnDragOver(dragEventHandler);
            imageView.setOnDragDropped(dragEventHandler);
            imageView.setOnDragDone(dragEventHandler);
        }
        blackImage = new Image("/edu/monash/images/piece-black.png");
        whiteImage = new Image("/edu/monash/images/piece-white.png");
    }

    /**
     * Given the row and column on the game board,
     * return the position ID in the game model.
     *
     * @param x The column.
     * @param y The row.
     * @return The position ID in the game model.
     */
    static Integer getPositionId(Integer x, Integer y) {
        try {
            return boardMapping[y][x];
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Initialize the game board grid pane to the initial state.
     */
    void initState() {
        for (Node node : this.getChildren()) {
            ImageView imageView = (ImageView) node;
            imageView.setImage(null);
        }
    }

    /**
     * Undo the last action.
     * @return Undo the previous action on the view level.
     */
    boolean undo() {
        UndoAction action = new UndoAction(game);
        boolean executed = game.execute(action);

        boolean isRemoveAction = false;
        if (executed) {
            if (action.getFrom() != null) {
                ImageView removeImageView = getImageViewByPositionId(action.getFrom());
                Image image = removeImageView.getImage();
                removeImageView.setImage(null);

                if (action.getTo() != null) {
                    ImageView addImageView = getImageViewByPositionId(action.getTo());
                    addImageView.setImage(image);
                }
            } else {
                ImageView addImageView = getImageViewByPositionId(action.getTo());
                Image image = game.getOpponent().getPieceColour() == PieceColour.BLACK ? blackImage : whiteImage;
                addImageView.setImage(image);
                isRemoveAction = true;
            }
        }
        return isRemoveAction;
    }

    /**
     * Update the view to reflect the current state of the game model.
     */
    void updateView() {
        initState();
        for (Integer[] rows : boardMapping) {
            for (Integer positionId : rows) {
                Position position = game.getBoard().getPosition(positionId);
                if (position != null) {
                    ImageView imageView = getImageViewByPositionId(positionId);
                    imageView.setImage(getImageByPieceColour(position.getPiece()));
                }
            }
        }
    }

    /**
     * Get the piece image by the piece colour.
     * @param pieceColour The piece colour.
     * @return The piece image.
     */
    private Image getImageByPieceColour(PieceColour pieceColour) {
        if (pieceColour == null)
            return null;

        return pieceColour == PieceColour.BLACK ? blackImage : whiteImage;
    }

    /**
     * Get the image view by the position ID.
     * @param positionId The position ID.
     * @return The image view.
     */
    ImageView getImageViewByPositionId(int positionId) {
        for (Node node : getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                Integer x = GridPane.getColumnIndex(imageView);
                Integer y = GridPane.getRowIndex(imageView);

                if (x != null && y != null && getPositionId(x, y) == positionId) {
                    return imageView;
                }
            }
        }
        return null; // ImageView not found for the given position ID
    }

    /**
     * The handler used for handling mouse events in the game board.
     * @param game The game model.
     * @param imageView The image view.
     * @param viewController The view controller.
     */
    private record MouseEventHandler(Game game, ImageView imageView, ViewController viewController) implements EventHandler<MouseEvent> {

        /**
         * Handle the incoming mouse event.
         * @param event the event which occurred
         */
        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType() == MouseEvent.DRAG_DETECTED)
                onDragDetectedHandler(event);
            else if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
                onMouseClickedHandler(event);
        }

        /**
         * Handle the mouse clicked event.
         * @param event The mouse clicked event.
         */
        private void onMouseClickedHandler(MouseEvent event) {
            if (imageView.getImage() == null)
                return;

            Integer fromId = getPositionId(
                    getColumnIndex(imageView),
                    getRowIndex(imageView)
            );

            if (game.getBoard().hasNoValidMove()){
                viewController.showDrawDialog();
            }

            Action action = new RemoveAction(game.getPlayer(), fromId);
            boolean executed = game.execute(action);
            if (executed)
                imageView.setImage(null);

            if (game.getPlayer().hasLost() || game.getOpponent().hasLost()){
                viewController.showGameWonDialog();
            }

            event.consume();
        }

        /**
         * Handle the drag detected event.
         * @param event The drag detected event.
         */
        private void onDragDetectedHandler(MouseEvent event) {
            if (imageView.getImage() == null)
                return;

            Integer sourceId = getPositionId(
                    getColumnIndex(imageView),
                    getRowIndex(imageView)
            );

            Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            if (sourceId != null)
                content.putString(String.valueOf(sourceId));
            db.setContent(content);

            event.consume();
        }

    }

    /**
     * The handler used for handling drag events in the game board.
     * @param game The game model.
     * @param imageView The image view involved in the drag event.
     */
    private record DragEventHandler(Game game, ImageView imageView) implements EventHandler<DragEvent> {

        /**
         * Handle the incoming drag event.
         * @param event The event which occurred.
         */
        @Override
        public void handle(DragEvent event) {
            if (event.getEventType() == DragEvent.DRAG_OVER)
                onDragOverHandler(event);
            else if (event.getEventType() == DragEvent.DRAG_DROPPED)
                onDragDroppedHandler(event);
            else if (event.getEventType() == DragEvent.DRAG_DONE)
                onDragDoneHandler(event);
        }

        /**
         * Handle the drag done event.
         * @param event The drag done event.
         */
        private void onDragOverHandler(DragEvent event) {
            if (event.getTransferMode() != TransferMode.MOVE || !event.getGestureSource().equals(imageView))
                event.acceptTransferModes(TransferMode.MOVE);

            event.consume();
        }

        /**
         * Handle the drag dropped event.
         * @param event The drag dropped event.
         */
        private void onDragDroppedHandler(DragEvent event) {
            if (event.getTransferMode() != TransferMode.MOVE)
                return;

            Dragboard db = event.getDragboard();

            Integer sourceId = db.hasString() ? Integer.parseInt(db.getString()) : null;
            Integer destinationId = getPositionId(
                    getColumnIndex(imageView),
                    getRowIndex(imageView)
            );

            Action action = sourceId != null
                    ? new MoveAction(game.getPlayer(), sourceId, destinationId)
                    : new PlaceAction(game.getPlayer(), destinationId);
            boolean executed = game.execute(action);

            if (executed)
                imageView.setImage(db.getImage());

            ClipboardContent content = new ClipboardContent();
            content.putString(Boolean.toString(executed));
            db.setContent(content);

            event.setDropCompleted(true);
            event.consume();
        }

        /**
         * Handle the drag done event.
         * @param event The drag done event.
         */
        private void onDragDoneHandler(DragEvent event) {
            if (event.getTransferMode() != TransferMode.MOVE)
                return;

            Dragboard db = event.getDragboard();
            boolean executed = Boolean.parseBoolean(db.getString());
            if (executed)
                imageView.setImage(null);

            event.consume();
        }

    }
}
