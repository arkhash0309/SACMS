module com.example.clubmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.clubmanagementsystem to javafx.fxml;
    exports com.example.clubmanagementsystem;
    exports LoginManager;
    opens LoginManager to javafx.fxml;
    exports StudentManager;
    opens StudentManager to javafx.fxml;
    exports ClubAdvisorManager;
    opens ClubAdvisorManager to javafx.fxml;
}