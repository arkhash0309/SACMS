package ClubAdvisorManager;

import com.example.clubmanagementsystem.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClubAdvisorDashboardControlller {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane justAnchor;
    private double xPosition;

    private double yPosition;

    private Scene scene;
    private Stage stage;

    private Parent root;

    @FXML
    private StackPane ClubAdvisorDashboard;

    @FXML
    private AnchorPane dashboardMainPane;

    @FXML
    private AnchorPane ManageClubPane;

    @FXML
    private AnchorPane ScheduleEventsPane;

    @FXML
    private AnchorPane GenerateReportsPane;

    @FXML
    private Button ManageclubButton;
    @FXML
    private AnchorPane AttendancePane;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button ScheduleEventsButton;

    @FXML
    private Button AttendanceButton;

    @FXML
    private Button GenerateReportsButton;

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

    @FXML
    void dashBoardLogOut(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginManager/ClubAdvisorLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void MinimizePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(ClubAdvisorDashboard);
    }


    @FXML
    void ClosePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    public void makeAllClubAdvisorPanesInvisible(){
        dashboardMainPane.setVisible(false);
        ManageClubPane.setVisible(false);
        ScheduleEventsPane.setVisible(false);
        AttendancePane.setVisible(false);
        GenerateReportsPane.setVisible(false);
    }

    public void makeAllButtonsColoured(){
        dashboardButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ScheduleEventsButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        AttendanceButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        GenerateReportsButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
    }

    @FXML
    void GoToDashBoardClubAdvisor(ActionEvent event) {
       makeAllClubAdvisorPanesInvisible();
       makeAllButtonsColoured();
       dashboardMainPane.setVisible(true);
       dashboardButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @FXML
    void GoToManageClubPane(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        ManageClubPane.setVisible(true);
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @FXML
    void GoToScheduleEvents(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        ScheduleEventsPane.setVisible(true);
        ScheduleEventsButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @FXML
    void GoToTrackAttendance(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        AttendancePane.setVisible(true);
        AttendanceButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @FXML
    void GoToGenerateReports(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        GenerateReportsPane.setVisible(true);
        GenerateReportsButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }
}