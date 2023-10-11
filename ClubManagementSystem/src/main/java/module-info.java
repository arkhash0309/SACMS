module com.example.clubmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.clubmanagementsystem to javafx.fxml;
    exports com.example.clubmanagementsystem;
}