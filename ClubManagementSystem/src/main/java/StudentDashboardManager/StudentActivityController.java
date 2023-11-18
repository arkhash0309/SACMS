package StudentDashboardManager;

import SystemUsers.Student;
import SystemUsers.User;
import com.example.clubmanagementsystem.ApplicationController;
import com.example.clubmanagementsystem.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentActivityController extends StudentDashboardController{

    public static boolean validStat = true;
    static int studentAdmissionNum;

    public static String existingUserName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int grade = 6; grade<14; grade++) {
            studentUpdateProfileGrade.getItems().add(String.format("%02d", grade));
        }
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
    }

    @Override
    public void GoToJoinLeaveClub(ActionEvent actionEvent) {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        JoinLeaveClubPane.setVisible(true);
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }


    @Override
    public void GoToEvents(ActionEvent actionEvent) {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        EventStudentPane.setVisible(true);
        ViewEventButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @FXML
    void studentProfileDirector(ActionEvent event) {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        StudentProfilePane.setVisible(true);
        ProfileDirectorButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }


    public void onStudentProfileUpdateButtonClick() {
        validStat = true;


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

        Student student = new Student(studentUpdateProfileUserName.getText(), studentUpdateProfileExistingPassword.getText(),
                studentUpdateProfileFName.getText(), studentUpdateProfileLName.getText());

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
        } catch (NumberFormatException e) {
//            Student.admissionNumStatus = "format";
//            System.out.println("Invalid Student ID");
//            validateStatus = false;
        } catch (Exception e) {
            validStat = false;
        }
        displayAdmissionNumError();
        try{
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
        } catch(NumberFormatException e) {
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



        System.out.println(validStat + " : Valid Stat");
        if (validStat) {
            System.out.println("Not implemented yet");
//            Student.studentDetailArray.set()
//            Student updateDataStudent = new Student(updatedUserName, updatedPassword, updatedFirstName, updatedLastName);
//            Student.studentDetailArray.add(updateDataStudent);
        }
        System.out.println("\n\n\n");
    }

    public void onStudentProfilePasswordChangeButtonClick() throws SQLException {
        String updatedPassword = this.studentUpdateProfileNewPassword.getText();
        String updateConfirmPassword = this.studentUpdateProfileConfirmPassword.getText();

        Student newStd = new Student(updatedPassword);
        if (!newStd.validatePassword("update")) {
            System.out.println("Wrong password.");
            validStat = false;
        } else {
            User.passwordValidateStatus = "";
        }
        displayPasswordError();

        if (updateConfirmPassword.isEmpty()) {
            studentUpdateConfirmPasswordLabel.setText("Cannot be empty.");
            validStat = false;

        } else if (!updateConfirmPassword.equals(updatedPassword)) {
            studentUpdateConfirmPasswordLabel.setText("Passwords do not match");
            validStat = false;
        } else {
            studentUpdateConfirmPasswordLabel.setText("");
        }

        if (validStat) {
            studentUpdateProfileExistingPassword.setText(updatedPassword);
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

    public void displayPasswordError() {
        if (User.passwordValidateStatus.equals("empty")) {
            studentUpdateNewPasswordLabel.setText("Password cannot be empty.");
        } else if (User.passwordValidateStatus.equals("format")) {
            studentUpdateNewPasswordLabel.setText("Password should consists of 8 characters including numbers and special characters.");
        } else {
            studentUpdateNewPasswordLabel.setText("");
        }
    }

    @Override
    public void makeAllStudentButtonsColoured(){
        dashboardButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ViewEventButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ProfileDirectorButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
    }
    public void displayAdmissionNumError() {
        if (Student.admissionNumStatus.equals("empty")) {
            studentUpdateIDLabel.setText("Admission Number cannot be empty.");
        } else if (Student.admissionNumStatus.equals("length")) {
            studentUpdateIDLabel.setText("Admission Number has to be 6 digits.");
        } else if (Student.admissionNumStatus.equals("exist")) {
            studentUpdateIDLabel.setText("Admission Number already exists.");
        } else if (Student.admissionNumStatus.equals("format")) {
            studentUpdateIDLabel.setText("Admission Number contain only numbers.");
        } else {
            studentUpdateIDLabel.setText("");
        }
    }



    @FXML
    void studentJoinClub(){

    }


    @FXML
    void studentLeaveClub(){

    }
}
