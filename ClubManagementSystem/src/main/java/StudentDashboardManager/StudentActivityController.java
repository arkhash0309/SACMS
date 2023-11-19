package StudentDashboardManager;

import ClubManager.Club;
import ClubManager.Event;
import SystemUsers.ClubAdvisor;
import SystemUsers.Student;
import SystemUsers.User;
import com.example.clubmanagementsystem.ApplicationController;
import com.example.clubmanagementsystem.HelloApplication;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentActivityController extends StudentDashboardController{

    public static boolean validStat = true;
    static int studentAdmissionNum;

    public static String existingUserName;

    static int clubIndexStudentLeave;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int grade = 6; grade<14; grade++) {
            studentUpdateProfileGrade.getItems().add(String.format("%02d", grade));
        }

        studentUpdateProfileGrade.getSelectionModel().selectFirst();

        displayNumberOfEnrolledClubs();
        displayNumberOfUpcomingEvents();
        findNextEventDateForStudent();

        studentEventSelector.getItems().add("All Clubs");
        studentEventSelector.getSelectionModel().selectFirst();

        leaveClubClubIdColumn.setCellValueFactory(new PropertyValueFactory<>("clubId"));
        leaveClubClubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        leaveClubClubAdvisorName.setCellValueFactory(new PropertyValueFactory<>("clubAdvisorName"));

        // the columns are initialized for the event view table
        studentViewClubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        studentViewEventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        studentViewEventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        studentViewEventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("eventTime"));
        studentViewEventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));
        studentViewEventTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        studentViewDeliveryTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventDeliveryType"));
        studentViewEventDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
    }
    @Override
    void StudentLogout(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginDashboardManager/StudentLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void StudentDashboardDragDetected(MouseEvent mouseEvent) {
        Stage stage =  (Stage)StudentDashboard.getScene().getWindow();
        stage.setX(mouseEvent.getScreenX()- xPosition);
        stage.setY(mouseEvent.getScreenY() - yPosition);
    }

    @Override
    public void StudentPanePressed(MouseEvent mouseEvent) {
        xPosition = mouseEvent.getSceneX();
        yPosition = mouseEvent.getSceneY();
    }

    @Override
    void MinimizePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(StudentDashboard);
    }


    @Override
    void ClosePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    @Override
    public void makeAllStudentDashBoardPanesInvisible(){
        EventStudentPane.setVisible(false);
        JoinLeaveClubPane.setVisible(false);
        StudentDashBoardPane.setVisible(false);
        StudentProfilePane.setVisible(false);
    }

    @Override
    void GoToDashBoard(ActionEvent event) {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        StudentDashBoardPane.setVisible(true);
        dashboardButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        displayNumberOfEnrolledClubs();
        displayNumberOfUpcomingEvents();
        findNextEventDateForStudent();
    }

    @Override
    public void GoToJoinLeaveClub(ActionEvent actionEvent) {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        JoinLeaveClubPane.setVisible(true);
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        getCreatedClubs();
        populateLeaveClubDetails();
    }


    @Override
    public void GoToEvents(ActionEvent actionEvent) {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        EventStudentPane.setVisible(true);
        ViewEventButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        populateAllEvents();
        populateStudentJoinedClubsComboBox();
    }

    @FXML
    void studentProfileDirector(ActionEvent event) {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        StudentProfilePane.setVisible(true);
        ProfileDirectorButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }


    public void onStudentProfileUpdateButtonClick() {
        validStat = true; // boolean value is set to true;

        // the respective entries are retrieved from the text fields and combo boxes
        String updatedAdmissionNumber = studentUpdateProfileID.getText();
        String updatedFirstName = studentUpdateProfileFName.getText();
        String updatedLastName = studentUpdateProfileLName.getText();
        String updatedUserName = studentUpdateProfileUserName.getText();
        String updatedContactNum = studentUpdateProfileContactNum.getText();
        String updatedGrade = studentUpdateProfileGrade.getValue();
        System.out.println("Grade is " + updatedGrade);

//        if(updatedGrade.isEmpty()){
//            updateGradeLabel.setText("Please select your grade");
//            return;
//        }

        // an object of data type Student is created
        Student student = new Student(studentUpdateProfileUserName.getText(), studentUpdateProfileExistingPassword.getText(),
                studentUpdateProfileFName.getText(), studentUpdateProfileLName.getText());

        // the Strings are set to correct initially
        Student.fNameValidateStatus = "correct";
        Student.lNameValidateStatus = "correct";
        Student.contactNumberValidateStatus = "correct";
        Student.passwordValidateStatus = "correct";
        Student.userNameValidateStatus = "correct";

        // the first name is validated using the validateFirstName method
        if (!student.validateFirstName()) {
            System.out.println("Incorrect First Name.");
            System.out.println(Student.fNameValidateStatus + " : First Name");
            validStat = false; // boolean value is set to false
        }
        displayNameError("firstName"); // method is called to display error messages related to first name


        // the last name is validated using the validateLastName method
        if (!student.validateLastName()) {
            System.out.println("Incorrect Last Name.");
            System.out.println(Student.lNameValidateStatus);
            validStat = false; // boolean value is set to false
        }
        displayNameError("lastName"); // method is called to display error messages related to last name


        // the admission number is validated using a try catch approach
        try{
            // check if the field is empty
            if (updatedAdmissionNumber.isEmpty()) {
                validStat = false; // boolean value is set to false
                Student.admissionNumStatus = "empty"; // error is specified for the error message
                throw new Exception(); // an exception is thrown
            }
            int admissionNumValue = Integer.parseInt(updatedAdmissionNumber); // the string value is converted into an integer
            Student std2 = new Student(admissionNumValue); // an object of data type Student is created

            // the validateStudentAdmissionNumber method is passed to check for the length of the entry
            if (!std2.validateStudentAdmissionNumber()) {
                System.out.println("Invalid");
                validStat = false; // boolean value is set to false
            } else {
                Student.admissionNumStatus = ""; // empty string assigned
            }
        }
        // numberFormatExceptions are caught
        catch (NumberFormatException e) {
              Student.admissionNumStatus = "format"; // error is specified to display error message
              System.out.println("Invalid Student ID");
               validStat = false; // boolean value is set to false
        } catch (Exception e) {
            validStat = false; // boolean value is set to false
        }
        displayAdmissionNumError(); // the method is called to display error message related to admission numbers


        // a try catch approach is taken to validate the contact number
        try{
            String tempContactNum = updatedContactNum; // the contact number value is stored temporarily in another variable
            // check if the contact number is empty
            if (tempContactNum.isEmpty()) {
                User.contactNumberValidateStatus = "empty"; // error is specified to display error message
                throw new Exception(); // an exception is thrown
            }
            Double.parseDouble(updatedContactNum.trim()); // whitespaces are removed using trim and the string is converted to a double

            Student std1 = new Student(tempContactNum);// an object of data type Student is created passing the contact number

            // the value is validated using the validateContactNumber method
            if (!std1.validateContactNumber()) {
                validStat = false; // boolean value is set to false
                System.out.println("Invalid Contact Number 1");
            } else {
                User.contactNumberValidateStatus = ""; // empty string assigned
            }
        }
        // numberFormatExceptions are caught
        catch(NumberFormatException e) {
            System.out.println("Invalid ContactNumber 2");
            User.contactNumberValidateStatus = "format"; // error is specified to display error message
            validStat = false; // boolean value is set to false
        } catch (Exception e) {
            validStat = false; // boolean value is set to false
        }
        displayContactNumError(); // the method is called to display error message related to contact numbers


        if (!student.validateUserName("updation", "student")) {
            System.out.println("Wrong user name.");
            validStat = false; // boolean value is set to false
        } else {
            User.userNameValidateStatus = ""; // empty string assigned
        }
        displayUserNameError(); // the method is called to display error message related to usernames


        System.out.println(validStat + " : Valid Stat");
        // if the boolean value of validStat is true
        if (validStat) {
            System.out.println("Not implemented yet");
//            Student.studentDetailArray.set()
//            Student updateDataStudent = new Student(updatedUserName, updatedPassword, updatedFirstName, updatedLastName);
//            Student.studentDetailArray.add(updateDataStudent);
        }
        System.out.println("\n\n\n"); // three blank lines
    }

    public void onStudentProfilePasswordChangeButtonClick() throws SQLException {
        String existingPassword = this.studentUpdateProfileExistingPassword.getText(); // existing password is retrieved
        String updatedPassword = this.studentUpdateProfileNewPassword.getText(); // new password is retrieved
        String updateConfirmPassword = this.studentUpdateProfileConfirmPassword.getText(); // confirmation is retrieved

        Student newStd = new Student(updatedPassword); // an object of data type Student is created

        if (!newStd.validatePassword("update")) {
            System.out.println("Wrong password.");
            validStat = false; // boolean value is set to false
        } else {
            User.passwordValidateStatus = ""; // empty string assigned
        }
        displayPasswordError(); // the method is called to display error message related to password


        if (updatedPassword.isEmpty()) {
            User.passwordValidateStatus = "empty"; // error is specified to display error message
            validStat = false; // boolean value is set to false
        }
        // check if the confirmation password field is empty
        if (updateConfirmPassword.isEmpty()) {
            studentUpdateConfirmPasswordLabel.setText("Cannot be empty."); // error label text is set
            validStat = false; // boolean value is set to false

        }
        // check to see if the new password and the confirmation password match
        else if (!updateConfirmPassword.equals(updatedPassword)) {
            studentUpdateConfirmPasswordLabel.setText("Passwords do not match"); // error label text is set
            validStat = false; // boolean value is set to false
        } else {
            studentUpdateConfirmPasswordLabel.setText(""); // valid entries
        }

        // if the boolean value is true, the new password is set to the existing password
        if (validStat) {
            studentUpdateProfileExistingPassword.setText(updatedPassword);
        }
    }


    // method to display error messages related to first and last name
    public void displayNameError(String nameType) {
        // checks if firstName is called
        if (nameType.equals("firstName")) {
            // check if the field is empty
            if (Student.fNameValidateStatus.equals("empty")) {
                studentUpdateFNameLabel.setText("First Name cannot be empty."); // error message is set respectively
            }
            // checks if the format is correct
            else if (Student.fNameValidateStatus.equals("format")) {
                studentUpdateFNameLabel.setText("First Name can contain only letters."); // error message is set respectively
            }
            // if the entry is valid
            else {
                studentUpdateFNameLabel.setText(""); // no error
            }

        }
        // checks if lastName is called
        else if (nameType.equals("lastName")) {
            // check if the field is empty
            if (Student.lNameValidateStatus.equals("empty")) {
                studentUpdateLNameLabel.setText("Last Name cannot be empty."); // error message is set respectively
            }
            // checks if the format is correct
            else if (Student.lNameValidateStatus.equals("format")) {
                studentUpdateLNameLabel.setText("Last name can contain only letters."); // error message is set respectively
            }
            // if the entry is valid
            else {
                studentUpdateLNameLabel.setText(""); // no error
            }
        }
    }


    // method to display error messages related to contact number
    public void displayContactNumError() {
        // check if the field is empty
        if (User.contactNumberValidateStatus.equals("empty")) {
            studentUpdateContactNumLabel.setText("Contact number cannot be empty."); // error message is set respectively
        }
        // checks if the length is valid
        else if (User.contactNumberValidateStatus.equals("length")){
            studentUpdateContactNumLabel.setText("Contact number should be 10 digits."); // error message is set respectively
        }
        // checks if the format is valid
        else if (User.contactNumberValidateStatus.equals("format")) {
            studentUpdateContactNumLabel.setText("It should contain only numbers."); // error message is set respectively
        }
        // if the entry is valid
        else {
            studentUpdateContactNumLabel.setText(""); // no error
        }
    }


    // method to display error messages related to admission number
    public void displayAdmissionNumError() {
        // check if the field is empty
        if (Student.admissionNumStatus.equals("empty")) {
            studentUpdateIDLabel.setText("Admission Number cannot be empty."); // error message is set respectively
        }
        // checks if the length is valid
        else if (Student.admissionNumStatus.equals("length")) {
            studentUpdateIDLabel.setText("Admission Number has to be 6 digits."); // error message is set respectively
        }
        // checks if the admission number already exists
        else if (Student.admissionNumStatus.equals("exist")) {
            studentUpdateIDLabel.setText("Admission Number already exists."); // error message is set respectively
        }
        // checks if the format is valid
        else if (Student.admissionNumStatus.equals("format")) {
            studentUpdateIDLabel.setText("Admission Number contain only numbers."); // error message is set respectively
        }
        // if the entry is valid
        else {
            studentUpdateIDLabel.setText(""); // no error
        }
    }


    // method to display error messages related to username
    public void displayUserNameError() {
        // check if the field is empty
        if (User.userNameValidateStatus.equals("empty")) {
            studentUpdateUserNameLabel.setText("User name cannot be empty."); // error message is set respectively
        }
        // checks if the username already exists
        else if (Student.userNameValidateStatus.equals("exists")) {
            studentUpdateUserNameLabel.setText("Entered username already exists."); // error message is set respectively
        }
        // checks if the field has spaces
        else if (User.userNameValidateStatus.equals("blank")) {
            studentUpdateUserNameLabel.setText("Username cannot contain spaces."); // error message is set respectively
        }
        // checks if the length is valid
        else if (User.userNameValidateStatus.equals("length")) {
            studentUpdateUserNameLabel.setText("The length should be 5 to 10 characters."); // error message is set respectively
        }
        // if the entry is valid
        else {
            studentUpdateUserNameLabel.setText(""); // no error
        }
    }


    // method to display error messages related to password
    public void displayPasswordError() {
        // check if the field is empty
        if (User.passwordValidateStatus.equals("empty")) {
            studentUpdateNewPasswordLabel.setText("Password cannot be empty."); // error message is set respectively
        }
        // checks if the format is valid
        else if (User.passwordValidateStatus.equals("format")) {
            studentUpdateNewPasswordLabel.setText("Password should consists of 8 characters including numbers and special characters."); // error message is set respectively
        }
        // if the entry is valid
        else {
            studentUpdateNewPasswordLabel.setText(""); // no error
        }
    }


    public void getCreatedClubs(){
        if(!studentJoinClubDropDownList.getItems().contains("None")){
            studentJoinClubDropDownList.getItems().add("None");
        }

        for(Club club: Club.clubDetailsList){
            String clubName;
            clubName = club.getClubName();

            boolean viewContainsStatus = studentJoinClubDropDownList.getItems().contains(clubName);


            if(!viewContainsStatus){
                studentJoinClubDropDownList.getItems().add(clubName);
            }

        }

        studentJoinClubDropDownList.getSelectionModel().selectFirst(); // the selected value is retrieved
    }

    @FXML
    // method to define what happens when the Join/Leave Club button is clicked
    void OnStudentClubSelection(ActionEvent event) {
        // before the student selects the club, the text field are kept empty
        studentJoinClubID.setText(" ");
        studentJoinClubName.setText(" ");
        studentJoinClubAdvisorName.setText(" ");

        String selectedClub = studentJoinClubDropDownList.getSelectionModel().getSelectedItem(); // the selected value is retrieved

        // if no club is selected
        if(!selectedClub.equals("None")) {
            // for each club in the clubDetailsList Array list
            for (Club club : Club.clubDetailsList) {
                // if the club name is the name of the selected club
                if (club.getClubName().equals(selectedClub)) {
                    // the club ID is retrieved, converted to String, and set to the text field
                    studentJoinClubID.setText(String.valueOf(club.getClubId()));
                    studentJoinClubName.setText(club.getClubName()); // the name if retrieved and set to the text field

                    // for each club advisor in the clubAdvisorDetailsList Array list
                    for (ClubAdvisor advisor : ClubAdvisor.clubAdvisorDetailsList) {
                        System.out.println("bn");
                        // for each club in the createdClubDetailsList
                        for (Club clubName : advisor.createdClubDetailsList) {
                            System.out.println("Incharge clubName ");
                            // if the club name is the same as the club selected
                            if (clubName.getClubName().equals(selectedClub)) {
                                // the first and last name fo the club advisor is set to the text field
                                studentJoinClubAdvisorName.setText(advisor.getFirstName() + " " + advisor.getLastName());
                                System.out.println("Incharge clubName " + "Hello");
                                break; // the conditional statement is terminated
                            }
                        }
                    }
                }
            }
        }
    }

    @FXML
    void joinClubController(){
       String clubToJoin = studentJoinClubDropDownList.getSelectionModel().getSelectedItem(); // the selected value is retrieved

        // if no club to join is selected
       if(clubToJoin.equals("None")){
           // an alert is generated
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("School Club Management System"); // the title is set for the error message
           alert.setHeaderText("Please select a club to join with a club"); // the content is set for the error message
           alert.show(); // the  alert is displayed
       }else{
           for(Club club : Student.studentJoinedClubs){
               // if the club name matches the selected name
               if(club.getClubName().equals(clubToJoin)){
                   // alert is generated
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("School Club Management System"); // the title is set for the error message
                   alert.setHeaderText("You have already joined with this club"); // the content is set for the error message
                   alert.show(); // the alert is displayed
                   return; // the loop is exited
               }
           }

           // for each entry in the clubDetailsList Array list
           for(Club club : Club.clubDetailsList){
               // if the club name matches the selected name
               if(club.getClubName().equals(clubToJoin)){
                   Student student = new Student(); // a new object of data type Student is created
                   student.joinClub(club); // the student is added to the respective club
                   populateLeaveClubDetails(); // the leave club table is populated
                   populateStudentEvents(); // the student events table is populated
                   return;
               }
           }
       }

    }

    public void populateLeaveClubDetails(){
        leaveClubTable.getItems().clear();
        for(Club club : Student.studentJoinedClubs){
            Club clubs = new Club(club.getClubId(), club.getClubName(), club.getClubDescription(), club.getClubLogo());
            ObservableList<Club> viewJoinedClubs = leaveClubTable.getItems();
            viewJoinedClubs.add(clubs);
            leaveClubTable.setItems(viewJoinedClubs);
        }
    }

    @FXML
    void leaveClubController(ActionEvent event){
        leaveClubController();
    }


    public void leaveClubController(){
        try{
            Club selectedClub = leaveClubTable.getSelectionModel().getSelectedItem();
            clubIndexStudentLeave = leaveClubTable.getSelectionModel().getSelectedIndex();
            System.out.println(selectedClub.getClubName());

            Alert cancelEvent = new Alert(Alert.AlertType.CONFIRMATION);
            cancelEvent.initModality(Modality.APPLICATION_MODAL);
            cancelEvent.setTitle("School Activity Club Management System");
            cancelEvent.setHeaderText("Do you really want to leave the club ?");

            Optional<ButtonType> result = cancelEvent.showAndWait();
            if(result.get() != ButtonType.OK){
                return;
            }

            Student student = new Student();
            student.leaveClub(selectedClub, clubIndexStudentLeave);
            populateLeaveClubDetails();

            Iterator<Event> iterator = Student.studentEvent.iterator();
            while (iterator.hasNext()) {
                Event event = iterator.next();
                if (event.getClubName().equals(selectedClub.getClubName())) {
                    iterator.remove();
                }
            }

        }catch(NullPointerException error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("School Club Management System");
            alert.setHeaderText("Select a club from table to leave a club");
            alert.show();
        }
    }


    @FXML
    void searchJoinedClubs(ActionEvent event) {
        searchJoinedClubs(leaveClubTable, studentLeaveClubSearch);
    }

    public void searchJoinedClubs(TableView<Club> tableView, TextField searchBar){

        String clubName = searchBar.getText();
        System.out.println(clubName);

        Club foundClub = null;
        boolean foundStat = false;
        int count = 0;
        for(Club clubVal : Student.studentJoinedClubs){
            if(clubVal.getClubName().equals(clubName)){
                foundClub = clubVal;
                System.out.println(foundClub.getClubName());
                foundStat = true;
                break;
            }
            count++;
        }

        if(foundStat){
                tableView.getSelectionModel().select(count);
                clubIndexStudentLeave = tableView.getSelectionModel().getSelectedIndex();
                System.out.println(clubIndexStudentLeave);
                tableView.scrollTo(foundClub);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("School Club Management System");
            alert.setHeaderText("The club " + clubName+ " does not found");
            alert.showAndWait();
            System.out.println(foundStat);
        }

    }

    // method to populate the student events table
    public void populateStudentEvents(){
        Student.studentEvent.clear(); // the array list is cleared
        // for each entry in the studentJoinedClubs Array list
        for(Club club: Student.studentJoinedClubs){
            // for each entry in the eventDetails Array list
            for(Event event : Event.eventDetails){
                // if the club name is the same as the club selected
                if(event.getClubName().equals(club.getClubName())){
                    Student.studentEvent.add(event); // the event is added
                }
            }
        }
    }


    // method to populate the combo box for the joined clubs
    public void populateStudentJoinedClubsComboBox(){
        studentEventSelector.getItems().clear(); // the items in the combo box are cleared

        // if the combo box does not contain "All clubs"
        if(!studentEventSelector.getItems().contains("All clubs")){
            studentEventSelector.getItems().add("All clubs"); // the option "All clubs" is added
        }

        // for each entry in studentJoinedClubs Array list
        for(Club club : Student.studentJoinedClubs){
            studentEventSelector.getItems().add(club.getClubName()); // each club name student is part of is added to the combo box
        }
        studentEventSelector.getSelectionModel().selectFirst(); // the selected value is retrieved
    }

    @FXML
    // the student events table view method
    void populateStudentViewEventTable(ActionEvent event) {
       populateStudentViewEventTable(); // populate method is called
    }


    // method to populate the student event table
    public void populateStudentViewEventTable(){
        EventViewTableStudent.getItems().clear(); // the table data is cleared
        ArrayList<Event> filteredEvents = new ArrayList<>(); // an array list of data type Event is created
        String selectedClub = studentEventSelector.getSelectionModel().getSelectedItem(); // the value from the combo box is assigned to a variable
        System.out.println(selectedClub + " bro");

        // if no value is selected
        if(selectedClub == null){
            return; // the method is exited
        }

        // if all clubs is selected
        if(selectedClub.equals("All Clubs")){
            populateAllEvents(); // all events are displayed
            return; // the method is exited
        }else{
            // for each entry in the eventDetails Array list
            for(Event events : Event.eventDetails){
                // if the club name is the same as the club selected
                if(events.getClubName().equals(selectedClub)){
                    filteredEvents.add(events); // the respective events of the club are added
                }
            }
        }

        ClubAdvisor clubAdvisor = new ClubAdvisor(); // an object of data type ClubAdvisor is created
        clubAdvisor.viewEvent(); // Override scene eka

        // for each entry in the filteredEvents Array list
        for(Event value : filteredEvents){
            Club hostingClubDetail = value.getHostingClub();
            // the details of the required event are retrieved
            Event requiredEvent = new Event(value.getEventName(), value.getEventLocation(),
                    value.getEventType(),value.getEventDeliveryType(), value.getEventDate(),
                    value.getEventTime(), hostingClubDetail, value.getEventDescription());

            // an observable list is created of data type Event
            ObservableList<Event> viewScheduledEvents = EventViewTableStudent.getItems();
            viewScheduledEvents.add(requiredEvent); // the details of the required event is added to the observable list
            EventViewTableStudent.setItems(viewScheduledEvents); // the details are set to the table
        }

    }

    public void populateAllEvents(){
        for(Event value : Student.studentEvent){
            Club hostingClubDetail = value.getHostingClub();
            Event requiredEvent = new Event(value.getEventName(), value.getEventLocation(),
                    value.getEventType(),value.getEventDeliveryType(), value.getEventDate(),
                    value.getEventTime(), hostingClubDetail, value.getEventDescription());

            ObservableList<Event> viewScheduledEvents = EventViewTableStudent.getItems();
            viewScheduledEvents.add(requiredEvent);
            EventViewTableStudent.setItems(viewScheduledEvents );
        }
    }

    // method to display number of enrolled clubs on the dashboard
    public void displayNumberOfEnrolledClubs(){
      EnrolledClubCountStudent.setText(String.valueOf(Student.studentJoinedClubs.size())); // the value is set to the label
    }


    // method to display number of upcooming events on the dashboard
    public void displayNumberOfUpcomingEvents(){
        int count = 0; // counter is initially set to zero
        // for each event in the studentEvent Array list
        for(Event event : Student.studentEvent){
            // the counter is increased if the date of the event is after the current date
            if(event.getEventDate().isAfter(LocalDate.now())){
                count ++; // counter increased by one
            }
        }

        UpcomingEventForStudent.setText(String.valueOf(count)); // the value is set to the label
    }


    // method to dsiplay date of the next event on dashboard
    public void findNextEventDateForStudent(){
        // if the studentEvent Array list is empty
        if (Student.studentEvent.isEmpty()) {
            nextEventDateForStudent.setText("   No events"); // the message is displayed saying there are no events
            return; // the method is exited
        }

        LocalDate currentDate = LocalDate.now(); // the current date is retrieved

        LocalDate nextDate = null; // the next event date is initially set as null

        // for each entry in the studentEvent Array list
        for (Event event : Student.studentEvent) {
            LocalDate eventDate = event.getEventDate(); // the event date is retrieved
            // if the date is after or on the current date
            if ((eventDate.isAfter(currentDate) || eventDate.isEqual(currentDate)) &&
                    (nextDate == null || eventDate.isBefore(nextDate))) {
                nextDate = eventDate; // the event date is set as the next event date
            }
        }

        // if the next event date is not null
        if (nextDate != null) {
            nextEventDateForStudent.setText("   " + nextDate); // the date value is set to the label
        }
    }


    @Override
    // styles are set for the student action buttons on the left pane
    public void makeAllStudentButtonsColoured(){
        dashboardButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ViewEventButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ProfileDirectorButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
    }

}
