package ClubAdvisorDashboardManager;

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

abstract public class ClubAdvisorDashboardControlller {
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
    public Button dashboardButton;

    @FXML
    private Button ScheduleEventsButton;

    @FXML
    private Button AttendanceButton;

    @FXML
    private Button GenerateReportsButton;

    @FXML
    private AnchorPane ClubActivitiesPane;

    @FXML
    private AnchorPane EventAttendancePane;

    @FXML
    private AnchorPane MembershipReportPane;

    @FXML
    private AnchorPane ViewEventsPane;

    @FXML
    private AnchorPane ScheduleEventsInnerPane;

    @FXML
    private AnchorPane CancelEventsPane;

    @FXML
    private AnchorPane UpdatesEventPane;

    @FXML
    private AnchorPane UpdateClubDetailPane;

    @FXML
    private AnchorPane ProfilePane;

    @FXML
    private AnchorPane createClubPane;
    @FXML
    public Button ViewEventButton;

    @FXML
    private Button ScheduleEventButton;

    @FXML
    private Button CancelEventButton;

    @FXML
    private Button UpdateEventButton;

    @FXML
    public Button GoToClubMembershipButton;

    @FXML
    private Button GoToEventAttendanceButton;

    @FXML
    private Button GoToClubActivitiesButton;

    @FXML
    public Button CreateClubDirectorButton;


    @FXML
    private Button UpdateClubDirectorButton;

    @FXML
    private Button AdvisorProfileButton;

    @FXML
    private Label clubIdError;
    @FXML
    private Label clubLogoError;
    @FXML
    private Label clubNameError;
    @FXML
    private Label clubDescriptionError;


}