package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.PieceColour;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

/**
 * A grid pane representing a player's hand.
 */
public class PlayerHandGridPane extends GridPane {

    /**
     * The game model.
     */
    private Game game;

    /**
     * The colour of the pieces in the hand.
     */
    private PieceColour pieceColour;

    /**
     * The image to use for black pieces.
     */
    private Image blackImage;

    /**
     * The image to use for white pieces.
     */
    private Image whiteImage;

    /**
     * Initialize the player hand grid pane.
     * @param game The game model.
     * @param pieceColour The colour of the pieces in the hand.
     */
    void initialize(Game game, PieceColour pieceColour) {
        this.game = game;
        this.pieceColour = pieceColour;

        for (Node node : this.getChildren()) {
            ImageView imageView = (ImageView) node;
            imageView.setOnDragDetected(new MouseEventHandler(imageView));
            imageView.setOnDragDone(new DragEventHandler(imageView));
        }

        blackImage = new Image("/edu/monash/images/piece-black.png");
        whiteImage = new Image("/edu/monash/images/piece-white.png");
    }

    /**
     * Initialize the initial state of the player's hand.
     */
    void initState() {
        // Set all ImageView instances to the initial image
        for (int row = 0; row < getRowCount(); row++) {
            for (int col = 0; col < getColumnCount(); col++) {
                ImageView imageView = (ImageView) getChildren().get(col + row * getColumnCount());
                Image image = pieceColour == PieceColour.BLACK ? blackImage : whiteImage;
                imageView.setImage(image);
            }
        }
    }

    /**
     * Undo the last move made by the player.
     */
    void undo() {
        boolean isUndo = false;
        int row = getRowCount() - 1;
        int col = getColumnCount() - 1 ;

        while (!isUndo && row >= 0) {
            ImageView imageView = (ImageView) getChildren().get(col + row * getColumnCount());
            if (imageView.getImage() == null) {
                Image image = pieceColour == PieceColour.BLACK ? blackImage : whiteImage;
                imageView.setImage(image);
                isUndo = true;
            }
            row -= 1;
        }
    }

    /**
     * Refresh the view.
     */
    void updateView() {
        // Set all pieces to the initial image.
        initState();

        int MAX_PIECES = 9;
        int playerHand = game.getPlayer(pieceColour).getPiecesOnHand();

        // Remove pieces from the view based on the player's hand.
        for (int row = 0; row < MAX_PIECES - playerHand; row++) {
            ImageView imageView = (ImageView) getChildren().get(row);
            imageView.setImage(null);
        }
    }

    /**
     * A drag event handler for the image views in player hand grid pane.
     */
    private class MouseEventHandler implements EventHandler<MouseEvent> {

        /**
         * The image view that the event handler is attached to.
         */
        private final ImageView imageView;

        /**
         * Initialize the mouse event handler.
         * @param imageView The image view that the event handler is attached to.
         */
        MouseEventHandler(ImageView imageView) {
            this.imageView = imageView;
        }

        /**
         * Handle the mouse event.
         * @param event The mouse event occurred.
         */
        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType() == MouseEvent.DRAG_DETECTED)
                onDragDetectedHandler(event);
        }

        /**
         * A drag detected event handler for the player hand grid pane.
         * @param event The event.
         */
        private void onDragDetectedHandler(MouseEvent event) {
            if (imageView.getImage() == null || game.getPlayer().getPieceColour() != pieceColour)
                return;

            Dragboard dragboard = imageView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            dragboard.setContent(content);

            event.consume();
        }

    }

    /**
     * A drag event handler for the image views in player hand grid pane.
     * @param imageView The image view that the event handler is attached to.
     */
    private record DragEventHandler(ImageView imageView) implements EventHandler<DragEvent> {

        /**
         * Handle the drag event.
         * @param event The drag event which occurred.
         */
        @Override
        public void handle(DragEvent event) {
            if (event.getEventType() == DragEvent.DRAG_DONE)
                onDragDoneHandler(event);
        }

        /**
         * A drag done event handler for the image view.
         * @param event The event.
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