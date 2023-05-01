package edu.monash;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(classLoader.getResource("edu/monash/fxml/main.fxml"));
            Parent root = fxmlLoader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Nine Men's Morris");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
