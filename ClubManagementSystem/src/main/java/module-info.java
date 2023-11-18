module com.example.clubmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    opens ClubManager to javafx.fxml;
    exports ClubManager;

    opens ClubManager to javafx.fxml;
    exports ClubManager;
    opens com.example.clubmanagementsystem to javafx.fxml;
    exports com.example.clubmanagementsystem;
    exports LoginDashboardManager;
    opens LoginDashboardManager to javafx.fxml;
    exports StudentDashboardManager;
    opens StudentDashboardManager to javafx.fxml;
    exports ClubAdvisorDashboardManager;
    opens ClubAdvisorDashboardManager to javafx.fxml;
}