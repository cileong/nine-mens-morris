package edu.monash;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

public class GameBoardGridPane extends GridPane {

    private static final Integer[][] boardMapping = {
            {    0, null, null,    1, null, null,    2 },
            { null,    8, null,    9, null,   10, null },
            { null, null,   16,   17,   18, null, null },
            {    7,   15,   23, null,   19,   11,    3 },
            { null, null,   22,   21,   20, null, null },
            { null,   14, null,   13, null,   12, null },
            {    6, null, null,    5, null, null,    4 }
    };

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

    static Integer getPositionId(Integer x, Integer y) {
        try {
            return boardMapping[y][x];
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private record MouseEventHandler(ImageView imageView) implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType() == MouseEvent.DRAG_DETECTED)
                onDragDetectedHandler(event);
            else if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
                onMouseClickedHandler(event);
        }

        private void onMouseClickedHandler(MouseEvent event) {

        }

        private void onDragDetectedHandler(MouseEvent event) {
            if (imageView.getImage() == null)
                return;

            Integer sourceId = getPositionId(
                    getColumnIndex(imageView),
                    getRowIndex(imageView)
            );

            Dragboard dragboard = imageView.startDragAndDrop(TransferMode.MOVE);
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
            if (event.getEventType() == DragEvent.DRAG_OVER)
                onDragOverHandler(event);
            else if (event.getEventType() == DragEvent.DRAG_DROPPED)
                onDragDroppedHandler(event);
            else if (event.getEventType() == DragEvent.DRAG_DONE)
                onDragDoneHandler(event);
        }

        private void onDragOverHandler(DragEvent event) {
            Dragboard db = event.getDragboard();

            if (!event.getGestureSource().equals(imageView) && db.hasImage())
                event.acceptTransferModes(TransferMode.MOVE);

            event.consume();
        }

        private void onDragDroppedHandler(DragEvent event) {
            Dragboard db = event.getDragboard();

            if (db.hasImage()) {
                Integer sourceId = db.hasString() ? Integer.parseInt(db.getString()) : null;
                Integer destinationId = getPositionId(
                        getColumnIndex(imageView),
                        getRowIndex(imageView)
                );

                System.out.println("sourceId: " + sourceId);
                System.out.println("destinationId: " + destinationId);

                imageView.setImage(db.getImage());
                event.setDropCompleted(true);
            }

            event.consume();
        }

        private void onDragDoneHandler(DragEvent event) {
            if (event.getTransferMode() == TransferMode.MOVE)
                imageView.setImage(null);

            event.consume();
        }

    }
}
