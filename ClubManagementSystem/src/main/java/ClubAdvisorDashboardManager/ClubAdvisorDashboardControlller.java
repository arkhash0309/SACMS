package ClubAdvisorDashboardManager;

import ClubManager.Club;
import ClubManager.Attendance;
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


// work done by- Pramuditha, Arkhash, Lakshan and Deelaka
abstract public class ClubAdvisorDashboardControlller implements Initializable {

    @FXML
    protected Label welcomeText;
    @FXML
    protected AnchorPane justAnchor;
    protected double xPosition;
    protected double yPosition;

    @FXML
    protected DatePicker scheduleEventDatePicker; // Date picker for the schedule event pane
    protected Scene scene; // scene of the current stage
    protected Stage stage; // current stage
    protected Parent root; // root of the current stage
    @FXML
    protected StackPane ClubAdvisorDashboard;
    // FXML elements defined in the associated FXML file
    @FXML
    protected AnchorPane dashboardMainPane; // Main pane of the dashboard

    @FXML
    protected AnchorPane ManageClubPane;  // Pane for managing clubs

    @FXML
    protected AnchorPane ScheduleEventsPane; // Pane for scheduling events

    @FXML
    protected AnchorPane GenerateReportsPane; // Pane for generating reports

    @FXML
    protected Button ManageclubButton; // Button for managing clubs

    @FXML
    protected AnchorPane AttendancePane; // Pane for tracking attendance
    @FXML
    public Button dashboardButton; // Button for going to the dashboard

    @FXML
    protected Button ScheduleEventsButton; // Button for scheduling events

    @FXML
    protected Button AttendanceButton; // Button for tracking attendance

    @FXML
    protected Button GenerateReportsButton; // Button for generating reports

    @FXML
    protected AnchorPane ClubActivitiesPane; // Pane for club activities

    @FXML
    protected AnchorPane EventAttendancePane; // Pane for event attendance

    @FXML
    protected AnchorPane MembershipReportPane; // Pane for membership report

    @FXML
    protected AnchorPane ViewEventsPane; // Pane for viewing events

    @FXML
    protected AnchorPane ScheduleEventsInnerPane; // Inner pane for scheduling events

    @FXML
    protected AnchorPane CancelEventsPane; // Pane for cancelling events

    @FXML
    protected AnchorPane UpdatesEventPane; // Pane for updating events

    @FXML
    protected AnchorPane UpdateClubDetailPane; // Pane for updating club details

    @FXML
    protected AnchorPane ProfilePane; // Pane for viewing profile

    @FXML
    protected AnchorPane createClubPane; // Pane for creating a club
    @FXML
    public Button ViewEventButton; // Button for viewing events

    @FXML
    protected Button ScheduleEventButton; // Button for scheduling events

    @FXML
    protected Button CancelEventButton; // Button for cancelling events

    @FXML
    protected Button UpdateEventButton; // Button for updating events
    @FXML
    public Button GoToClubMembershipButton; // Button for going to the club membership pane

    @FXML
    protected Button GoToEventAttendanceButton; // Button for going to the event attendance pane

    @FXML
    protected Button GoToClubActivitiesButton; // Button for going to the club activities pane

    @FXML
    public Button CreateClubDirectorButton; // Button for creating a club


    @FXML
    protected Button UpdateClubDirectorButton; // Button for updating club details

    @FXML
    protected Button AdvisorProfileButton;
    // Text fields and labels for student profile information
    @FXML
    protected TextField scheduleEventNameTextField; // Text field for the event name

    @FXML
    protected TextField scheduleEventsLocationTextField; // Text field for the event location

    @FXML
    protected Button scheduleEventScheduleButton; // Button for scheduling an event

    @FXML
    protected Button scheduleEventClearButton; // Button for clearing the event details

    @FXML
    protected TextField scheduleCreatedEventsSearchBar; // Search bar for searching scheduled events

    @FXML
    protected Button scheduleCreatedEventsSearchButton; // Button for searching scheduled events

    @FXML
    protected TextField viewCreatedEventsSearchPane; // Search bar for searching created events

    @FXML
    protected Button ViewCreatedEventsSearchButton; // Button for searching created events

    @FXML
    protected ComboBox<String> scheduleEventTypeCombo; // Combo box for the event type

    @FXML
    protected ComboBox<String> ScheduleEventsDeliveryType; // Combo box for the event delivery type

