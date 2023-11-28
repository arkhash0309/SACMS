package StudentDashboardManager;

import ClubManager.Club;
import ClubManager.Event;
import DataBaseManager.StudentDataBaseManager;
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

    public static boolean validStat = true; // uses for validation purposes
    static int studentAdmissionNum; // hold student admission number for StudentActivityController class purposes
    private String selectedGrade; // hold selected grade from combo box
    private char studentGender; // hold student gender
    private String studentExistingAdmission; // hold student existing admission number
    public static int studentAdmission; // hold student admission number to show username in dashboard
    private String studentEnteredExistingPassword; // hold student entered existing password
    int updatedGrade; // hold student grade
    String studentExistingPassword; // hold student real existing password
    static int clubIndexStudentLeave;

    // work done by- Arkhash, Lakshan and Deelaka
    // This method initializes the student dashboard
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // initialize method is used to load the details when studentDashboard FXML is loading
        // the columns are initialized for the event view table
        studentGender = studentDetailArray.get(0).getGender();
        studentAdmissionNum = studentDetailArray.get(0).getStudentAdmissionNum();

        // the columns are initialized for the event view table
        studentUpdateProfileGrade.getItems().add("Select Grade"); // firstly we are selecting "Select Grade" to update grade combo box
        for (int grade = 6; grade < 14; grade++) {
            studentUpdateProfileGrade.getItems().add(String.valueOf(grade));
        }
        studentUpdateProfileGrade.getSelectionModel().selectFirst();
        selectedGrade = "Select Grade";
        updatedGrade = -1;
