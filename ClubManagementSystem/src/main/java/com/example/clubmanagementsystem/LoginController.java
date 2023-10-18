package com.example.clubmanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class LoginController {
    private double xPosition;

    private double yPosition;

    @FXML
    private AnchorPane LoginPane;

    @FXML
    void LoginDragDetected(MouseEvent event) {
        Stage stage =  (Stage)LoginPane.getScene().getWindow();
        stage.setX(event.getScreenX()- xPosition);
        stage.setY(event.getScreenY() - yPosition);
    }

    @FXML
    void LoginPanePressed(MouseEvent event) {
        xPosition = event.getSceneX();
        yPosition = event.getSceneY();
    }

}
