package edu.monash;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

import static javafx.scene.input.MouseEvent.DRAG_DETECTED;

public class MouseEventHandler implements EventHandler<MouseEvent> {

    private final ImageView imageView;

    public MouseEventHandler(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == DRAG_DETECTED)
            onDragDetectedHandler(event);
    }

    private void onDragDetectedHandler(MouseEvent event) {
        if (imageView.getImage() == null)
            return;

        Integer sourceId = ViewController.getPositionId(
                GridPane.getColumnIndex(imageView),
                GridPane.getRowIndex(imageView)
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