//        studentUpdateProfileID.setText(studentUpdateProfileID);
        displayNumberOfEnrolledClubs(); // this method is used to display number of enrolled clubs
        displayNumberOfUpcomingEvents(); // this method is used to display number of upcoming events
        findNextEventDateForStudent(); // this method is used to find next event date for student
        studentEventSelector.getItems().add("All Clubs");
        studentEventSelector.getSelectionModel().selectFirst(); // this method is used to select "All Clubs" in studentEventSelector combo box

        // the columns are initialized for the leave club table
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

        setUpdateTextFields(); // this method is used to set update text fields
    }

    // work done by- Lakshan
    // This method is used to display number of enrolled clubs
    @Override
    void StudentLogout(MouseEvent event) throws IOException {
        // Loading the student login page
        Parent root = FXMLLoader.load(getClass().getResource("/LoginDashboardManager/StudentLogin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        // making centered the login window
        stage.centerOnScreen();
        stage.show();
    }

    // work done by- Lakshan
    // This method detected student dashboard drag
    @Override
    public void StudentDashboardDragDetected(MouseEvent mouseEvent) {
        // Getting the current stage associated with the provided stackPane
        Stage stage = (Stage) StudentDashboard.getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - xPosition);
        stage.setY(mouseEvent.getScreenY() - yPosition);
    }

    // work done by- Lakshan
    // This student pane pressed method is used to detect student dashboard drag
    @Override
    public void StudentPanePressed(MouseEvent mouseEvent) {
        // Getting the current stage associated with the provided stackPane
        xPosition = mouseEvent.getSceneX();
        yPosition = mouseEvent.getSceneY();
    }

    // work done by- Lakshan
    // This method minimize student dashboard
    @Override
    void MinimizePane(ActionEvent event) {
        // Getting the current stage associated with the provided stackPane
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(StudentDashboard);
    }


    // work done by- Lakshan
    // This method close the student dashboard
    @Override
    void ClosePane(ActionEvent event) {
        // Getting the current stage associated with the provided stackPane
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    // work done by- Lakshan
    // This method make invisible student dashboard panes
    @Override
    public void makeAllStudentDashBoardPanesInvisible() {
        EventStudentPane.setVisible(false); // this method is used to make event student pane invisible
        JoinLeaveClubPane.setVisible(false); // this method is used to make join leave club pane invisible
        StudentDashBoardPane.setVisible(false); // this method is used to make student dashboard pane invisible
        StudentProfilePane.setVisible(false); // this method is used to make student profile pane invisible
    }

    // work done by- Lakshan
    // This method direct student to student dashboard
    @Override
    void GoToDashBoard(ActionEvent event) {
        makeAllStudentDashBoardPanesInvisible(); // this method is used to make all student dashboard panes invisible
        makeAllStudentButtonsColoured(); // this method is used to make all student buttons coloured
        StudentDashBoardPane.setVisible(true); //
        dashboardButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        displayNumberOfEnrolledClubs(); // this method is used to display number of enrolled clubs
        displayNumberOfUpcomingEvents(); // this method is used to display number of upcoming events
        findNextEventDateForStudent(); // this method is used to find next event date for student
        displayEventCountPerClub(); // this method is used to display event count per club
    }

    // work done by- Lakshan
    // This method direct student to join leave club pane
    @Override
    public void GoToJoinLeaveClub(ActionEvent actionEvent) {
        makeAllStudentDashBoardPanesInvisible(); // this method is used to make all student dashboard panes invisible
        makeAllStudentButtonsColoured(); // this method is used to make all student buttons coloured
        JoinLeaveClubPane.setVisible(true); // this method is used to make join leave club pane visible
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        getCreatedClubs(); // get created clubs
        populateLeaveClubDetails(); //
    }


    // work done by- Lakshan
    // This method direct student to events pane
    @Override
    public void GoToEvents(ActionEvent actionEvent) {
        makeAllStudentDashBoardPanesInvisible(); // this method is used to make all student dashboard panes invisible
        makeAllStudentButtonsColoured(); // this method is used to make all student buttons coloured
        EventStudentPane.setVisible(true);
        ViewEventButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        populateAllEvents();
        populateStudentJoinedClubsComboBox();
    }

    // work done by- Lakshan
    @FXML
    void studentProfileDirector(ActionEvent event) {
        updatedGrade = -1;
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        StudentProfilePane.setVisible(true);
        ProfileDirectorButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    // work done by- Deelaka
    public void setUpdateTextFields() {
        // this will set the values to student dashboard update profile
        studentExistingAdmission = String.valueOf(studentDetailArray.get(0).getStudentAdmissionNum());
        studentUpdateProfileID.setText(String.valueOf(studentDetailArray.get(0).getStudentAdmissionNum()));
        studentUpdateProfileFName.setText(studentDetailArray.get(0).getFirstName());
        studentUpdateProfileLName.setText(studentDetailArray.get(0).getLastName());
        studentUpdateProfileUserName.setText(studentDetailArray.get(0).getUserName());
        String contactNumber = makeTenDigitsForNumber(Integer.parseInt(studentDetailArray.get(0).getContactNumber()));
        studentUpdateProfileGrade.setValue(String.valueOf(studentDetailArray.get(0).getStudentGrade()));
        studentUpdateProfileContactNum.setText(contactNumber);
        studentExistingPassword = studentDetailArray.get(0).getPassword();
        studentUpdateProfileGrade.getSelectionModel().select(String.valueOf(studentDetailArray.get(0).getStudentGrade()));
    }


    // work done by- Arkhash, Deelaka and Lakshan
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
        // the  first name is validated using the validator interface
        if (!student.validateFirstName()) {
            System.out.println("Incorrect First Name.");
            System.out.println(Student.fNameValidateStatus + " : First Name");
            validStat = false; // the boolean value is set to false as there is an error
        }
        //the error field is specified as the first and last names follow the same validation
        displayNameError("firstName");
        // the last name is validated using the validator interface
        if (!student.validateLastName()) {
            System.out.println("Incorrect Last Name.");
            System.out.println(Student.lNameValidateStatus);
            validStat = false; // the boolean value is set to false as there is an error
        }
        displayNameError("lastName");
        // validating contact number
        try {
            String tempContactNum = updatedContactNum; // the contact number is stored in a temporary variable
            // check if the value is empty
            if (tempContactNum.isEmpty()) {
                User.contactNumberValidateStatus = "empty";
                throw new Exception(); // general exception is thrown
            }
            // the string is converted to a double and it is trimmed
            Double.parseDouble(updatedContactNum.trim());
            Student std1 = new Student(tempContactNum);  /* a new object is created of
                                                        data type Student with only the temporary holder as the */

            if (!std1.validateContactNumber()) {
                validStat = false;  // the boolean value is set to false as there is an error
                System.out.println("Invalid Contact Number 1");
            } else {
                // the contact number is validated
                User.contactNumberValidateStatus = "";
            }
        } catch (NumberFormatException e) {
            // catching number format exceptions
            System.out.println("Invalid ContactNumber 2");
            User.contactNumberValidateStatus = "format";
            validStat = false;  // the boolean value is set to false as there is an error
        } catch (Exception e) {
            validStat = false; // the boolean value is set to false as there is an error
        }
        displayContactNumError(); // the error method is called to specify what type of error is produced

        if (!student.validateUserName("updation", "student")) { /* passing parameters to
                                            validateUserName method, and if username did not
                                             meet system standards */
            System.out.println("Wrong user name.");
            validStat = false;
        } else {
            User.userNameValidateStatus = ""; // when entered username is valid
        }
        displayUserNameError(); // the error method is called to specify what type of error is produced

        // if student did not select grade in student dashboard, will set validateStat to false, in order to set the respective error label
        if(validateGradeSelection() == -1){
            validStat = false;
        }

        System.out.println(validStat + " : Valid Stat");
        if (validStat) {
            for (Student foundStudent : studentDetailArray) { /* here we are updating the studentDetailArray list,
                                                adding updated personal details of the student */
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

                preparedStatement.setString(1, updatedFirstName); // setting updatedFirstName
                preparedStatement.setString(2, updatedLastName); // setting updatedLastName
                preparedStatement.setInt(3, updatedGrade); // setting updatedGrade
                preparedStatement.setString(4, String.valueOf(studentGender)); // setting studentGender
                preparedStatement.setInt(5, Integer.parseInt(updatedContactNum)); // setting updatedContactNum
                preparedStatement.setString(6, String.valueOf(studentAdmissionNum)); // setting studentAdmissionNum
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
            }
            String updateStudentUserNameQuery = "update studentCredentials set studentUserName = ? " + "where studentAdmissionNum = ?"; // student username update query
            try(PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(updateStudentUserNameQuery)){
                preparedStatement.setString(1, updatedUserName); // setting updatedUserName
                preparedStatement.setString(2, String.valueOf(updatedAdmissionNumber)); // setting updatedAdmissionNumber to map tables
                preparedStatement.executeUpdate();
                studentUpdateProfileUserName.setText(updatedUserName);
                showUserName.setText(updatedUserName); // setting newly updated username to dashboard
                showUserName.setStyle("-fx-text-alignment: center");
                System.out.println("Username, Working as desired");
            }catch (Exception e){
                System.out.println(e);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("School Club Management System");
            alert.setHeaderText("You have successfully update your personal details.");
            alert.showAndWait();


        }
        System.out.println("\n\n\n");

    }

    // work done by- Deelaka, Arkhash and Lakshan
    public void onStudentProfilePasswordChangeButtonClick() throws SQLException {
        validStat = true;

        int admissionNumber = Integer.parseInt(studentUpdateProfileID.getText()); // getting student admission number from studentUpdateProfileID text field
        String updatedUserName = studentUpdateProfileUserName.getText(); // getting student updated username  from studentUpdateProfileUserName text field
        String updatedPassword = studentUpdateProfileNewPassword.getText();  // getting student updated username  from studentUpdateProfileNewPassword text field
        String updateConfirmPassword = studentUpdateProfileConfirmPassword.getText(); // getting student updateConfirmPassword from studentUpdateProfileConfirmPassword text field
        // getting student studentEnteredExistingPassword from studentUpdateProfileExistingPassword text field
        studentEnteredExistingPassword = studentUpdateProfileExistingPassword.getText();

        // passwords validation process
        existingPasswordChecker(studentExistingPassword, studentEnteredExistingPassword);
        if(validStat){
            PasswordChecker(updatedPassword);
        }
        if(validStat){
            ConfirmPasswordChecker(updatedPassword, updateConfirmPassword);
        }

        // inserting updated credentials to database
        if(validStat) {
            String updatedStudentCredentialsQuery = "update studentCredentials set studentUserName = ?, studentPassword = ?  where studentAdmissionNum = ?";

            try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(updatedStudentCredentialsQuery)) {
                preparedStatement.setString(1, updatedUserName); // setting updatedUserName
                preparedStatement.setString(2, updateConfirmPassword); // setting updateConfirmPassword
                preparedStatement.setString(3, String.valueOf(admissionNumber)); // setting admissionNumber to map table
                preparedStatement.executeUpdate();

            } catch (Exception e) {
                System.out.println(e);
            }

            StudentDataBaseManager.setStudentUserName(updatedUserName);

            System.out.println("Updation done");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("School Club Management System");
            alert.setHeaderText("You have successfully update your credentials.");
            alert.showAndWait();
        }

        //Update database
    }

    // work done by- Deelaka
//     this method is used to validate student new password
    void PasswordChecker(String studentUpdatedPassword){
        String specialCharacters = "!@#$%^&*()_+-=[]{};':\",./<>?"; /* this variable is used to check
                                                    whether new password containing special characters */
        if(studentUpdatedPassword.equals("")){ // if new password is empty
            studentUpdateNewPasswordLabel.setText("Password cannot be empty");
            validStat = false;
            return;
        }else {
            validStat = true;
            studentUpdateNewPasswordLabel.setText("");
            studentUpdateProfileNewPassword.setText("");// clearing password text field, to sure privacy measurements
        }

        validStat = false;
        for (char specialChar : specialCharacters.toCharArray()) { /* here it is iterating character by character
                                                            of the new password to check whether it is containing special characters */
            if(studentUpdatedPassword.contains(String.valueOf(specialChar))){
                validStat = true;
            }
        }
        if(studentUpdatedPassword.length() >= 8 && studentUpdatedPassword.length() <= 20){ /* here we are checking whether
                                                                        new password consist of more than 8 characters */
            if(!validStat){
                studentUpdateNewPasswordLabel.setText("""
                                    Password should consist of special 
                                    characters""");
            }else {
                validStat = true;
                studentUpdateNewPasswordLabel.setText("");
                studentUpdateProfileNewPassword.setText("");// clearing password text field, to sure privacy measurements
            }
        }else{
            validStat = false;
            studentUpdateNewPasswordLabel.setText("Password should consist of 8 characters");
        }
    }

    // work done by- Deelaka
    // this method is to check confirm password is entered correctly
    void ConfirmPasswordChecker(String studentUpdatedPassword, String studentConfirmPassword){


        if(studentConfirmPassword.isEmpty()){ // if confirm password field is empty
            studentUpdateConfirmPasswordLabel.setText("Password cannot be empty");
            validStat = false;
        }else {
            validStat = true;
            studentUpdateConfirmPasswordLabel.setText("");
            studentUpdateProfileConfirmPassword.setText("");// clearing password text field, to sure privacy measurements

        }if(!studentConfirmPassword.equals(studentUpdatedPassword)){ // if both studentConfirmPassword and studentUpdatedPassword are not matching
            studentUpdateConfirmPasswordLabel.setText("Passwords are not matching");
            validStat = false;
        } else{
            validStat = true; // if confirm password is correct
            studentUpdateConfirmPasswordLabel.setText("");
            studentUpdateProfileConfirmPassword.setText("");// clearing password text field, to sure privacy measurements
        }
    }

    // work done by- Deelaka
    // this method will check whether existing password
    void existingPasswordChecker(String realExistingPassword, String enteredExistingPassword){

        if(enteredExistingPassword.equals("")){ // here it is checking whether existing password is empty or not.
            studentUpdateExistingPasswordLabel.setText("Password cannot be empty");
            validStat = false;
            return;
        }else{
            validStat = true;
            studentUpdateExistingPasswordLabel.setText(""); // clearing studentUpdateExistingPasswordLabel
            studentUpdateProfileExistingPassword.setText(""); // clearing password text field, to sure privacy measurements
        }

        if (!realExistingPassword.equals(enteredExistingPassword)){ // if student did not enter the existing password correctly, this if condition will perform
            studentUpdateExistingPasswordLabel.setText("""
                                    Please enter your current password 
                                    correctly""");
            validStat = false;
        }else{ // if password is correct
            validStat = true;
            studentUpdateExistingPasswordLabel.setText("");
            studentUpdateProfileExistingPassword.setText("");

        }
    }

    // work done by- Deelaka
    // this method will ensure that student chose his or her grade in student profile update section
    private int validateGradeSelection(){
        selectedGrade = studentUpdateProfileGrade.getValue();

        if(selectedGrade == "Select Grade"){
            updateGradeLabel.setText("Please select your grade");
            return -1;
        } else {
            updateGradeLabel.setText("");
            updatedGrade = Integer.parseInt(this.studentUpdateProfileGrade.getValue());/* if student chose his or her grade correctly,
                                                                                            updatedGrade variable will be assigned new grade */
            return updatedGrade;
        }
    }

    // work done by- Deelaka
    /* this method is to set zero to contact number, because when we retrieve the contact number
     from database, we will receive a 9 digit number, since the format is int in database */
    public void displayUserNameError() { // username checking
        if (User.userNameValidateStatus.equals("empty")) { // when username field is empty
            studentUpdateUserNameLabel.setText("User name cannot be empty.");
        } else if (Student.userNameValidateStatus.equals("exist")) { // when user enter an existed admission number
            studentUpdateUserNameLabel.setText("Entered username already exists.");
        } else if (User.userNameValidateStatus.equals("blank")) { // when username contain spaces
            studentUpdateUserNameLabel.setText("Username cannot contain spaces.");
        } else if (User.userNameValidateStatus.equals("length")) { // when username is not lengthier enough
            studentUpdateUserNameLabel.setText("The length should be 5 to 10 characters.");
        } else {
            studentUpdateUserNameLabel.setText(""); // when entered username is correct
        }
    }

    // work done by- Arkhash
    public void displayNameError(String nameType) { // entered name checking
        if (nameType.equals("firstName")) { // checking first name
            if (Student.fNameValidateStatus.equals("empty")) {  // if first name field is empty
                studentUpdateFNameLabel.setText("First Name cannot be empty.");
            } else if (Student.fNameValidateStatus.equals("format")) {  // if first name field contain invalid character
                studentUpdateFNameLabel.setText("First Name can contain only letters.");
            } else {
                studentUpdateFNameLabel.setText(""); // when user correctly enter first name
            }
        } else if (nameType.equals("lastName")) { // checking last name
            if (Student.lNameValidateStatus.equals("empty")) {  //if last Name field is empty
                studentUpdateLNameLabel.setText("Last Name cannot be empty.");
            } else if (Student.lNameValidateStatus.equals("format")) {  // if last name field contain invalid characters
                studentUpdateLNameLabel.setText("Last name can contain only letters.");
            } else {
                studentUpdateLNameLabel.setText(""); // when user correctly enter last name
            }
        }
    }

    // work done by- Arkhash
    public void displayContactNumError() { // contact number checking
        if (User.contactNumberValidateStatus.equals("empty")) {  // when contact number field is empty
            studentUpdateContactNumLabel.setText("Contact number cannot be empty.");
        } else if (User.contactNumberValidateStatus.equals("length")){ // when entered contact number is not a valid number
            studentUpdateContactNumLabel.setText("Contact number should be 10 digits.");
        } else if (User.contactNumberValidateStatus.equals("format")) { // when entered contact number has string values
            studentUpdateContactNumLabel.setText("It should contain only numbers.");
        } else {
            studentUpdateContactNumLabel.setText(""); // when entered contact is correct
        }
    }

    // work done by- Arkhash and Deelaka
    public static String makeTenDigitsForNumber(int number) {
        // Convert the number to string
        String strNumber = Integer.toString(number);
        // If the number has less than 10 digits, add leading zeros
        if (strNumber.length() < 10) { // when strNumber is less than 10 digits
            StringBuilder zeros = new StringBuilder();
            for (int i = 0; i < 10 - strNumber.length(); i++) {
                zeros.append('0');
            }
            // combine leading zeros and the original number
            return zeros.toString() + strNumber;
        } else {
            // If the number has more than 10 digits, truncate it to the first ten digits
            return strNumber.substring(0, 10);
        }
    }

    // work done by- Lakshan
    @Override
    public void makeAllStudentButtonsColoured(){
        dashboardButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ViewEventButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ProfileDirectorButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
    }

    // work done by- Lakshan
    // This method get created club names
    public void getCreatedClubs(){

        if(!studentJoinClubDropDownList.getItems().contains("None")){
            // adding "None" to studentJoinClubDropDownList combo box
            studentJoinClubDropDownList.getItems().add("None");
        }

        for(Club club: Club.clubDetailsList){
            String clubName; // hold club name
            clubName = club.getClubName(); // getting club name

            // checking whether club name is already in the combo box
            boolean viewContainsStatus = studentJoinClubDropDownList.getItems().contains(clubName);


            if(!viewContainsStatus){
                // adding club name to studentJoinClubDropDownList combo box
                studentJoinClubDropDownList.getItems().add(clubName);
            }
        }

        // selecting "None" in studentJoinClubDropDownList combo box
        studentJoinClubDropDownList.getSelectionModel().selectFirst();
    }

    // work done by- Deelaka and Lakshan
    // this method is used to populate all events in student dashboard
    @FXML
    void OnStudentClubSelection(ActionEvent event) {
        studentJoinClubID.setText(" "); // setting the club ID of the student selected club
        studentJoinClubName.setText(" "); // setting the club name of the student selected club
        studentJoinClubAdvisorName.setText(" "); // setting club advisor name of the respective club

        // getting selected club name from studentJoinClubDropDownList combo box
        String selectedClub = studentJoinClubDropDownList.getSelectionModel().getSelectedItem();

        if(!selectedClub.equals("None")) {
            // iterating through club details list
            for (Club club : Club.clubDetailsList) {
                // checking whether selected club name is equal to club name in club details list
                if (club.getClubName().equals(selectedClub)) {
                    studentJoinClubID.setText(String.valueOf(club.getClubId())); // setting the club ID of the student selected club
                    studentJoinClubName.setText(club.getClubName()); // setting the club name of the student selected club

                    // iterating through club advisor details list
                    for (ClubAdvisor advisor : ClubAdvisor.clubAdvisorDetailsList) {
                        // checking whether selected club name is equal to club name in club advisor details list
                        for (Club clubName : advisor.createdClubDetailsList) {
                            System.out.println("Incharge clubName ");
                            if (clubName.getClubName().equals(selectedClub)) {
                                studentJoinClubAdvisorName.setText(advisor.getFirstName() + " " + advisor.getLastName()); // setting club advisor name of the respective club
                                System.out.println("Incharge clubName " + "Hello");
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    // work done by- Deelaka and Lakshan
    // This method handles joining to the club
    @FXML
    void joinClubController(){
        // getting selected club name from studentJoinClubDropDownList combo box
       String clubToJoin = studentJoinClubDropDownList.getSelectionModel().getSelectedItem();

       // checking whether student selected "None" in studentJoinClubDropDownList combo box
       if(clubToJoin.equals("None")){
           // setting alert message to student in order to confirm that he or she successfully joined to the club
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("School Club Management System");
           alert.setHeaderText("Please select a club to join with a club");
           alert.show();
       }else{
           // checking whether student already joined to the club
           for(Club club : Student.studentJoinedClubs){
               if(club.getClubName().equals(clubToJoin)){
                   // setting alert message to student in order to confirm that he or she successfully joined to the club
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("School Club Management System");
                   alert.setHeaderText("You have already joined with this club");
                   alert.show();
                   return;
               }
           }

           // iterating through club details list
           for(Club club : Club.clubDetailsList){
               if(club.getClubName().equals(clubToJoin)){
                   Student student = new Student(); // creating new student object
                   student.joinClub(club); // calling joinClub method
                   populateLeaveClubDetails(); // populating leave club details
                   populateStudentEvents(); // populating student events
                   return;
               }
           }
       }

    }

    // work done by- Deelaka and Lakshan
    // This method is used to display number of enrolled clubs
    public void populateLeaveClubDetails(){
        // clearing leaveClubTable table view
        leaveClubTable.getItems().clear();
        // iterating through student joined clubs list
        for(Club club : Student.studentJoinedClubs){
            // creating new club object
            Club clubs = new Club(club.getClubId(), club.getClubName(), club.getClubDescription(), club.getClubLogo());
            ObservableList<Club> viewJoinedClubs = leaveClubTable.getItems(); // creating observable list
            viewJoinedClubs.add(clubs); // adding clubs to observable list
            leaveClubTable.setItems(viewJoinedClubs); //
        }
    }

    // work done by- Deelaka
    // controlls leaving
    @FXML
    void leaveClubController(ActionEvent event){ // leave club method
        leaveClubController();
    }


    // work done by- Lakshan and Deelaka
    public void leaveClubController(){ // student leave club method
        try{
            // getting selected club from leaveClubTable table view
            Club selectedClub = leaveClubTable.getSelectionModel().getSelectedItem();
            // getting selected club index from leaveClubTable table view
            clubIndexStudentLeave = leaveClubTable.getSelectionModel().getSelectedIndex();
            System.out.println(selectedClub.getClubName());

            // setting alert message to student in order to confirm that he or she successfully left the club
            Alert cancelEvent = new Alert(Alert.AlertType.CONFIRMATION);
            cancelEvent.initModality(Modality.APPLICATION_MODAL);
            cancelEvent.setTitle("School Activity Club Management System");
            cancelEvent.setHeaderText("Do you really want to leave the club ?");

            // setting alert message to student in order to confirm that he or she successfully left the club
            Optional<ButtonType> result = cancelEvent.showAndWait();
            if(result.get() != ButtonType.OK){
                return;
            }

            // creating new student object
            Student student = new Student();
            // calling leaveClub method
            student.leaveClub(selectedClub, clubIndexStudentLeave);
            // populating leave club details
            populateLeaveClubDetails();

            // creating new iterator object
            Iterator<Event> iterator = Student.studentEvent.iterator();
            while (iterator.hasNext()) {
                // getting event from student event list
                Event event = iterator.next();
                // checking whether event club name is equal to selected club name
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


    // work done by- Lakshan
    // This method search joined clubs
    @FXML
    void searchJoinedClubs(ActionEvent event) {
        searchJoinedClubs(leaveClubTable, studentLeaveClubSearch);
    }

    // work done by- Lakshan
    // This method search joined clubs
    public void searchJoinedClubs(TableView<Club> tableView, TextField searchBar){

        String clubName = searchBar.getText();
        System.out.println(clubName);

        Club foundClub = null;
        boolean foundStat = false;
        int count = 0;
        // iterating through student joined clubs list
        for(Club clubVal : Student.studentJoinedClubs){
            // checking whether club name is equal to entered club name
            if(clubVal.getClubName().equals(clubName)){
                foundClub = clubVal; // setting found club
                System.out.println(foundClub.getClubName());
                foundStat = true;
                break;
            }
            count++; // incrementing count
        }
        // checking whether club is found
        if(foundStat){
                tableView.getSelectionModel().select(count); // selecting found club
                // getting selected club index from leaveClubTable table view
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

    // work done by- Lakshan
    // This method populate student events
    public void populateStudentEvents(){
        Student.studentEvent.clear();
        // iterating through student joined clubs list
        for(Club club: Student.studentJoinedClubs){
            // iterating through event details list
            for(Event event : Event.eventDetails){
                //
                if(event.getClubName().equals(club.getClubName())){
                    Student.studentEvent.add(event);
                }
            }
        }
    }

    // work done by- Lakshan
    // This method populate student joined clubs combo box
    public void populateStudentJoinedClubsComboBox(){
        // clearing studentEventSelector combo box
        studentEventSelector.getItems().clear();

        // checking whether studentEventSelector combo box contains "All Clubs"
        if(!studentEventSelector.getItems().contains("All Clubs")){
            studentEventSelector.getItems().add("All Clubs");
        }

        // iterating through student joined clubs list
        for(Club club : Student.studentJoinedClubs){
            studentEventSelector.getItems().add(club.getClubName());
        }
        // selecting "All Clubs" in studentEventSelector combo box
        studentEventSelector.getSelectionModel().selectFirst();
    }

    // work done by- Lakshan
    // This method populate student view event table fx id
    @FXML
    void populateStudentViewEventTable(ActionEvent event) {
       populateStudentViewEventTable();
    }

    // This method populate student view event table
    public void populateStudentViewEventTable(){
        // clearing EventViewTableStudent table view
        EventViewTableStudent.getItems().clear();
        ArrayList<Event> filteredEvents = new ArrayList<>(); //
        String selectedClub = studentEventSelector.getSelectionModel().getSelectedItem();
        System.out.println(selectedClub + " bro");

        // checking whether selected club is null
        if(selectedClub == null){
            return;
        }

        // checking whether selected club is equal to "All Clubs"
        if(selectedClub.equals("All Clubs")){
            populateAllEvents(); // populating all events
            return;
        }else{
            // iterating through event details list
            for(Event events : Event.eventDetails){
                // checking whether club name is equal to selected club name
                if(events.getClubName().equals(selectedClub)){
                    filteredEvents.add(events);
                }
            }
        }

        // iterating through filtered events list
        for(Event value : filteredEvents){
            Club hostingClubDetail = value.getHostingClub();
            // creating new event object
            Event requiredEvent = new Event(value.getEventName(), value.getEventLocation(),
                    value.getEventType(),value.getEventDeliveryType(), value.getEventDate(),
                    value.getEventTime(), hostingClubDetail, value.getEventDescription(), value.getEventId());

            // creating observable list
            ObservableList<Event> viewScheduledEvents = EventViewTableStudent.getItems();
            // adding required event to observable list
            viewScheduledEvents.add(requiredEvent);
            // setting observable list to EventViewTableStudent table view
            EventViewTableStudent.setItems(viewScheduledEvents );
        }

    }

    // work done by- Lakshan
    // This method popualate all event in table view
    public void populateAllEvents(){
        for(Event value : Student.studentEvent){
            Club hostingClubDetail = value.getHostingClub();
            // creating new event object
            Event requiredEvent = new Event(value.getEventName(), value.getEventLocation(),
                    value.getEventType(),value.getEventDeliveryType(), value.getEventDate(),
                    value.getEventTime(), hostingClubDetail, value.getEventDescription(), value.getEventId());
            // creating observable list
            ObservableList<Event> viewScheduledEvents = EventViewTableStudent.getItems();
            // adding required event to observable list
            viewScheduledEvents.add(requiredEvent);
            // setting observable list to EventViewTableStudent table view
            EventViewTableStudent.setItems(viewScheduledEvents );
        }
    }

    // work done by- Lakshan
    // This method display event details
    public void displayNumberOfEnrolledClubs(){
        // setting number of enrolled clubs to NumberOfEnrolledClubCountStudent text field
      EnrolledClubCountStudent.setText(String.valueOf(Student.studentJoinedClubs.size()));
    }

    // work done by- Lakshan
    // This method display number of upcoming events
    public void displayNumberOfUpcomingEvents(){
        int count = 0;
        // iterating through student event list
        for(Event event : Student.studentEvent){
            // checking whether event date is after current date
            if(event.getEventDate().isAfter(LocalDate.now())){
                count ++; // incrementing count
            }
        }

        // setting number of upcoming events to NumberOfUpcomingEventForStudent text field
        UpcomingEventForStudent.setText(String.valueOf(count));
    }


    // work done by- Lakshan
    // This method finds next event date for student
    public void findNextEventDateForStudent(){
        // checking whether student event list is empty
        if (Student.studentEvent.isEmpty()) {
            // setting "No events" to nextEventDateForStudent text field
            nextEventDateForStudent.setText("   No events");
            return;
        }

        // getting current date
        LocalDate currentDate = LocalDate.now();

        LocalDate nextDate = null; // creating next date variable

        // iterating through student event list
        for (Event event : Student.studentEvent) {
            // checking whether event date is after current date
            LocalDate eventDate = event.getEventDate();
            // checking whether next date is null or event date is before next date
            if ((eventDate.isAfter(currentDate) || eventDate.isEqual(currentDate)) &&
                    (nextDate == null || eventDate.isBefore(nextDate))) {
                nextDate = eventDate; // setting next date
            }
        }

        // checking whether next date is null
        if (nextDate != null) {
            nextEventDateForStudent.setText("   " + nextDate);
        }

    }


    // work done by- Lakshan
    // This method display event count per club
    public void displayEventCountPerClub() {
        // creating new hash map
        HashMap<String, Integer> clubEventCount = new HashMap<>();

        LocalDate currentDate = LocalDate.now(); // getting current date
        for (Event event : Student.studentEvent) {
            // checking whether event date is after current date
            if(event.getEventDate().isAfter(currentDate)){
                // getting hosting club
                Club club = event.getHostingClub(); // getting hosting club
                String clubName = club.getClubName(); // getting club name
                System.out.println(clubName);

                //  put values to hash map
                clubEventCount.put(clubName, clubEventCount.getOrDefault(clubName, 0) + 1);
            }
        }

        // clearing UpcomingEventRateForTable table view
        UpcomingEventRateForTable.getData().clear();

        // creating new XYChart.Series object
        XYChart.Series setOfData = new XYChart.Series();
        // setting name to XYChart.Series object
        for (Map.Entry<String, Integer> entry : clubEventCount.entrySet()) {
            // adding data to XYChart.Series object
            setOfData.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }
        // setting XYChart.Series object to UpcomingEventRateForTable table view
        UpcomingEventRateForTable.getData().addAll(setOfData);
    }

}