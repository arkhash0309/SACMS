module com.example.clubmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.clubmanagementsystem to javafx.fxml;
    exports com.example.clubmanagementsystem;
}