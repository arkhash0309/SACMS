package LoginManager;

import com.example.clubmanagementsystem.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClubAdvisorLoginController {
    @FXML
    private StackPane clubAdvisorStackPane;
    private Scene scene;
    private Stage stage;

    private Parent root;

    private double xPosition;

    private double yPosition;

    @FXML
    private Button ClubAdvisorLoginMinimizer;


    @FXML
    void DirectToStartPane(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/clubmanagementsystem/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clubAdvisorPaneDragDetector(MouseEvent event) {
        Stage stage =  (Stage)clubAdvisorStackPane.getScene().getWindow();
        stage.setX(event.getScreenX()- xPosition);
        stage.setY(event.getScreenY() - yPosition);
    }

    @FXML
    void clubAdvisorPanePressDetector(MouseEvent event) {
        xPosition = event.getSceneX();
        yPosition = event.getSceneY();
    }

    @FXML
    void MinimizeClubAdvisorLogin(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(clubAdvisorStackPane);
    }


    @FXML
    void ClubAdvisorLoginExit(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    @FXML
    void DirectToStudentDashBoard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/clubmanagementsystem/ClubAdvisorDashboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


}
