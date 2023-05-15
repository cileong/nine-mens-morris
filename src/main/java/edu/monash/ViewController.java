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
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewController {

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

    private static final Integer[][] boardMapping = {
            {    0, null, null,    1, null, null,    2 },
            { null,    8, null,    9, null,   10, null },
            { null, null,   16,   17,   18, null, null },
            {    7,   15,   23, null,   19,   11,    3 },
            { null, null,   22,   21,   20, null, null },
            { null,   14, null,   13, null,   12, null },
            {    6, null, null,    5, null, null,    4 }
    };

    public static Integer getPositionId(int x, int y) {
        try {
            return boardMapping[y][x];
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
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

                    if (imageView.getImage() != null) {

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
        for (Node node : grid.getChildren()) {
            ImageView imageView = (ImageView) node;

            MouseEventHandler mouseEventHandler = new MouseEventHandler(imageView);
            imageView.setOnDragDetected(mouseEventHandler);

            DragEventHandler dragEventHandler = new DragEventHandler(imageView);
            imageView.setOnDragOver(dragEventHandler);
            imageView.setOnDragDropped(dragEventHandler);
            imageView.setOnDragDone(dragEventHandler);
        }
    }

    @FXML
    private void initialize() {
        initDrag(blackGrid);
        initDrag(whiteGrid);
        initDrag(boardGrid);
//        removePieceWhenMillIsFormed();
    }

}
