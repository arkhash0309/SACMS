package com.example.clubmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.sql.*;

// HelloApplication will run as the application launcher
public class HelloApplication extends Application {
    // static variables are declared for database connection to use in any class
    public static Connection connection; // Assign database connection
    public static Statement statement; // prepare statement
    @Override
    public void start(Stage stage) throws IOException {
        try{
            // Common login page will be loaded first
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/clubmanagementsystem/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 763,502);
            stage.setResizable(false);
            // Making application default ribbon for close and minimize buttons undecorated
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();



            System.out.println("Lakshan200");
            System.out.println("Lakshan1234");
        }catch (Exception E){
            E.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        // Local host database connection related details
        String URL = "jdbc:mysql://localhost:3306/ClubManagementSystsem";
        String user = "root"; // username for localhost database
        String password = "root"; // password for localhost database

        try{
            // Loading JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Established the localhost database connection and to create statements
            connection = DriverManager.getConnection(URL, user, password);
            statement = connection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        launch();

        // Closing the database connection after terminating the application
        connection.close();
    }

}