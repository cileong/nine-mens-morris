package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.PieceColour;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

public class PlayerHandGridPane extends GridPane {

    private Game game;
    private PieceColour pieceColour;
    private Image blackImage;
    private Image whiteImage;

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


    private class MouseEventHandler implements EventHandler<MouseEvent> {

        private final ImageView imageView;

        MouseEventHandler(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType() == MouseEvent.DRAG_DETECTED)
                onDragDetectedHandler(event);
        }

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

    private record DragEventHandler(ImageView imageView) implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            if (event.getEventType() == DragEvent.DRAG_DONE)
                onDragDoneHandler(event);
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