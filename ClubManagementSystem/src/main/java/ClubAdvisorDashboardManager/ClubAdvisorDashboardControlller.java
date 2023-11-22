package ClubAdvisorDashboardManager;

import ClubManager.Club;
import ClubManager.Attendance;
import ClubManager.Club;
import ClubManager.Event;
import SystemUsers.ClubAdvisor;
import SystemUsers.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import ClubManager.Event;


abstract public class ClubAdvisorDashboardControlller implements Initializable {

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
    public ComboBox<String> updateEventClubCombo;
    @FXML
    public ComboBox<String> viewCreatedEventsSortComboBox;

    @FXML
    protected TextArea scheduleEventDescriptionTextField;
    @FXML
    public Label showUserNameClubAdvisor;
  
    @FXML
    public TextField clubName;
    @FXML
    public TextField clubId;
    @FXML
    public TextArea clubDescription;
    @FXML
    public ImageView createClubImage;
    @FXML
    public Button createClubImageButton;
    @FXML
    public Label clubLogoError;
    @FXML
    public Label clubNameError;
    @FXML
    public Label clubDescriptionError;
    @FXML
    public TableView<Club> createClubDetailsTable;
    @FXML
    public TableColumn<Club, String> createClubTableDescription;
    @FXML
    public TableColumn<Club, Integer> createClubTableId;
    @FXML
    public TableColumn<Club, ImageView> createClubTableLogo;
    @FXML
    public TableColumn<Club, String> createClubTableName;
    @FXML
    public Label updateClubNameError;
    @FXML
    public Label updateClubDescriptionError;
    @FXML
    public TextField updateClubSearch;
    @FXML
    public TableView<Club> updateClubDetailsTable;
    @FXML
    public TableColumn<Club, String> updateClubTableDescription;
    @FXML
    public TableColumn<Club, Integer> updateClubTableId;
    @FXML
    public TableColumn<Club, String> updateClubTableLogo;
    @FXML
    public TableColumn<Club, String> updateClubTableName;
    @FXML
    public TextField updateClubID;
    @FXML
    public TextField updateClubName;
    @FXML
    public TextArea updateClubDescription;
    @FXML
    public ImageView updateClubImage;
    @FXML
    public Button updateClubImageButton;
  
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
    protected Label updateErrorLabelClubName;

    @FXML
    protected ComboBox<String> updateHourComboBox;

    @FXML
    protected ComboBox<String> updateMinuteComboBox;

    @FXML
    protected Button updateEventFieldButton;
    @FXML
    protected Button clearEventFieldButton;
    @FXML
    private TextField updateEventClubTextField;

    @FXML
    protected ComboBox<String> scheduleEventHour;

    @FXML
    protected ComboBox<String> scheduleEventMinutes;
    @FXML
    public ComboBox<String> scheduleEventsClubName;

    @FXML
    protected TableView<Event> scheduleCreatedEventTable;

    @FXML
    protected TableColumn<Event, String> createEventClubNameColumn;

    @FXML
    protected TableColumn<Event, String> createEventEventNameColumn;

    @FXML
    protected TableColumn<Event, String> createEventEventDateColumn;

    @FXML
    protected TableColumn<Event, String> createEventLocationColumn;

    @FXML
    protected TableColumn<Event, String> createEventTypeColumn;

    @FXML
    protected TableColumn<Event, String> createEventDeliveryTypeColumn;

    @FXML
    protected TableColumn<Event, String> createEventDescriptionColumn;

    @FXML
    protected TableColumn<Event, LocalTime> createEventTimeColumn;

    @FXML
    protected TableView<Event> updateEventTable;

    @FXML
    protected TableColumn<Event, String> updateClubNameColumn;

    @FXML
    protected TableColumn<Event, String> updateEventNameColumn;

    @FXML
    protected TableColumn<Event, String> updateEventDateColumn;

    @FXML
    protected TableColumn<Event, String> updateEventLocationColumn;

