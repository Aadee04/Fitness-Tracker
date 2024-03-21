package com.example.demofitness;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml")); //loading login page
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Fitness Tracker");
            stage.setResizable(false);
            stage.setScene(scene); //setting login page
            stage.show();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
