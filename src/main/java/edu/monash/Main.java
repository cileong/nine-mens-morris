package edu.monash;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The entry point of the application.
 */
public class Main extends Application {

    /**
     * Starts the application.
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set. Applications may
     *              create other stages, if needed, but they will not
     *              be primary stages.
     */
    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML file representing the view.
            ClassLoader classLoader = getClass().getClassLoader();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(classLoader.getResource("edu/monash/fxml/main.fxml"));
            Parent root = fxmlLoader.load();

            // Configure the primary stage.
            stage.setScene(new Scene(root));
            stage.setTitle("Nine Men's Morris");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The entry point of the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
