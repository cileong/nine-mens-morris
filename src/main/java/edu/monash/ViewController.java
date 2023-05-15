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

    private void removePieceWhenMillIsFormed() {
        for (ImageView imageView : boardGridChildren) {
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

                Integer destinationX = GridPane.getRowIndex(imageView);
                Integer destinationY = GridPane.getColumnIndex(imageView);
                Integer positionOnBoard = GameBoardGridPane.getPositionId(destinationX, destinationY);
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
