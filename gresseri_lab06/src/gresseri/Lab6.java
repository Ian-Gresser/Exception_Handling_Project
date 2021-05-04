/* Course: CS1021 - 081
 * Winter
 * Lab 6 - Exceptions
 * Name: Ian Gresser
 * Created: 1-23-20
 * Last modified: 1-29-20
 */
package gresseri;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main runner class of the gui
 */
public class Lab6 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Loads the FXML file, loads it into the primary stage, and shows it
     * @param primaryStage the stage the FXML is loaded into
     * @throws Exception based on error thrown from Lab6Controller
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("lab6.fxml"));
        primaryStage.setTitle("Website Tester");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}