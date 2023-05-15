package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.actions.MoveAction;
import edu.monash.game.actions.PlaceAction;
import edu.monash.game.actions.RemoveAction;
import edu.monash.game.player.PlacePhase;
import edu.monash.game.player.SlidePhase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewController {

    private static final DataFormat GRID_PANE_CELL_FORMAT =
            new DataFormat("application/x-gridpane-cell");

    private Game game;

    public ViewController() {
        game = new Game();
    }

    private ObservableList<ImageView> boardGridChildren = FXCollections.observableArrayList();

    @FXML
    private Stage stage;
    @FXML
    private GridPane boardGrid;
    @FXML
    private GridPane blackGrid;
    @FXML
    private GridPane whiteGrid;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void newGame() {
        enableBlur();
//        showPromptDialog("New Game", null, "Start a new game?", 0);
        disableBlur();
    }

    @FXML
    public void quitGame() {
        enableBlur();
        //showPromptDialog("NINE MEN MORRIS", "Quit Game?", "Quit game?", 1);
        disableBlur();
    }


    public void showPromptDialog(String title, String header, String content, Runnable yesCallback, Runnable noCallback) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        // Remove default buttons and add custom "Yes" and "No" buttons
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == yesButton) {
                yesCallback.run();
            } else if (buttonType == noButton) {
                noCallback.run();
            }
        });
    }

    private final Integer[][] boardMapping = {
            {    0, null, null,    1, null, null,    2 },
            { null,    8, null,    9, null,   10, null },
            { null, null,   16,   17,   18, null, null },
            {    7,   15,   23, null,   19,   11,    3 },
            { null, null,   22,   21,   20, null, null },
            { null,   14, null,   13, null,   12, null },
            {    6, null, null,    5, null, null,    4 }
    };

    public Integer getPositionId(int x, int y) {
        try {
            return boardMapping[y][x];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void removePieceWhenMillIsFormed() {
        for (ImageView imageView : boardGridChildren) {
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

                Integer destinationX = GridPane.getRowIndex(imageView);
                Integer destinationY = GridPane.getColumnIndex(imageView);
                Integer positionOnBoard = getPositionId(destinationX, destinationY);
                boolean canRemove = game.execute(new RemoveAction(game.getCurrentPlayer(), positionOnBoard));
                System.out.println("Can remove: " + canRemove);

                    if (imageView.getImage() != null && imageView.getId() != null) {

                            if (canRemove) {
                                imageView.setImage(null);
                                imageView.setId(null);

                            } else {
                                showInvalidMoveDialog("Selected piece cannot be removed.");
                            }
                }
                event.consume();
            });
        }
    }
    private void showInvalidMoveDialog(String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.initStyle(StageStyle.UTILITY);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    private void initDrag(GridPane grid) {
        for (Node i : grid.getChildren()) {
//            if (!(i instanceof ImageView imageView)) {
//                System.out.println(i instanceof HBox);
//                continue;
//            }
            ImageView imageView = (ImageView) i;

            AtomicInteger sourceX = new AtomicInteger();
            AtomicInteger sourceY = new AtomicInteger();
            AtomicInteger sourcePosition = new AtomicInteger(getPositionId(sourceX.get(), sourceY.get()));

            imageView.setOnDragDetected(event -> {
                if (imageView.getImage() == null) {
                    return;
                }

                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                content.putString(imageView.getId());
                db.setContent(content);

                if (grid == boardGrid){
                    sourceX.set(GridPane.getRowIndex(imageView));
                    sourceY.set(GridPane.getColumnIndex(imageView));
                    sourcePosition.set(getPositionId(sourceX.get(), sourceY.get()));
                }

                event.consume();
            });

            imageView.setOnDragDone(event -> {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    imageView.setImage(null);
                    if (grid.getId().equals(boardGrid.getId())) {
                        imageView.setId(null);
                    }
                }

                event.consume();
            });

            imageView.setOnDragOver(event -> { // DragEvent

                Dragboard db = event.getDragboard();
                if (event.getGestureSource() != imageView && db.hasImage() && db.hasString() && imageView.getId() == null) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            int finalSourcePosition = sourcePosition.get();
            imageView.setOnDragDropped(event -> { // DragEvent
                Dragboard db = event.getDragboard();
                if (db.hasImage() && db.hasString()) {
                    Integer destinationX = GridPane.getRowIndex(imageView);
                    Integer destinationY = GridPane.getColumnIndex(imageView);
                    Integer destinationPosition = getPositionId(destinationX, destinationY);

                    if (game.getCurrentPlayer().getPhase() instanceof PlacePhase) {
                        boolean canPlace = game.execute(new PlaceAction(game.getCurrentPlayer(), destinationPosition));
                        System.out.println("position: " + destinationPosition + "Can Place: " + canPlace);
                    } else if (game.getCurrentPlayer().getPhase() instanceof SlidePhase){
                        boolean canMove = game.execute(new MoveAction(game.getCurrentPlayer(), finalSourcePosition,destinationPosition));
                        System.out.println("position: " + destinationPosition + "Can Move: " + canMove);
                    }

                    imageView.setImage(db.getImage());
                    imageView.setId(db.getString());

                    event.setDropCompleted(true);

                }
                event.consume();
            });
        }
    }


    private void enableBlur() {
        stage.getScene().getRoot().setEffect(new GaussianBlur(4));
    }

    private void disableBlur() {
        stage.getScene().getRoot().setEffect(null);
    }

    @FXML
    private void initialize() {

        initDrag(blackGrid);
        initDrag(whiteGrid);
        initDrag(boardGrid);
        removePieceWhenMillIsFormed();
//        System.out.println("HELLO WORLD");
    }


}
