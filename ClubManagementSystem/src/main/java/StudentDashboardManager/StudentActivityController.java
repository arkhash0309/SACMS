package StudentDashboardManager;

import SystemUsers.Student;
import SystemUsers.User;
import com.example.clubmanagementsystem.ApplicationController;
import com.example.clubmanagementsystem.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentActivityController extends StudentDashboardController{
    public static boolean validStat = true;
    static int studentAdmissionNum;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int grade = 0; grade<13; grade++) {
            studentUpdateProfileGrade.getItems().add(String.format("%02d", grade));
        }
        studentUpdateProfileGrade.getSelectionModel().selectFirst();
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

    @Override
    void GoToStudentProfile(MouseEvent mouseEvent) throws SQLException {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        StudentProfilePane.setVisible(true);
        ProfileDirectorButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        String name = Student.studentDetailArray.get(0).getFirstName();
        studentUpdateProfileUserName.setText(Student.studentDetailArray.get(0).getUserName());
        studentUpdateProfileExistingPassword.setText(Student.studentDetailArray.get(1).getPassword());
        studentUpdateProfileFName.setText(Student.studentDetailArray.get(2).getFirstName());
        studentUpdateProfileLName.setText(Student.studentDetailArray.get(3).getLastName());
        studentUpdateProfileContactNum.setText(Student.studentDetailArray.get(4).getContactNumber());
//        studentUpdateProfileGrade.setValue(Integer.parseInt(Student.studentDetailArray.get(6).getStudentGrade()));
        onStudentProfileUpdateButtonClick();
        onStudentProfilePasswordChangeButtonClick();
    }


    public void onStudentProfileUpdateButtonClick() {
        validStat = true;
        String updatedFirstName = this.studentUpdateProfileFName.getText();
        String updatedLastName = this.studentUpdateProfileLName.getText();
        String updatedUserName = this.studentUpdateProfileUserName.getText();
        String updatedContactNum = this.studentUpdateProfileContactNum.getText();
        String updatedGrade = this.studentUpdateProfileGrade.getValue();

        Student newStudent = new Student(updatedUserName, updatedFirstName, updatedLastName,
                updatedContactNum);

        if (!newStudent.validateFirstName()) {
            System.out.println("Incorrect First Name.");
            validStat = false;
        }
        displayNameError("firstName");

        if (!newStudent.validateLastName()) {
            System.out.println("Incorrect Last Name.");
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
        } catch(NumberFormatException e) {
            System.out.println("Invalid ContactNumber 2");
            User.contactNumberValidateStatus = "format";
            validStat = false;
        } catch (Exception e) {
            validStat = false;
        }
        displayContactNumError();

        if (!newStudent.validateUserName("updation", "student")) {
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
            studentUpdateContactNumLabel.setText("Contact number should contain only numbers.");
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


}