    @FXML
    protected TableColumn<Event, String> updateEventTypeColumn;

    @FXML
    protected TableColumn<Event, String> updateDeliveryTypeColumn;

    @FXML
    protected TableColumn<Event, String> updateEventDescriptionColumn;

    @FXML
    protected TableColumn<Event, LocalTime> updateEventTimeColumn;

    @FXML
    protected TableView<Event> cancelEventTable;

    @FXML
    protected TableColumn<Event, String> cancelEventClubNameColumn;

    @FXML
    protected TableColumn<Event, String> cancelEventEventNameColumn;

    @FXML
    protected TableColumn<Event, String> cancelEventEventDateColumn;

    @FXML
    protected TableColumn<Event, String> cancelEventEventLocationColumn;

    @FXML
    protected TableColumn<Event, String> cancelEventEventTypeColumn;

    @FXML
    protected TableColumn<Event, String> cancelEventDeliveryTypeColumn;

    @FXML
    protected TableColumn<Event, String> cancelEventEventDescriptionColumn;

    @FXML
    protected TableColumn<Event, LocalTime> cancelEventTimeColumn;

    @FXML
    public TableView<Event> viewCreatedEventsTable;

    @FXML
    protected TableColumn<Event, String> viewEventClubNameColumn;

    @FXML
    protected TableColumn<Event, String> viewEventEventNameColumn;

    @FXML
    protected TableColumn<Event, String> viewEventDateColumn;

    @FXML
    protected TableColumn<Event, String> viewEventLocationColumn;

    @FXML
    protected TableColumn<Event, String> viewEventTypeColumn;

    @FXML
    protected TableColumn<Event, String> viewEventDeliveryTypeColumn;

    @FXML
    protected TableColumn<Event, String> viewEventDescriptionColumn;

    @FXML
    protected TableColumn<Event, LocalTime> viewEventTimeColumn;

    @FXML
    protected TextField cancelEventSearchBar;

    @FXML
    protected TextField updateEventSearchBar;

    @FXML
    protected Button scheduleEventSearchButton;

    @FXML
    protected TextField createdEventSearchBar;

    @FXML
    protected Label numberOfScheduledEvents;

    @FXML
    protected Label nextEventDate;

    @FXML
    protected Label numberOfClubs;

    @FXML
    protected Button GoToRegistrationButton;

    @FXML
    protected AnchorPane RegistrationReportPane;

    @FXML
    protected TableView<Attendance> tb1;

    @FXML
    protected TableColumn<Attendance, Boolean> atColumn;

    @FXML
    protected TableColumn<Attendance, CheckBox> stColumn;

    @FXML
    public TextField profileAdvisorUsername;
    @FXML
    public TextField profileAdvisorLname;
    @FXML
    public TextField profileAdvisorId;
    @FXML
    public TextField profileAdvisorFname;


    @FXML
    public TextField profileAdvisorCnumber;
    @FXML
    public Label profileAdvisorCnumberError;
    @FXML
    public Label profileAdvisorFnameError;
    @FXML
    public Label profileAdvisorIdError;
    @FXML
    public Label profileAdvisorLnameError;
    @FXML
    public Label profileAdvisorUsernameError;

    @FXML
    public PasswordField profileAdvisorExistingpw;
    @FXML
    public Label profileAdvisorExistingpwError;
    @FXML
    public PasswordField profileAdvisorNewpw;
    @FXML
    public Label profileAdvisorNewpwError;
    @FXML
    public PasswordField profileAdvisorConfirmpw;
    @FXML
    public Label profileAdvisorConfirmpwError;
    @FXML
    public ComboBox<String> clubMembershipCombo;
    
    @FXML
    protected BarChart<?, ?> GenderRatioChart;

    @FXML
    protected CategoryAxis GenderOfStudent;

    @FXML
    protected NumberAxis NumberOfStudents;

