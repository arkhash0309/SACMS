// Import statements for required classes and packages
package StudentDashboardManager;
import ClubManager.Club;
import ClubManager.Event;
import com.example.clubmanagementsystem.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

// Abstract class representing the controller for the student dashboard

// work done by- Lakshan, Arkhash and Deelaka
abstract public class StudentDashboardController implements Initializable {
    // Variables to store mouse position
    protected double xPosition;  // x position of the mouse
    protected double yPosition; // y position of the mouse

    // FXML elements defined in the associated FXML file
    @FXML
    protected StackPane StudentDashboard; // Stack pane for the student dashboard
    protected Scene scene; // Scene for the student dashboard
    protected Stage stage; // Stage for the student dashboard
    private Parent root; //
    @FXML
    protected AnchorPane EventStudentPane; // Anchor pane for the event student pane
    @FXML
    protected AnchorPane JoinLeaveClubPane; // Anchor pane for the join leave club pane
    @FXML
    protected AnchorPane StudentDashBoardPane; // Anchor pane for the student dashboard pane
    @FXML
    protected AnchorPane StudentProfilePane; // Anchor pane for the student profile pane
    @FXML
    public TextField studentUpdateProfileID; // Text field for the student update profile ID
    @FXML
    public TextField studentUpdateProfileUserName; // Text field for the student update profile user name
    @FXML
    public TextField studentUpdateProfileContactNum; // Text field for the student update profile contact number
    @FXML
    public PasswordField studentUpdateProfileExistingPassword; // Password field for the student update profile existing password
    @FXML
    public PasswordField studentUpdateProfileNewPassword; // Password field for the student update profile new password
    @FXML
    public PasswordField studentUpdateProfileConfirmPassword; // Password field for the student update profile confirm password
    @FXML
    public TextField studentUpdateProfileFName; // Text field for the student update profile first name
    @FXML
    public TextField studentUpdateProfileLName; // Text field for the student update profile last name
    // Labels for the student update profile pane
    @FXML
    public Label studentUpdateIDLabel, studentUpdateFNameLabel, studentUpdateLNameLabel, studentUpdateUserNameLabel,
            studentUpdateContactNumLabel, studentUpdateExistingPasswordLabel, studentUpdateNewPasswordLabel,
            studentUpdateConfirmPasswordLabel, updateGradeLabel;
    // Buttons for the student update profile pane
    @FXML
    public ComboBox<String> studentUpdateProfileGrade;
    // Buttons for the student update profile pane
    @FXML
    public Button dashboardButton;
    // Buttons for the student update profile pane
    @FXML
    protected Button ViewEventButton;
    // Buttons for the student update profile pane
    @FXML
    protected Button ManageclubButton;
    // Buttons for the student update profile pane
    @FXML
    protected Button ProfileDirectorButton;
    // Buttons for the student update profile pane
    @FXML
    protected ComboBox<String> studentJoinClubDropDownList;
    // Buttons for the student update profile pane
    @FXML
    protected TextField studentJoinClubName;
    // Button
    @FXML
    protected TextField studentJoinClubID;
    // Buttons for the student update profile pane
    @FXML
    protected TextField studentJoinClubAdvisorName;
    // Table and columns for leaving a club
    @FXML
    protected TableView<Club> leaveClubTable; // Table for leaving a club
    // Table and columns for leaving a club
    @FXML
    protected TableColumn<Club, Integer> leaveClubClubIdColumn;
    // Table and columns for leaving a club
    @FXML
    protected TableColumn<Club, String> leaveClubClubNameColumn;
    // Table and columns for leaving a club
    @FXML
    protected TableColumn<Club, String> leaveClubClubAdvisorName;
    // Table and columns for leaving a club
    @FXML
    protected TextField studentLeaveClubSearch;

    // Table and columns for viewing events
    @FXML
    protected TableView<Event> EventViewTableStudent;
    // Table and columns for viewing events
    @FXML
    protected TableColumn<Event, String> studentViewClubNameColumn;
    // Table and columns for viewing events
    @FXML
    protected TableColumn<Event, String> studentViewEventNameColumn;
    // Table and columns for viewing events
    @FXML
    protected TableColumn<Event, LocalDate> studentViewEventDateColumn;
    // Table and columns for viewing events
    @FXML
    protected TableColumn<Event, LocalTime> studentViewEventTimeColumn;
    // Table and columns for viewing events
    @FXML
    protected TableColumn<Event, String> studentViewEventLocationColumn;
    // Table and columns for viewing events
    @FXML
    protected TableColumn<Event, String> studentViewEventTypeColumn;
    // Table and columns for viewing events
    @FXML
    protected TableColumn<Event, String> studentViewDeliveryTypeColumn;
    // Table and columns for viewing events
    @FXML
    protected TableColumn<Event, String> studentViewEventDescriptionColumn;
    // Table and columns for viewing events
    @FXML
    protected ComboBox<String> studentEventSelector;
    // Table and columns for viewing events
    @FXML
    protected Label UpcomingEventForStudent;
    // Table and columns for viewing events
    @FXML
    protected Label EnrolledClubCountStudent; // Label for the enrolled club count for student
    @FXML
    protected Label  nextEventDateForStudent; // Label for the next event date for student
    @FXML
    public  Label showUserName; // Label for showing the user name

    // Bar chart for displaying upcoming event rates
    @FXML
    public BarChart<?, ?> UpcomingEventRateForTable; // Bar chart for displaying upcoming event rates

    @FXML
    public CategoryAxis clubNameForEvent; // Category axis for the club name for event

    @FXML
    public NumberAxis NumberOfEvents; // Number axis for the number of events
    @FXML
    abstract void StudentLogout(MouseEvent event) throws IOException; //

    // Abstract methods for handling drag and drop events
    abstract public void StudentDashboardDragDetected(MouseEvent mouseEvent);

    // Abstract methods for pane pressed events
    abstract public void StudentPanePressed(MouseEvent mouseEvent);

    // Abstract methods for minimizing the pane
    @FXML
    abstract void MinimizePane(ActionEvent event);

    // Abstract methods for close the pane
    @FXML
    abstract void ClosePane(ActionEvent event);

    // Abstract methods for navigating the dashboard and interacting with clubs and events
    abstract public void makeAllStudentDashBoardPanesInvisible();

    // Abstract methods for navigating the dashboard and interacting with clubs and events
    @FXML
    abstract void GoToDashBoard(ActionEvent event);

   // Abstract methods for navigating the dashboard and interacting with clubs and events
    @FXML
    abstract public void GoToJoinLeaveClub(ActionEvent actionEvent) throws ClassNotFoundException, SQLException;
    // Abstract methods for navigating the dashboard and interacting with clubs and events
    @FXML
    abstract public void GoToEvents(ActionEvent actionEvent);

    // make all student buttons coloured
    abstract public void makeAllStudentButtonsColoured();
}