package com.example.clubmanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        System.out.println("Hello World !!!");

        System.out.println("My name is Deelaka Dias.");
        System.out.println("Done");

    }
}