    @FXML
    public ComboBox<String> updateEventClubCombo; // Combo box for the club name
    @FXML
    public ComboBox<String> viewCreatedEventsSortComboBox; // Combo box for sorting created events

    @FXML
    protected TextArea scheduleEventDescriptionTextField; // Text area for the event description
    @FXML
    public Label showUserNameClubAdvisor; // Label for showing the username of the club advisor
    @FXML
    public TextField clubName; // Text field for the club name
    @FXML
    public TextField clubId; // Text field for the club ID
    @FXML
    public TextArea clubDescription; // Text area for the club description
    @FXML
    public ImageView createClubImage; // Image view for the club logo
    @FXML
    public Button createClubImageButton; // Button for uploading the club logo
    @FXML
    public Label clubLogoError; // Label for the club logo error
    @FXML
    public Label clubNameError; // Label for the club name error
    @FXML
    public Label clubDescriptionError; // Label for the club description error
    @FXML
    public TableView<Club> createClubDetailsTable;  // Table for the club details
    @FXML
    public TableColumn<Club, String> createClubTableDescription; // Column for the club details
    @FXML
    public TableColumn<Club, Integer> createClubTableId; // Column for the club details
    @FXML
    public TableColumn<Club, ImageView> createClubTableLogo; // Column for the club details
    @FXML
    public TableColumn<Club, String> createClubTableName; // Column for the club details
    @FXML
    public Label updateClubNameError;  // Label for the club name error
    @FXML
    public Label updateClubDescriptionError; // Label for the club description error
    @FXML
    public TextField updateClubSearch;
    // Table and columns for updating club
    @FXML
    public TableView<Club> updateClubDetailsTable; // Table for the club details
    @FXML
    public TableColumn<Club, String> updateClubTableDescription; // Column for the club details
    @FXML
    public TableColumn<Club, Integer> updateClubTableId; // Column for the club details
    @FXML
    public TableColumn<Club, String> updateClubTableLogo; // Column for the club details
    @FXML
    public TableColumn<Club, String> updateClubTableName;
    @FXML
    public TextField updateClubID; // Text field for the club ID
    @FXML
    public TextField updateClubName; // Text field for the club name
    @FXML
    public TextArea updateClubDescription; // Text area for the club description
    @FXML
    public ImageView updateClubImage; // Image view for the club logo
    @FXML
    public Button updateClubImageButton; // Button for uploading the club logo

  // setting labels from FXML

    @FXML
    public Button updateClubButton; // Button for updating the club details
  

    @FXML
    protected Label updateErrorLabelEventLocation; // Label for the event location error

    @FXML
    protected Label updateErrorLabelEventName; // Label for the event name error

    @FXML
    protected Label updateErrorLabelEventType; // Label for the event type error

    @FXML
    protected Label updateErrorLabelEventDate; // Label for the event date error

    @FXML
    protected Label updateErrorLabelDeliveryType; // Label for the event delivery type error

    @FXML
    protected Label updateErrorLabelEventDescription; // Label for the event description error

    @FXML
    protected Label scheduleErrorLabelClubName; // Label for the club name error

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
    protected TextField cancelEventSearchBar; // Search bar for searching cancelled events

    @FXML
    protected TextField updateEventSearchBar; // Search bar for searching updated events

    @FXML
    protected Button scheduleEventSearchButton; // Button for searching scheduled events

    @FXML
    protected TextField createdEventSearchBar; // Search bar for searching created events

    @FXML
    protected Label numberOfScheduledEvents; // Label for the number of scheduled events

    @FXML
    protected Label nextEventDate; // Label for the next event date

    @FXML
    protected Label numberOfClubs; // Label for the number of clubs

    @FXML
    protected Button GoToRegistrationButton; // Button for going to the registration pane

    @FXML
    protected AnchorPane RegistrationReportPane; // Pane for the registration report

    @FXML
    protected TableView<Attendance> tb1; // Table for the attendance tracker

    @FXML
    protected TableColumn<Attendance, Boolean> atColumn; // Column for the attendance tracker

    @FXML
    protected TableColumn<Attendance, CheckBox> stColumn; // Column for the attendance tracker

    @FXML
    public TextField profileAdvisorUsername; // Text field for the username
    @FXML
    public TextField profileAdvisorLname; // Text field for the last name
    @FXML
    public TextField profileAdvisorId;  // Text field for the ID
    @FXML
    public TextField profileAdvisorFname; // Text field for the first name
    @FXML
    public TextField profileAdvisorCnumber; // Text field for the contact number
    @FXML
    public Label profileAdvisorCnumberError; // Label for the contact number error
    @FXML
    public Label profileAdvisorFnameError; // Label for the first name error
    @FXML
    public Label profileAdvisorIdError; // Label for the ID error
    @FXML
    public Label profileAdvisorLnameError; // Label for the last name error
    @FXML
    public Label profileAdvisorUsernameError; // Label for the username error

