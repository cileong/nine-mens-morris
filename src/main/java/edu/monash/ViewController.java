package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.actions.RemoveAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewController {

    private Game game;

    public ViewController() {
        game = new Game();
    }

    private ObservableList<ImageView> boardGridChildren = FXCollections.observableArrayList();

    @FXML
    private Stage stage;
    @FXML
    private GameBoardGridPane boardGrid;
    @FXML
    private PlayerHandGridPane blackGrid;
    @FXML
    private PlayerHandGridPane whiteGrid;

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

    public static Integer getPositionId(Integer x, Integer y) {
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

    @FXML
    private void initialize() {
        boardGrid.initialize();
        blackGrid.initialize();
        whiteGrid.initialize();
    }

}
