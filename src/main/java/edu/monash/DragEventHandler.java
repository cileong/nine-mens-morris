package edu.monash;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

import static javafx.scene.input.DragEvent.*;
import static javafx.scene.input.TransferMode.MOVE;

public class DragEventHandler implements EventHandler<DragEvent> {

    private final ImageView imageView;

    DragEventHandler(ImageView imageView) {
        this.imageView = imageView;
    }

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
                    GridPane.getColumnIndex(imageView),
                    GridPane.getRowIndex(imageView)
            );

            imageView.setImage(db.getImage());
            event.setDropCompleted(true);

            System.out.println(sourceId + " " + destinationId);
        }

        event.consume();
    }

    private void onDragDoneHandler(DragEvent event) {
        if (event.getTransferMode() == MOVE)
            imageView.setImage(null);

        event.consume();
    }

}