    @FXML
    public PasswordField profileAdvisorExistingpw; // Password field for the existing password
    @FXML
    public Label profileAdvisorExistingpwError; // Label for the existing password error
    @FXML
    public PasswordField profileAdvisorNewpw; // Password field for the new password
    @FXML
    public Label profileAdvisorNewpwError; // Label for the new password error
    @FXML
    public PasswordField profileAdvisorConfirmpw; // Password field for the confirm password
    @FXML
    public Label profileAdvisorConfirmpwError; // Label for the confirm password error
    @FXML
    public ComboBox<String> clubMembershipCombo; // Combo box for the club membership
    
    @FXML
    protected BarChart<?, ?> GenderRatioChart; // Bar chart

    @FXML
    protected CategoryAxis GenderOfStudent; // Category axis for the bar chart

    @FXML
    protected NumberAxis NumberOfStudents; // Number axis for the bar chart

    @FXML
    protected BarChart<?, ?> EnrollStudentCountEachGrade; // Bar chart for the enrollment count of each grade

    @FXML
    protected CategoryAxis gradeDetail; // Category axis for the bar chart

    @FXML
    protected NumberAxis NumberOfStudentsEachGrade; // Number axis for the bar chart
    @FXML
    protected ComboBox<String> attendanceClubNameComboBox; // Combo box for the club name

    @FXML
    protected ComboBox<String> attendanceEventNameComboBox; // Combo box for the event name

    @FXML
    protected TableColumn<Attendance, String> attendanceClubNameColumn; // Column for the attendance tracker

    @FXML
    protected TableColumn<Attendance, String> attendanceEventNameColumn; // Column for the attendance tracker

    @FXML
    protected TableColumn<Attendance, Integer> attendanceStudentAdmissionNumColumn; // Column for the attendance tracker

    @FXML
    protected TableColumn<Attendance, String> attendanceStudentNameColumn; // Column for the attendance tracker

    @FXML
    protected TableColumn<Attendance, CheckBox> attendanceStatusColumn; // Column for the attendance tracker

    @FXML
    protected TableView<Attendance> attendanceTrackerTable; // Table for the attendance tracker
    @FXML
    public TableView<Event> generateReportEventViewTable; // Table for the event view
    @FXML
    public TableColumn<Event, String> generateReportClubName; // Column for the event view

    @FXML
    public TableColumn<Event, String> generateReportEventName; // Column for the event view

    @FXML
    public TableColumn<Event, LocalDate> generateReportEventDate; // Column for the event view

    @FXML
    public TableColumn<Event, LocalTime> generateReportEventTime; // Column for the event view

    @FXML
    public TableColumn<Event, String> generateReportEventLocation; // Column for the event view

    @FXML
    public TableColumn<Event, String> generateReportEventType; // Column for the event view

    @FXML
    public TableColumn<Event, String> generateReportDeliveryType; // Column for the event view

    @FXML
    public TableColumn<Event, String> generateReportEventDescription; // Column for the event view

    @FXML
    public ComboBox<String> generateReportClubNameComboBox; // Combo box for the club name

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
    public Label membershipReportNumber;
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
    protected Label userCountLabel;

    @FXML
    protected ComboBox<String> ReportAttendanceClubName;

    @FXML
    protected ComboBox<String> ReportAttendanceEventName;

    @FXML
    protected TableColumn<Attendance, String> generateReportAttendanceClubName;

    @FXML
    protected TableColumn<Attendance, String> generateReportAttendanceEventName;

    @FXML
    protected TableColumn<Attendance, Integer> generateReportAttendanceAdmissionNum;

    @FXML
    protected TableColumn<Attendance, String> generateReportAttendanceStudentName;

    @FXML
    protected TableColumn<Attendance, String> generateReportAttendanceStatus;

    @FXML
    protected TableView<Attendance> generateReportAttendanceTable;

    @FXML
    protected Label totalAbsentStudents;

    @FXML
    protected Label totalAttendedStudents;
    @FXML
    protected Label totalStudentCountAttendance;

    @FXML
    protected Button generateReportAttendanceButton;
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