    @FXML
    protected BarChart<?, ?> EnrollStudentCountEachGrade;

    @FXML
    protected CategoryAxis gradeDetail;

    @FXML
    protected NumberAxis NumberOfStudentsEachGrade;
    @FXML
    protected ComboBox<String> attendanceClubNameComboBox;

    @FXML
    protected ComboBox<String> attendanceEventNameComboBox;

    @FXML
    protected TableColumn<Attendance, String> attendanceClubNameColumn;

    @FXML
    protected TableColumn<Attendance, String> attendanceEventNameColumn;

    @FXML
    protected TableColumn<Attendance, Integer> attendanceStudentAdmissionNumColumn;

    @FXML
    protected TableColumn<Attendance, String> attendanceStudentNameColumn;

    @FXML
    protected TableColumn<Attendance, CheckBox> attendanceStatusColumn;

    @FXML
    protected TableView<String> attendanceTrackerTable;
    @FXML
    public TableView<Event> generateReportEventViewTable;
    @FXML
    public TableColumn<Event, String> generateReportClubName;

    @FXML
    public TableColumn<Event, String> generateReportEventName;

    @FXML
    public TableColumn<Event, LocalDate> generateReportEventDate;

    @FXML
    public TableColumn<Event, LocalTime> generateReportEventTime;

    @FXML
    public TableColumn<Event, String> generateReportEventLocation;

    @FXML
    public TableColumn<Event, String> generateReportEventType;

    @FXML
    public TableColumn<Event, String> generateReportDeliveryType;

    @FXML
    public TableColumn<Event, String> generateReportEventDescription;

    @FXML
    public ComboBox<String> generateReportClubNameComboBox;

    @FXML
    public Label UpcomingEventCountGenerateReports;

    @FXML
    public Label eventDateRange;

    public int clubAdvisorId;

    @FXML
    public TableView<Student> clubMembershipTable;
    @FXML
    public TableColumn<Student, Integer> memberAdmissionNumber;
    @FXML
    public TableColumn<Student, String> memberContactNumber;
    @FXML
    public TableColumn<Student, String> memberFirstName;
    @FXML
    public TableColumn<Student, Character> memberGender;
    @FXML
    public TableColumn<Student, Integer> memberGrade;
    @FXML
    public TableColumn<Student, String> memberLastName;
    @FXML
    public TableColumn<Student, String>memberUsername;
    @FXML
    protected ComboBox<String> registrationUserSelectComboBox;

    @FXML
    protected TableView<Student> registrationStudentTable;

    @FXML
    protected TableColumn<Student, Integer> registrationStudentAdmissionNumberColumn;

    @FXML
    protected TableColumn<Student, String> registrationStudentUserName;

    @FXML
    protected TableColumn<Student, String> registrationStudentFirstNameColumn;

    @FXML
    protected TableColumn<Student, String> registrationStudentLastNameColumn;

    @FXML
    protected TableColumn<Student, Integer> registrationStudentGradeColumn;

    @FXML
    protected TableColumn<Student, String> registrationStudentContactNumberColumn;

    @FXML
    protected TableColumn<Student, String> registrationStudentGenderColumn;
    @FXML
    protected TableView<ClubAdvisor> registrationAdvisorTable;

    @FXML
    protected TableColumn<ClubAdvisor, Integer> registrationAdvisorID;

    @FXML
    protected TableColumn<ClubAdvisor, String> registrationAdvisorUserName;

    @FXML
    protected TableColumn<ClubAdvisor, String> registrationAdvisorFirstName;

    @FXML
    protected TableColumn<ClubAdvisor, String> registrationAdvisorLastName;

    @FXML
    protected TableColumn<ClubAdvisor, String> registrationAdvisorContactNumber;

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
    abstract void clubCreationChecker(ActionEvent event);
  
    @FXML
    abstract void clubCreationReset(ActionEvent event);
  
    abstract protected void clearUpdateEventFields(ActionEvent event);


}