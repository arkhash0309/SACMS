package com.example.clubmanagementsystem;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Optional;

// This class is responsible on handling the application ribbon for all the stages
public class ApplicationController{
    // This method is responsible for closing the application
    public void closingApp(){
        // Conformation alert for closing the application
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.initModality(Modality.APPLICATION_MODAL);
        exitAlert.setTitle("School Activity Club Management System");
        exitAlert.setHeaderText("Do you really want to exit the program ?");

        // Display the alert and waiting for user input
        Optional<ButtonType> resultExit = exitAlert.showAndWait();

        // Handling user's decision to exit or not
        if(resultExit.get() == ButtonType.OK){
            Platform.exit();
        }
    }

    // This method is responsible for closing the window
    public void MinimizeApp(StackPane paneName){
        // Getting the current stage associated with the provided stackPane
        Stage stage = (Stage) paneName.getScene().getWindow();

        // Minimizing the stage
        stage.setIconified(true);
    }


}