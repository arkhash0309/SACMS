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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import static SystemUsers.ClubAdvisor.clubAdvisorDetailsList;
import static SystemUsers.Student.studentDetailArray;

public class StudentActivityController extends StudentDashboardController {

    public static boolean validStat = true;
    static int studentAdmissionNum;
    private String selectedGrade;
    private char studentGender;
    private String studentExistingAdmission;
    public static int studentAdmission;
    private String studentEnteredExistingPassword;
    int updatedGrade;
    String studentExistingPassword;
    static int clubIndexStudentLeave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // initialize method is used to load the details when studnetDashboard FXML is loading

        studentGender = studentDetailArray.get(0).getGender();
        studentAdmissionNum = studentDetailArray.get(0).getStudentAdmissionNum();

        setUpdateTextFields();

        studentUpdateProfileGrade.getItems().add("Select Grade"); // firstly we are selecting "Select Grade" to update grade combo box
        for (int grade = 6; grade < 14; grade++) {
            studentUpdateProfileGrade.getItems().add(String.valueOf(grade));
        }
        studentUpdateProfileGrade.getSelectionModel().selectFirst();
        selectedGrade = "Select Grade";
        updatedGrade = -1;

//        studentUpdateProfileID.setText(studentUpdateProfileID);

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
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void StudentDashboardDragDetected(MouseEvent mouseEvent) {
        Stage stage = (Stage) StudentDashboard.getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - xPosition);
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
    public void makeAllStudentDashBoardPanesInvisible() {
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
        updatedGrade = -1;
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        StudentProfilePane.setVisible(true);
        ProfileDirectorButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    public void setUpdateTextFields() { // this will set the values to student dashboard update profile

        studentExistingAdmission = String.valueOf(studentDetailArray.get(0).getStudentAdmissionNum());
        studentUpdateProfileID.setText(String.valueOf(studentDetailArray.get(0).getStudentAdmissionNum()));
        studentUpdateProfileFName.setText(studentDetailArray.get(0).getFirstName());
        studentUpdateProfileLName.setText(studentDetailArray.get(0).getLastName());
        studentUpdateProfileUserName.setText(studentDetailArray.get(0).getUserName());
        String contactNumber = makeTenDigitsForNumber(Integer.parseInt(studentDetailArray.get(0).getContactNumber()));
        studentUpdateProfileGrade.setValue(String.valueOf(studentDetailArray.get(0).getStudentGrade()));
        studentUpdateProfileContactNum.setText(contactNumber);
        studentExistingPassword = studentDetailArray.get(0).getPassword();

    }


    public void onStudentProfileUpdateButtonClick() {
        validStat = true;

        String updatedAdmissionNumber = studentUpdateProfileID.getText();
        String updatedFirstName = studentUpdateProfileFName.getText();
        String updatedLastName = studentUpdateProfileLName.getText();
        String updatedUserName = studentUpdateProfileUserName.getText();
        String updatedContactNum = studentUpdateProfileContactNum.getText();

        Student student = new Student(updatedUserName, studentEnteredExistingPassword, updatedFirstName, updatedLastName);

        Student.fNameValidateStatus = "correct";
        Student.lNameValidateStatus = "correct";
        Student.contactNumberValidateStatus = "correct";
        Student.passwordValidateStatus = "correct";
        Student.userNameValidateStatus = "correct";

        if (!student.validateFirstName()) {
            System.out.println("Incorrect First Name.");
            System.out.println(Student.fNameValidateStatus + " : First Name");
            validStat = false;
        }
        displayNameError("firstName");

        if (!student.validateLastName()) {
            System.out.println("Incorrect Last Name.");
            System.out.println(Student.lNameValidateStatus);
            validStat = false;
        }
        displayNameError("lastName");

        try {
            String tempContactNum = updatedContactNum;
            if (tempContactNum.isEmpty()) {
                User.contactNumberValidateStatus = "empty";
                throw new Exception();
            }
            Double.parseDouble(updatedContactNum.trim());
            Student std1 = new Student(tempContactNum);

            if (!std1.validateContactNumber()) {
                validStat = false;
                System.out.println("Invalid Contact Number 1");
            } else {
                User.contactNumberValidateStatus = "";
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ContactNumber 2");
            User.contactNumberValidateStatus = "format";
            validStat = false;
        } catch (Exception e) {
            validStat = false;
        }
        displayContactNumError();

        if (!student.validateUserName("updation", "student")) {
            System.out.println("Wrong user name.");
            validStat = false;
        } else {
            User.userNameValidateStatus = "";
        }
        displayUserNameError();

        // if student did not select grade in student dashboard, will set validateStat to false, in order to set the respective error label
        if(validateGradeSelection() == -1){
            validStat = false;
        }

        System.out.println(validStat + " : Valid Stat");
        if (validStat) {
            for (Student foundStudent : studentDetailArray) { // here we are updating the studentDetailArray list , adding updated personal details of the student
                if (foundStudent.getStudentAdmissionNum() == Integer.parseInt(updatedAdmissionNumber)) {
                    foundStudent.setFirstName(updatedFirstName);
                    foundStudent.setLastName(updatedLastName);
                    foundStudent.setUserName(updatedUserName);
//                    foundStudent.setGender(studentGender);
                    foundStudent.setContactNumber(updatedContactNum);
                }
            }

            // inserting updated details to database
            String updatedPersonalDetailsQuery = "UPDATE Student set studentFName = ?, studentLName = ?, studentGrade = ?,Gender = ?, studentContactNum = ? where studentAdmissionNum = ?";
            try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(updatedPersonalDetailsQuery)) {

                preparedStatement.setString(1, updatedFirstName);
                preparedStatement.setString(2, updatedLastName);
                preparedStatement.setInt(3, updatedGrade);
                preparedStatement.setString(4, String.valueOf(studentGender));
                preparedStatement.setInt(5, Integer.parseInt(updatedContactNum));
                preparedStatement.setString(6, String.valueOf(studentAdmissionNum));
                preparedStatement.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("School Club Management System");
                alert.setHeaderText("You have successfully update your personal details.");
                alert.showAndWait();

            } catch (Exception e) {
                System.out.println(e);
            }


        }
        System.out.println("\n\n\n");

    }

    public void onStudentProfilePasswordChangeButtonClick() throws SQLException {
        validStat = true;

        int admissionNumber = Integer.parseInt(studentUpdateProfileID.getText()); // getting student admission number from studentUpdateProfileID text field
        String updatedUserName = studentUpdateProfileUserName.getText(); // getting student updated username  from studentUpdateProfileUserName text field
        String updatedPassword = studentUpdateProfileNewPassword.getText();  // getting student updated username  from studentUpdateProfileNewPassword text field
        String updateConfirmPassword = studentUpdateProfileConfirmPassword.getText(); // getting student updateConfirmPassword from studentUpdateProfileConfirmPassword text field
        studentEnteredExistingPassword = studentUpdateProfileExistingPassword.getText(); // getting student studentEnteredExistingPassword from studentUpdateProfileExistingPassword text field

        existingPasswordChecker(studentExistingPassword, studentEnteredExistingPassword);
        ConfirmPasswordChecker(updatedPassword, updateConfirmPassword);
        PasswordChecker(updatedPassword);



        // inserting updated credentials to database
        if(validStat) {
            String updatedStudentCredentialsQueryt = "update studentCredentials set studentUserName = ?, studentPassword = ?  where studentAdmissionNum = ?";
            try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(updatedStudentCredentialsQueryt)) {
                preparedStatement.setString(1, updatedUserName);
                preparedStatement.setString(2, updateConfirmPassword);
                preparedStatement.setString(3, String.valueOf(admissionNumber));
                preparedStatement.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("School Club Management System");
                alert.setHeaderText("You have successfully update your credentials.");
                alert.showAndWait();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        //Update database
    }

    // this method is used to validate student new password
    void PasswordChecker(String studentUpdatedPassword){

        validStat = true;
        String specialCharacters = "!@#$%^&*()_+-=[]{};':\",./<>?"; // this variable is used to check whether new password containing special characters

        if(studentUpdatedPassword.isEmpty()){ // if new password is empty
            studentUpdateNewPasswordLabel.setText("Password cannot be empty");
            validStat = false;
        }else {
            studentUpdateNewPasswordLabel.setText("");
        }

        for (char specialChar : specialCharacters.toCharArray()) { // here it is iterating character by character of the new password to check whether it is containing special characters.
            studentUpdatedPassword.contains(String.valueOf(specialChar));
            validStat = true;
            studentUpdateNewPasswordLabel.setText("");
        }
        if(studentUpdatedPassword.length() < 8 || studentUpdatedPassword.length() > 20){ // here we are checking whether new password consist of more than 8 characters
            studentUpdateNewPasswordLabel.setText("");
            validStat = false;
            if(!validStat){
                studentUpdateNewPasswordLabel.setText("""
                    Password should consist of 8 characters
                    including numbers and special characters.""");

            }else {
                studentUpdateNewPasswordLabel.setText("");
            }
        }else{
            validStat = false;
            studentUpdateNewPasswordLabel.setText("""
                    Password should consist of 8 characters
                    including numbers and special characters.""");
        }
    }
    // this method is to check whether confirm password is entered correctly
    void ConfirmPasswordChecker(String studentUpdatedPassword, String studentConfirmPassword){
        validStat = true;

        if(studentConfirmPassword.isEmpty()){ // if confirm password field is empty
            studentUpdateConfirmPasswordLabel.setText("Password cannot be empty");
            validStat = false;
        }else {
            studentUpdateConfirmPasswordLabel.setText("");

        }if(!studentConfirmPassword.equals(studentUpdatedPassword)){ // if both studentConfirmPassword and studentUpdatedPassword are not matching
            studentUpdateConfirmPasswordLabel.setText("Passwords are not matching");
            validStat = false;
        } else{
            studentUpdateConfirmPasswordLabel.setText("");
        }
    }
    // this method will check whether existing password
    void existingPasswordChecker(String realExistingPassword, String enteredExistingPassword){
        if (!realExistingPassword.equals(enteredExistingPassword)){ // if student did not enter the existing password correctly, this if condition will perform
            studentUpdateExistingPasswordLabel.setText("Please enter your current password correctly");
            validStat = false;
        }else{ // if password is correct
            studentUpdateExistingPasswordLabel.setText("");

        }

        if(realExistingPassword.isEmpty()){ // here it is checking whether existing password is empty or not.
            studentUpdateExistingPasswordLabel.setText("Password cannot be empty");
            validStat = false;
        }else{
            studentUpdateExistingPasswordLabel.setText("");
        }
    }

    // this method will ensure that student chose his or her grade in student profile update section
    private int validateGradeSelection(){
        selectedGrade = studentUpdateProfileGrade.getValue();

        if(selectedGrade == "Select Grade"){
            updateGradeLabel.setText("Please select your grade");
            return -1;
        } else {
            updateGradeLabel.setText("");
            updatedGrade = Integer.parseInt(this.studentUpdateProfileGrade.getValue());// if student chose his or her grade correctly, updatedGrade variable will be assigned new grade
            return updatedGrade;
        }
    }
    // this method is to set zero to contact number, because when we retrieve the contact number from database
    public static String makeTenDigitsForNumber(int number) {
        String strNumber = Integer.toString(number);

        if (strNumber.length() < 10) { // when strNumber is less than 10 digits
            StringBuilder zeros = new StringBuilder();
            for (int i = 0; i < 10 - strNumber.length(); i++) {
                zeros.append('0');
            }
            return zeros.toString() + strNumber;
        } else {
            return strNumber.substring(0, 10);
        }
    }

    public void displayNameError(String nameType) {
        if (nameType.equals("firstName")) {
            if (Student.fNameValidateStatus.equals("empty")) {
                studentUpdateFNameLabel.setText("First Name cannot be empty.");
            } else if (Student.fNameValidateStatus.equals("format")) {
                studentUpdateFNameLabel.setText("First Name can contain only letters.");
            } else {
                studentUpdateFNameLabel.setText("");
            }
        } else if (nameType.equals("lastName")) {
            if (Student.lNameValidateStatus.equals("empty")) {
                studentUpdateLNameLabel.setText("Last Name cannot be empty.");
            } else if (Student.lNameValidateStatus.equals("format")) {
                studentUpdateLNameLabel.setText("Last name can contain only letters.");
            } else {
                studentUpdateLNameLabel.setText("");
            }
        }
    }



    public void displayContactNumError() {
        if (User.contactNumberValidateStatus.equals("empty")) {
            studentUpdateContactNumLabel.setText("Contact number cannot be empty.");
        } else if (User.contactNumberValidateStatus.equals("length")){
            studentUpdateContactNumLabel.setText("Contact number should be 10 digits.");
        } else if (User.contactNumberValidateStatus.equals("format")) {
            studentUpdateContactNumLabel.setText("It should contain only numbers.");
        } else {
            studentUpdateContactNumLabel.setText("");
        }
    }

    public void displayUserNameError() {
        if (User.userNameValidateStatus.equals("empty")) {
            studentUpdateUserNameLabel.setText("User name cannot be empty.");
        } else if (Student.userNameValidateStatus.equals("exists")) {
            studentUpdateUserNameLabel.setText("Entered username already exists.");
        } else if (User.userNameValidateStatus.equals("blank")) {
            studentUpdateUserNameLabel.setText("Username cannot contain spaces.");
        } else if (User.userNameValidateStatus.equals("length")) {
            studentUpdateUserNameLabel.setText("The length should be 5 to 10 characters.");
        } else {
            studentUpdateUserNameLabel.setText("");
        }
    }

    @Override
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
    void leaveClubController(ActionEvent event){ // leave club method
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

        }catch(NullPointerException error){ // here we are setting alert message to student in order to confirm that he or she successfully left the club
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