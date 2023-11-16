package com.example.clubmanagementsystem;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class ApplicationController{

    public void closingApp(){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.initModality(Modality.APPLICATION_MODAL);
        exitAlert.setTitle("School Activity Club Management System");
        exitAlert.setHeaderText("Do you really want to exit the program ?");

        Optional<ButtonType> resultExit = exitAlert.showAndWait();
        if(resultExit.get() == ButtonType.OK){
            Platform.exit();
        }
    }

    public void MinimizeApp(StackPane paneName){
        Stage stage = (Stage) paneName.getScene().getWindow();
        stage.setIconified(true);
    }


}