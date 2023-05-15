package edu.monash;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

import static javafx.scene.input.DragEvent.*;
import static javafx.scene.input.MouseEvent.DRAG_DETECTED;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import static javafx.scene.input.TransferMode.MOVE;

public class GameBoardGridPane extends GridPane {

    void initialize() {
        for (Node node : this.getChildren()) {
            ImageView imageView = (ImageView) node;

            EventHandler<MouseEvent> mouseEventHandler = new MouseEventHandler(imageView);
            imageView.setOnMouseClicked(mouseEventHandler);
            imageView.setOnDragDetected(mouseEventHandler);

            EventHandler<DragEvent> dragEventHandler = new DragEventHandler(imageView);
            imageView.setOnDragOver(dragEventHandler);
            imageView.setOnDragDropped(dragEventHandler);
            imageView.setOnDragDone(dragEventHandler);
        }
    }

    private record MouseEventHandler(ImageView imageView) implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType() == DRAG_DETECTED)
                onDragDetectedHandler(event);
            else if (event.getEventType() == MOUSE_CLICKED)
                onMouseClickedHandler(event);
        }

        private void onMouseClickedHandler(MouseEvent event) {

        }

        private void onDragDetectedHandler(MouseEvent event) {
            if (imageView.getImage() == null)
                return;

            Integer sourceId = ViewController.getPositionId(
                    getColumnIndex(imageView),
                    getRowIndex(imageView)
            );

            Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            if (sourceId != null)
                content.putString(String.valueOf(sourceId));
            dragboard.setContent(content);

            event.consume();
        }

    }

    private record DragEventHandler(ImageView imageView) implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            if (event.getEventType() == DRAG_OVER)
                onDragOverHandler(event);
            else if (event.getEventType() == DRAG_DROPPED)
                onDragDroppedHandler(event);
            else if (event.getEventType() == DRAG_DONE)
                onDragDoneHandler(event);
        }

        private void onDragOverHandler(DragEvent event) {
            Dragboard db = event.getDragboard();

            if (!event.getGestureSource().equals(imageView) && db.hasImage())
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

            event.consume();
        }

        private void onDragDroppedHandler(DragEvent event) {
            Dragboard db = event.getDragboard();

            if (db.hasImage()) {
                Integer sourceId = db.hasString() ? Integer.parseInt(db.getString()) : null;
                Integer destinationId = ViewController.getPositionId(
                        getColumnIndex(imageView),
                        getRowIndex(imageView)
                );

                imageView.setImage(db.getImage());
                event.setDropCompleted(true);
            }

            event.consume();
        }

        private void onDragDoneHandler(DragEvent event) {
            if (event.getTransferMode() == MOVE)
                imageView.setImage(null);

            event.consume();
        }

    }
}
