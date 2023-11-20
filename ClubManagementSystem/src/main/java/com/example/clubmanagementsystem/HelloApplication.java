package com.example.clubmanagementsystem;

import SystemUsers.Student;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    public static Connection connection;
    public static Statement statement;
    @Override
    public void start(Stage stage) throws IOException {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/clubmanagementsystem/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 763,502);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        }catch (Exception E){
            E.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/ClubManagementSystsem";
        String user = "root";
        String password = "root";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, user, password);
            statement = connection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        launch();
        connection.close();

    }

}