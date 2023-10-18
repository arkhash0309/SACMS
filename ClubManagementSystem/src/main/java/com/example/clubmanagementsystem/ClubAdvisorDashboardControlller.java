package com.example.clubmanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClubAdvisorDashboardControlller {
    @FXML
    private Label welcomeText;

    private double xPosition;

    private double yPosition;

    @FXML
    private StackPane ClubAdvisorDashboard;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    void ClubAdvisorDashboardDetected(MouseEvent event) {
       Stage stage =  (Stage)ClubAdvisorDashboard.getScene().getWindow();
       stage.setX(event.getScreenX()- xPosition);
       stage.setY(event.getScreenY() - yPosition);
    }

    @FXML
    void ClubAdvisorPanePressed(MouseEvent event) {
       xPosition = event.getSceneX();
       yPosition = event.getSceneY();
    }
}