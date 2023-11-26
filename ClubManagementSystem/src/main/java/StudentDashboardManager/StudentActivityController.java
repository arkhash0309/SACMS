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
import javafx.scene.chart.XYChart;
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
import java.util.*;

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
        displayEventCountPerClub();
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

    // method to validate the entries in updating profile
    public void onStudentProfileUpdateButtonClick() {
        validStat = true; // the boolean value is initially set to true

        // the values are retrieved from the text fields
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

        // an object of data type Student is created to hold the details of the student
        Student student = new Student(studentUpdateProfileUserName.getText(), studentUpdateProfileExistingPassword.getText(),
                studentUpdateProfileFName.getText(), studentUpdateProfileLName.getText());

        // the validateStatus is set to an initial value of correct
        Student.fNameValidateStatus = "correct";
        Student.lNameValidateStatus = "correct";
        Student.contactNumberValidateStatus = "correct";
        Student.passwordValidateStatus = "correct";
        Student.userNameValidateStatus = "correct";

        // the first name is validated
        if (!student.validateFirstName()) {
            System.out.println("Incorrect First Name.");
            System.out.println(Student.fNameValidateStatus + " : First Name");
            validStat = false; // boolean value is set to false
        }
        displayNameError("firstName"); //method to display the error message is called and the error type is specified

        if (!student.validateLastName()) {
            System.out.println("Incorrect Last Name.");
            System.out.println(Student.lNameValidateStatus);
            validStat = false; // boolean value is set to false
        }
        displayNameError("lastName"); //method to display the error message is called and the error type is specified

        // a try catch approach is carried out
        try{
            if (updatedAdmissionNumber.isEmpty()) {
                validStat = false;
                Student.admissionNumStatus = "empty";
                throw new Exception();
            }
            int admissionNumValue = Integer.parseInt(updatedAdmissionNumber);
            Student std2 = new Student(admissionNumValue);

            if (!std2.validateStudentAdmissionNumber()) {
                System.out.println("Invalid");
                validStat = false;
            } else {
                Student.admissionNumStatus = "";
            }
        }

        catch (NumberFormatException e) {
              Student.admissionNumStatus = "format";
              System.out.println("Invalid Student ID");
               validStat = false; // boolean value is set to false
        }
        // exceptions are caught
        catch (Exception e) {
            validStat = false; // boolean value is set to false
        }
        displayAdmissionNumError();


        // a try catch approach is carried out
        try{
            // the contact number is stored in a temporary String variable
            String tempContactNum = updatedContactNum;
            // check if the field is empty
            if (tempContactNum.isEmpty()) {
                User.contactNumberValidateStatus = "empty"; // the respective status is specified
                throw new Exception(); // an exception is thrown
            }
            // the contact number is converted to a double and the value is trimmed to remove leading zeros
            Double.parseDouble(updatedContactNum.trim());
            Student std1 = new Student(tempContactNum); // a new object of data type Student is created by passing the contact number

            // the contact number is passed through the validation method for contact numbers
            if (!std1.validateContactNumber()) {
                validStat = false; // boolean value is set to false
                System.out.println("Invalid Contact Number 1");
            } else {
                User.contactNumberValidateStatus = ""; // the respective status is specified
            }
        }
        // number format exceptions are caught
        catch(NumberFormatException e) {
            System.out.println("Invalid ContactNumber 2");
            User.contactNumberValidateStatus = "format"; // the respective status is specified
            validStat = false; // boolean value is set to false
        }
        // exceptions are caught
        catch (Exception e) {
            validStat = false; // boolean value is set to false
        }
        displayContactNumError(); // method to display the error message for contact number is called

        /* the username is validated by calling the method for username validation and the
        type of validation (update) is specified to ensure the value is validated accordingly*/
        if (!student.validateUserName("update", "student")) {
            System.out.println("Wrong user name.");
            validStat = false; // boolean value is set to false
        } else {
            User.userNameValidateStatus = ""; // the respective status is specified
        }
        displayUserNameError(); // method to display the error message for username is called

        System.out.println(validStat + " : Valid Stat");
        if (validStat) {
            System.out.println("Not implemented yet");
//            Student.studentDetailArray.set()
//            Student updateDataStudent = new Student(updatedUserName, updatedPassword, updatedFirstName, updatedLastName);
//            Student.studentDetailArray.add(updateDataStudent);
        }
        System.out.println("\n\n\n");
    }

    /* the below method is used to validate the change in password in case the user wishes to change it.
    This is done by creating an on action event. */
    public void onStudentProfilePasswordChangeButtonClick() throws SQLException {
        // the values are retrieved from the text fields
        String updatedPassword = this.studentUpdateProfileNewPassword.getText();
        String updateConfirmPassword = this.studentUpdateProfileConfirmPassword.getText();

        // an object of data type Student is created ny passing the updatedPassword value
        Student newStd = new Student(updatedPassword);
        /* the new password is validated by specifying the type of validation (update)
        so that the relevant validations can take place */
        if (!newStd.validatePassword("update")) {
            System.out.println("Wrong password.");
            validStat = false; // boolean value is set to false
        } else {
            User.passwordValidateStatus = "";
        }
        displayPasswordError(); // method to display the error message for password is called

        // the check takes place to see if the fields are empty
        if (updateConfirmPassword.isEmpty()) {
            studentUpdateConfirmPasswordLabel.setText("Cannot be empty."); // the error message is set to the label
            validStat = false; // boolean value is set to false

        }
        // the check takes place to see if the passwords match
        else if (!updateConfirmPassword.equals(updatedPassword)) {
            studentUpdateConfirmPasswordLabel.setText("Passwords do not match"); // the error message is set to the label
            validStat = false; // boolean value is set to false
        }
        // if the entered details are valid
        else {
            studentUpdateConfirmPasswordLabel.setText(""); // the label value is set to empty
        }

        // if the passed boolean value is true
        if (validStat) {
            // the new password is set to the existing password
            studentUpdateProfileExistingPassword.setText(updatedPassword);
        }
    }

    // method to display error messages related to first and last name
    public void displayNameError(String nameType) {
        // if it is the first name
        if (nameType.equals("firstName")) {
            // if the value is empty
            if (Student.fNameValidateStatus.equals("empty")) {
                studentUpdateFNameLabel.setText("First Name cannot be empty.");
            }
            // if the first name contains characters other than letters
            else if (Student.fNameValidateStatus.equals("format")) {
                studentUpdateFNameLabel.setText("First Name can contain only letters.");
            }
            // if a valid entry is made
            else {
                studentUpdateFNameLabel.setText("");
            }
        }
        // if it is the last name
        else if (nameType.equals("lastName")) {
            // if the value is empty
            if (Student.lNameValidateStatus.equals("empty")) {
                studentUpdateLNameLabel.setText("Last Name cannot be empty.");
            }
            // if the first name contains characters other than letters
            else if (Student.lNameValidateStatus.equals("format")) {
                studentUpdateLNameLabel.setText("Last name can contain only letters.");
            }
            // if a valid entry is made
            else {
                studentUpdateLNameLabel.setText("");
            }
        }
    }

    // method to display error messages related to contact num
    public void displayContactNumError() {
        // if the value is empty
        if (User.contactNumberValidateStatus.equals("empty")) {
            studentUpdateContactNumLabel.setText("Contact number cannot be empty.");
        }
        // if the contact number is not 10 digits
        else if (User.contactNumberValidateStatus.equals("length")){
            studentUpdateContactNumLabel.setText("Contact number should be 10 digits.");
        }
        // if the contact number contains characters other than numbers
        else if (User.contactNumberValidateStatus.equals("format")) {
            studentUpdateContactNumLabel.setText("It should contain only numbers.");
        }
        // if a valid entry is made
        else {
            studentUpdateContactNumLabel.setText("");
        }
    }

    // method to display error messages related to admission number
    public void displayAdmissionNumError() {
        // if the value is empty
        if (Student.admissionNumStatus.equals("empty")) {
            studentUpdateIDLabel.setText("Admission Number cannot be empty.");
        }
        // if the admission number is not 6 digits
        else if (Student.admissionNumStatus.equals("length")) {
            studentUpdateIDLabel.setText("Admission Number has to be 6 digits.");
        }
        // if the admission number is already assigned to another individual
        else if (Student.admissionNumStatus.equals("exist")) {
            studentUpdateIDLabel.setText("Admission Number already exists.");
        }
        // if the admission number contains characters other than numbers
        else if (Student.admissionNumStatus.equals("format")) {
            studentUpdateIDLabel.setText("Admission Number contain only numbers.");
        }
        // if a valid entry is made
        else {
            studentUpdateIDLabel.setText("");
        }
    }

    // method to display error messages related to username
    public void displayUserNameError() {
        // if the value is empty
        if (User.userNameValidateStatus.equals("empty")) {
            studentUpdateUserNameLabel.setText("User name cannot be empty.");
        }
        // if the username is already assigned to another individual
        else if (Student.userNameValidateStatus.equals("exists")) {
            studentUpdateUserNameLabel.setText("Entered username already exists.");
        }
        // if the username contains spaces
        else if (User.userNameValidateStatus.equals("blank")) {
            studentUpdateUserNameLabel.setText("Username cannot contain spaces.");
        }
        // if the username is not between 5 and 10 characters
        else if (User.userNameValidateStatus.equals("length")) {
            studentUpdateUserNameLabel.setText("The length should be 5 to 10 characters.");
        }
        // if a valid entry is made
        else {
            studentUpdateUserNameLabel.setText("");
        }
    }

    // method to display error messages related to password
    public void displayPasswordError() {
        // if the value is empty
        if (User.passwordValidateStatus.equals("empty")) {
            studentUpdateNewPasswordLabel.setText("Password cannot be empty.");
        }
        // if the format of the password is incorrect
        else if (User.passwordValidateStatus.equals("format")) {
            studentUpdateNewPasswordLabel.setText("Password should consists of 8 characters including numbers and special characters.");
        }
        // if a valid entry is made
        else {
            studentUpdateNewPasswordLabel.setText("");
        }
    }

    @Override
    // method to set styles to the buttons on the left hand side pane
    public void makeAllStudentButtonsColoured(){
        dashboardButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ViewEventButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ProfileDirectorButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
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

        studentJoinClubDropDownList.getSelectionModel().selectFirst();
    }

    @FXML
    void OnStudentClubSelection(ActionEvent event) {
        studentJoinClubID.setText(" ");
        studentJoinClubName.setText(" ");
        studentJoinClubAdvisorName.setText(" ");

        String selectedClub = studentJoinClubDropDownList.getSelectionModel().getSelectedItem();

        if(!selectedClub.equals("None")) {
            for (Club club : Club.clubDetailsList) {
                if (club.getClubName().equals(selectedClub)) {
                    studentJoinClubID.setText(String.valueOf(club.getClubId()));
                    studentJoinClubName.setText(club.getClubName());

                    for (ClubAdvisor advisor : ClubAdvisor.clubAdvisorDetailsList) {
                        System.out.println("bn");
                        for (Club clubName : advisor.createdClubDetailsList) {
                            System.out.println("Incharge clubName ");
                            if (clubName.getClubName().equals(selectedClub)) {
                                studentJoinClubAdvisorName.setText(advisor.getFirstName() + " " + advisor.getLastName());
                                System.out.println("Incharge clubName " + "Hello");
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @FXML
    void joinClubController(){
       String clubToJoin = studentJoinClubDropDownList.getSelectionModel().getSelectedItem();

       if(clubToJoin.equals("None")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("School Club Management System");
           alert.setHeaderText("Please select a club to join with a club");
           alert.show();
       }else{
           for(Club club : Student.studentJoinedClubs){
               if(club.getClubName().equals(clubToJoin)){
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("School Club Management System");
                   alert.setHeaderText("You have already joined with this club");
                   alert.show();
                   return;
               }
           }

           for(Club club : Club.clubDetailsList){
               if(club.getClubName().equals(clubToJoin)){
                   Student student = new Student();
                   student.joinClub(club);
                   populateLeaveClubDetails();
                   populateStudentEvents();
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

    public void populateStudentEvents(){
        Student.studentEvent.clear();
        for(Club club: Student.studentJoinedClubs){
            for(Event event : Event.eventDetails){
                if(event.getClubName().equals(club.getClubName())){
                    Student.studentEvent.add(event);
                }
            }
        }
    }

    public void populateStudentJoinedClubsComboBox(){
        studentEventSelector.getItems().clear();

        if(!studentEventSelector.getItems().contains("All Clubs")){
            studentEventSelector.getItems().add("All Clubs");
        }

        for(Club club : Student.studentJoinedClubs){
            studentEventSelector.getItems().add(club.getClubName());
        }
        studentEventSelector.getSelectionModel().selectFirst();
    }

    @FXML
    void populateStudentViewEventTable(ActionEvent event) {
       populateStudentViewEventTable();
    }

    public void populateStudentViewEventTable(){
        EventViewTableStudent.getItems().clear();
        ArrayList<Event> filteredEvents = new ArrayList<>();
        String selectedClub = studentEventSelector.getSelectionModel().getSelectedItem();
        System.out.println(selectedClub + " bro");

        if(selectedClub == null){
            return;
        }

        if(selectedClub.equals("All Clubs")){
            populateAllEvents();
            return;
        }else{
            for(Event events : Event.eventDetails){
                if(events.getClubName().equals(selectedClub)){
                    filteredEvents.add(events);
                }
            }
        }



        for(Event value : filteredEvents){
            Club hostingClubDetail = value.getHostingClub();
            Event requiredEvent = new Event(value.getEventName(), value.getEventLocation(),
                    value.getEventType(),value.getEventDeliveryType(), value.getEventDate(),
                    value.getEventTime(), hostingClubDetail, value.getEventDescription(), value.getEventId());

            ObservableList<Event> viewScheduledEvents = EventViewTableStudent.getItems();
            viewScheduledEvents.add(requiredEvent);
            EventViewTableStudent.setItems(viewScheduledEvents );
        }

    }

    public void populateAllEvents(){
        for(Event value : Student.studentEvent){
            Club hostingClubDetail = value.getHostingClub();
            Event requiredEvent = new Event(value.getEventName(), value.getEventLocation(),
                    value.getEventType(),value.getEventDeliveryType(), value.getEventDate(),
                    value.getEventTime(), hostingClubDetail, value.getEventDescription(), value.getEventId());

            ObservableList<Event> viewScheduledEvents = EventViewTableStudent.getItems();
            viewScheduledEvents.add(requiredEvent);
            EventViewTableStudent.setItems(viewScheduledEvents );
        }
    }

    public void displayNumberOfEnrolledClubs(){
      EnrolledClubCountStudent.setText(String.valueOf(Student.studentJoinedClubs.size()));
    }

    public void displayNumberOfUpcomingEvents(){
        int count = 0;
        for(Event event : Student.studentEvent){
            if(event.getEventDate().isAfter(LocalDate.now())){
                count ++;
            }
        }

        UpcomingEventForStudent.setText(String.valueOf(count));
    }


    public void findNextEventDateForStudent(){
        if (Student.studentEvent.isEmpty()) {
            nextEventDateForStudent.setText("   No events");
            return;
        }

        LocalDate currentDate = LocalDate.now();

        LocalDate nextDate = null;

        for (Event event : Student.studentEvent) {
            LocalDate eventDate = event.getEventDate();
            if ((eventDate.isAfter(currentDate) || eventDate.isEqual(currentDate)) &&
                    (nextDate == null || eventDate.isBefore(nextDate))) {
                nextDate = eventDate;
            }
        }

        if (nextDate != null) {
            nextEventDateForStudent.setText("   " + nextDate);
        }

    }


    public void displayEventCountPerClub() {
        HashMap<String, Integer> clubEventCount = new HashMap<>();

        LocalDate currentDate = LocalDate.now();
        for (Event event : Student.studentEvent) {
            if(event.getEventDate().isAfter(currentDate)){
                Club club = event.getHostingClub();
                String clubName = club.getClubName();
                System.out.println(clubName);

                clubEventCount.put(clubName, clubEventCount.getOrDefault(clubName, 0) + 1);
            }
        }

        UpcomingEventRateForTable.getData().clear();

        XYChart.Series setOfData = new XYChart.Series();
        for (Map.Entry<String, Integer> entry : clubEventCount.entrySet()) {
            setOfData.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }

        UpcomingEventRateForTable.getData().addAll(setOfData);
    }
}
