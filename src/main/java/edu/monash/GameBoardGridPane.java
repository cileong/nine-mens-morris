package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.actions.Action;
import edu.monash.game.actions.MoveAction;
import edu.monash.game.actions.PlaceAction;
import edu.monash.game.actions.RemoveAction;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

public class GameBoardGridPane extends GridPane {

    private Game game;

    private static final Integer[][] boardMapping = {
            {0, null, null, 1, null, null, 2},
            {null, 8, null, 9, null, 10, null},
            {null, null, 16, 17, 18, null, null},
            {7, 15, 23, null, 19, 11, 3},
            {null, null, 22, 21, 20, null, null},
            {null, 14, null, 13, null, 12, null},
            {6, null, null, 5, null, null, 4}
    };

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

    }

    static Integer getPositionId(Integer x, Integer y) {
        try {
            return boardMapping[y][x];
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    void initState(){
        for (Node node : this.getChildren()) {
            ImageView imageView = (ImageView) node;
            imageView.setImage(null);
        }
    }

    private record MouseEventHandler(Game game, ImageView imageView, ViewController viewController) implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType() == MouseEvent.DRAG_DETECTED)
                onDragDetectedHandler(event);
            else if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
                onMouseClickedHandler(event);
        }

        private void onMouseClickedHandler(MouseEvent event) {
            if (imageView.getImage() == null)
                return;

            Integer fromId = getPositionId(
                    getColumnIndex(imageView),
                    getRowIndex(imageView)
            );

            Action action = new RemoveAction(game.getPlayer(), fromId);
            boolean executed = game.execute(action);
            if (executed)
                imageView.setImage(null);

            if (game.getPlayer().hasLost() || game.getOpponent().hasLost()){
                viewController.showGameWonDialog();
            }
            else if (game.getBoard().hasNoValidMove()){
                viewController.showDrawDialog();
            }

            event.consume();
        }

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

    private record DragEventHandler(Game game, ImageView imageView) implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            if (event.getEventType() == DragEvent.DRAG_OVER)
                onDragOverHandler(event);
            else if (event.getEventType() == DragEvent.DRAG_DROPPED)
                onDragDroppedHandler(event);
            else if (event.getEventType() == DragEvent.DRAG_DONE)
                onDragDoneHandler(event);
        }

        private void onDragOverHandler(DragEvent event) {
            if (event.getTransferMode() != TransferMode.MOVE || !event.getGestureSource().equals(imageView))
                event.acceptTransferModes(TransferMode.MOVE);

            event.consume();
        }

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
