package ClubAdvisorDashboardManager;
import com.jfoenix.controls.JFXDatePicker;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;


abstract public class ClubAdvisorDashboardControlller implements Initializable {

    @FXML
    private JFXDatePicker dtpicker;
    @FXML
    protected Label welcomeText;
    @FXML
    protected AnchorPane justAnchor;
    protected double xPosition;
    protected double yPosition;

    protected Scene scene;
    protected Stage stage;
    protected Parent root;
    @FXML
    protected StackPane ClubAdvisorDashboard;

    @FXML
    protected AnchorPane dashboardMainPane;

    @FXML
    protected AnchorPane ManageClubPane;

    @FXML
    protected AnchorPane ScheduleEventsPane;

    @FXML
    protected AnchorPane GenerateReportsPane;

    @FXML
    protected Button ManageclubButton;

    @FXML
    protected AnchorPane AttendancePane;
    @FXML
    public Button dashboardButton;

    @FXML
    protected Button ScheduleEventsButton;

    @FXML
    protected Button AttendanceButton;

    @FXML
    protected Button GenerateReportsButton;

    @FXML
    protected AnchorPane ClubActivitiesPane;

    @FXML
    protected AnchorPane EventAttendancePane;

    @FXML
    protected AnchorPane MembershipReportPane;

    @FXML
    protected AnchorPane ViewEventsPane;

    @FXML
    protected AnchorPane ScheduleEventsInnerPane;

    @FXML
    protected AnchorPane CancelEventsPane;

    @FXML
    protected AnchorPane UpdatesEventPane;

    @FXML
    protected AnchorPane UpdateClubDetailPane;

    @FXML
    protected AnchorPane ProfilePane;

    @FXML
    protected AnchorPane createClubPane;
    @FXML
    public Button ViewEventButton;

    @FXML
    protected Button ScheduleEventButton;

    @FXML
    protected Button CancelEventButton;

    @FXML
    protected Button UpdateEventButton;
    @FXML
    public Button GoToClubMembershipButton;

    @FXML
    protected Button GoToEventAttendanceButton;

    @FXML
    protected Button GoToClubActivitiesButton;

    @FXML
    public Button CreateClubDirectorButton;


    @FXML
    protected Button UpdateClubDirectorButton;

    @FXML
    protected Button AdvisorProfileButton;

    @FXML
    protected TextField scheduleEventNameTextField;

    @FXML
    protected TextField scheduleEventsLocationTextField;

    @FXML
    protected Button scheduleEventScheduleButton;

    @FXML
    protected Button scheduleEventClearButton;

    @FXML
    protected DatePicker scheduleEventDatePicker;

    @FXML
    protected TextField scheduleCreatedEventsSearchBar;

    @FXML
    protected TableView<?> scheduleCreatedEventTable;

    @FXML
    protected Button scheduleCreatedEventsSearchButton;

    @FXML
    protected TextField viewCreatedEventsSearchPane;

    @FXML
    protected Button ViewCreatedEventsSearchButton;

    @FXML
    protected ComboBox<String> scheduleEventTypeCombo;

    @FXML
    protected ComboBox<String> ScheduleEventsDeliveryType;

    @FXML
    protected ComboBox<String> updateEventClubCombo;

    @FXML
    protected TextArea scheduleEventDescriptionTextField;
    @FXML
    private Label clubIdError;
    @FXML
    private Label clubLogoError;
    @FXML
    private Label clubNameError;
    @FXML
    private Label clubDescriptionError;

    @FXML
    protected Label updateErrorLabelEventLocation;

    @FXML
    protected Label updateErrorLabelEventName;

    @FXML
    protected Label updateErrorLabelEventType;

    @FXML
    protected Label updateErrorLabelEventDate;

    @FXML
    protected Label updateErrorLabelDeliveryType;

    @FXML
    protected Label updateErrorLabelEventDescription;

    @FXML
    protected Label scheduleErrorLabelClubName;

    @FXML
    protected Label scheduleErrorLabelEventName;

    @FXML
    protected Label scheduleErrorLabelEventDate;

    @FXML
    protected Label scheduleErrorLabelEventLocation;

    @FXML
    protected Label scheduleErrorLabelEventType;

    @FXML
    protected Label scheduleErrorLabelEventDeliveryType;

    @FXML
    protected Label scheduleErrorLabelEventDescription;

    @FXML
    protected TextField updateEventLocationTextField;

    @FXML
    protected TextField updateEventNameTextField;

    @FXML
    protected  ComboBox<String> updateEventTypeCombo;

    @FXML
    protected DatePicker updateEventDateDatePicker;

    @FXML
    protected ComboBox<String> updateEventDeliveryTypeCombo;

    @FXML
    protected  TextArea updateEventDescription;

    @FXML
    protected ComboBox<String> updateHourComboBox;

    @FXML
    protected ComboBox<String> updateMinuteComboBox;
    @FXML
    private TextField updateEventClubTextField;

    @FXML
    protected ComboBox<String> scheduleEventHour;

    @FXML
    protected ComboBox<String> scheduleEventMinutes;
    @FXML
    abstract protected void clearScheduleEventFields(ActionEvent event);

    @FXML
    abstract void ClubAdvisorDashboardDetected(MouseEvent event);

    @FXML
    abstract void ClubAdvisorPanePressed(MouseEvent event);

    @FXML
    abstract void dashBoardLogOut(MouseEvent event) throws IOException;
    @FXML
    abstract void MinimizePane(ActionEvent event);

    @FXML
    abstract void ClosePane(ActionEvent event);

    abstract public void makeAllClubAdvisorPanesInvisible();

    abstract public void makeAllButtonsColoured();

    @FXML
    abstract void GoToDashBoardClubAdvisor(ActionEvent event);

    @FXML
    abstract void GoToManageClubPane(ActionEvent event);

    @FXML
    abstract void GoToScheduleEvents(ActionEvent event);
    @FXML
    abstract void GoToTrackAttendance(ActionEvent event);
    @FXML
    abstract void GoToGenerateReports(ActionEvent event);
    @FXML
    abstract void GoToClubAdvisorProfile(ActionEvent event);
    @FXML
    abstract void GoToEventAttendance(ActionEvent event);
    @FXML
    abstract void GoToClubActivities(ActionEvent event);

    @FXML
    abstract void GoToClubMembership(ActionEvent event);

    abstract public void makeAllPanesInvisibleGeneratingReport();

    abstract public void makeAllPanesInvisibleEventPane();

    @FXML
    abstract void GoToUpdateEventsPanes(ActionEvent event);
    @FXML
    abstract void GoToViewEventsPane(ActionEvent event);

    @FXML
    abstract void GoToScheduleEventsPane(ActionEvent event);

    @FXML
    abstract void GoToCancelEventsPane(ActionEvent event);

    abstract public void makeAllClubCreationPanesInvisible();

    @FXML
    abstract void GoToCreateClubPane(ActionEvent event);

    @FXML
    abstract void GoToUpdateClubDetailsPane(ActionEvent event);

    @FXML
    abstract protected void clearUpdateEventFields(ActionEvent event);

}