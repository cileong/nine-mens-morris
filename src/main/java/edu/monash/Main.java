package edu.monash;

import java.io.IOException;

import edu.monash.game.Game;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class Main extends Application {

    private GUIController guiController;
    private Game game;
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getClassLoader().getResource("edu/monash/GUI.fxml"));
            VBox root = fxmlLoader.load();
            guiController = fxmlLoader.getController();

            game = new Game();
            guiController.setStage(stage);
            guiController.setGame(game);

            Platform.setImplicitExit(false);
            stage.setOnCloseRequest(event -> { // WindowEvent
                guiController.quitGame();
                event.consume();
            });

            ClassLoader classLoader = getClass().getClassLoader();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(classLoader.getResource("edu/monash/application.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("NINE MEN MORRIS");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            ClassLoader classLoader = getClass().getClassLoader();
//
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(classLoader.getResource("edu/monash/GUI.fxml"));
//            Parent root = fxmlLoader.load();
//
//
//
//            stage.setTitle("Nine Men's Morris");
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
