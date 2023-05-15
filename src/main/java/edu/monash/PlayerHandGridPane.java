package edu.monash;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

import static javafx.scene.input.DragEvent.*;
import static javafx.scene.input.MouseEvent.DRAG_DETECTED;
import static javafx.scene.input.TransferMode.MOVE;

public class PlayerHandGridPane extends GridPane {

    void initialize() {
        for (Node node : this.getChildren()) {
            ImageView imageView = (ImageView) node;
            imageView.setOnDragDetected(new MouseEventHandler(imageView));
            imageView.setOnDragDone(new DragEventHandler(imageView));
        }
    }

    private record MouseEventHandler(ImageView imageView) implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType() == DRAG_DETECTED)
                onDragDetectedHandler(event);
        }

        private void onDragDetectedHandler(MouseEvent event) {
            if (imageView.getImage() == null)
                return;

            Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            dragboard.setContent(content);

            event.consume();
        }

    }

    private record DragEventHandler(ImageView imageView) implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            if (event.getEventType() == DRAG_DONE)
                onDragDoneHandler(event);
        }

        private void onDragDoneHandler(DragEvent event) {
            if (event.getTransferMode() == MOVE)
                imageView.setImage(null);

            event.consume();
        }

    }

}
