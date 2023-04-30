package edu.monash;

import java.io.IOException;
import java.net.URL;

import edu.monash.controller.GUIController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private GUIController guiController;
    private Game game;
    @Override
    public void start(Stage stage) {
//        String javaVersion = System.getProperty("java.version");
//        String javafxVersion = System.getProperty("javafx.version");
//        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//        Scene scene = new Scene(new StackPane(l), 640, 480);
//        stage.setScene(scene);
//        stage.show();

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
                guiController.handleClose();
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

    }

    public static void main(String[] args) {
        launch();
    }

}
