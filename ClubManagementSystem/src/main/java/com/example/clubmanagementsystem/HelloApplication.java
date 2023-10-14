package com.example.clubmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ClubAdvisorDashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        }catch (Exception E){
            E.printStackTrace();
            System.out.println("Hello World !!!");
            System.out.println("Deelaka");
        }

    }

    public static void main(String[] args) {
        launch();
    }
}