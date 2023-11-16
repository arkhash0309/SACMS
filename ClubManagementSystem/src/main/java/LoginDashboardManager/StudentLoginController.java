package LoginDashboardManager;

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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.clubmanagementsystem.HelloApplication.statement;


public class StudentLoginController{

    private ArrayList<StudentLoginController> studentCredentialList = new ArrayList<StudentLoginController>();// this array list to store only student credentials from database

    static boolean loginStatus;
    String studentLoginPageUserName;
    String studentLoginPagePassword;
    private Scene scene;
    private Stage stage;

    private Parent root;

    private double xPosition;

    private double yPosition;

    private String confirmPassword;
    @FXML
    private Label studentLoginUserNameErrorLabel;
    @FXML
    private Label studentConfirmPasswordLabel;
    @FXML
    private Label studentIncorrectCredential;

    @FXML
    private Label studentLoginPasswordErrorLabel;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private StackPane StudentLoginForm;

    @FXML
    private Button studentLoginButton;

    @FXML
    private PasswordField studentRegisterPassword;

    @FXML
    private PasswordField studentRegisterConfirmPassword;

    @FXML
    private TextField studentRegisterLastName;
    @FXML
    private TextField PasswordTextField;

    @FXML
    private TextField studentRegisterAdmissionNumber;

    @FXML
    private TextField studentRegisterFirstName;

    @FXML
    private TextField studentRegisterContactNumber;

    @FXML
    private TextField studentRegisterUserName;
    @FXML
    private TextField LoginStudentUserName;
    @FXML
    private TextField studentLoginPassword;


    @FXML
    private ComboBox<String> studentRegisterGrade;

    @FXML
    private ComboBox<String> studentRegisterGender;


    @FXML
    public Label usernameLabel;

    public static boolean validStat = true;

    @FXML
    void DirectToStartPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/clubmanagementsystem/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void StudentLoginPaneDragDetected(MouseEvent event) {
        Stage stage =  (Stage)StudentLoginForm.getScene().getWindow();
        stage.setX(event.getScreenX()- xPosition);
        stage.setY(event.getScreenY() - yPosition);
    }

    @FXML
    void studentLoginPanePressedDetected(MouseEvent event) {
        xPosition = event.getSceneX();
        yPosition = event.getSceneY();
    }

    @FXML
    void minimizeTheProgram(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(StudentLoginForm);
    }

    @FXML
    void ExitTheProgram(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    boolean fieldsChecker() {
        loginStatus = true;
        studentLoginPageUserName = LoginStudentUserName.getText();
        studentLoginPagePassword = studentLoginPassword.getText();
        if(studentLoginPageUserName.isEmpty()){
            loginStatus = false;
            studentLoginUserNameErrorLabel.setText("This field cannot be empty");
        }
        if(studentLoginPagePassword.isEmpty()){
            loginStatus = false;
            studentLoginPasswordErrorLabel.setText("This field cannot be empty");
        }
        return loginStatus;
    }
    //studentCredentialChecker will check whether entered credentials are correct according to the given values
    boolean studentCredentialChecker(){
        String correctPassword = null; // store correct password from database
        String credentialChdeckQuery = "SELECT studentPassword FROM studentCredentials WHERE studentUserName = ?";
        try(PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(credentialChdeckQuery)){ // prepare the statement to execute the code
            preparedStatement.setString(1,studentLoginPageUserName); // we are setting the clubAdvisortLoginPageUserName to where the question mark is
            try(ResultSet results = preparedStatement.executeQuery()) { // results variable will store all the rows in Student table
                while (results.next()) { // this will loop the rows
                    correctPassword = results.getString("studentPassword"); // get the password
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        loginStatus = true;
        if(!studentLoginPagePassword.equals(correctPassword)){
            loginStatus = false;
            studentIncorrectCredential.setText("User name or Password Incorrect");
        }
        return loginStatus;
    }

    public void showTypedPassword() {
        if(showPasswordCheckBox.isSelected()){ // when user select show password checkbox
            studentLoginPassword.setVisible(false); //studentLoginPassword textfield will disable
            PasswordTextField.setVisible(true); // PasswordTextField textfield will enable
            PasswordTextField.setText(studentLoginPassword.getText()); // this will take the values from studnetLoginPassword textfield and will set to PasswordTextField
        }else{ // this will execute if user keep the checkbox as it is
            PasswordTextField.setVisible(false);
            studentLoginPassword.setVisible(true);
            studentLoginPassword.setText(PasswordTextField.getText());
        }
    }



    @FXML
    void DirectToStudentDashboard(ActionEvent event) throws IOException {

        if(!fieldsChecker()){
            return;
        }
        studentLoginUserNameErrorLabel.setText("");
        studentLoginPasswordErrorLabel.setText("");

        if(!studentCredentialChecker()){
            return;
        }
        studentLoginPasswordErrorLabel.setText("");
        System.out.println("Directing to student dashboard");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/clubmanagementsystem/StudentDashboard.fxml"));
        Parent root = loader.load();
        StudentDashboardManager.StudentActivityController studentDashboardController = loader.getController();
        studentDashboardController.dashboardButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2);");
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void GoToStudentRegistration(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/RegisterManager/StudentRegistration.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void DirectToLoginPane(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginDashboardManager/StudentLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void StudentRegistrationChecker(MouseEvent event) throws SQLException, IOException {
        validStat = true;
        String firstName = this.studentRegisterFirstName.getText();
        String lastName = this.studentRegisterLastName.getText();
        String admissionNum = this.studentRegisterAdmissionNumber.getText();
        String contactNum = this.studentRegisterContactNumber.getText();
        String grade = this.studentRegisterGrade.getValue();
        String gender = this.studentRegisterGender.getValue();
        String userName = this.studentRegisterUserName.getText();
        String password = this.studentRegisterPassword.getText();
        String passwordConfirm = this.studentRegisterConfirmPassword.getText();

        System.out.println(admissionNum);
        Student student = new Student(userName, password, firstName, lastName);

        if (!student.validateFirstName()) {
            System.out.println("Wrong first name");
            validStat = true;
        }
        displayNameError("FName");

        if (!student.validateLastName()) {
            System.out.println("Wrong last name");
            validStat = true;
        }

        displayNameError("LName");
        try {
            String tempContactVal = contactNum;
            if (tempContactVal.isEmpty()) {
                User.contactNumberValidateStatus = "empty";
                throw new Exception();
            }
            Double.parseDouble(contactNum.trim());
            Student std1 = new Student(tempContactVal);

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
        displayContactValError();

        try {
            if(admissionNum.isEmpty()) {
                validStat = false;
                Student.admissionNumStatus = "empty";
                throw new Exception();
            }
            int admissionNumValue = Integer.parseInt(admissionNum);
            Student std2 = new Student(admissionNumValue);

            if(!std2.validateStudentAdmissionNumber()) {
                System.out.println("Invalid");
                validStat = false;
            } else {
                Student.admissionNumStatus = "";
            }
        } catch (NumberFormatException e) {
            Student.admissionNumStatus = "format";
            System.out.println("Invalid Advisor Id");
            validStat = false;
        } catch (Exception e) {
            validStat = false;
        }
        displayAdmissionNumError();

        if (!student.validateUserName("registration","student")) {
            System.out.println("Wrong user name");
            validStat = false;
        } else {
            User.userNameValidateStatus = "";
        }
        displayUserNameError();

        if (!student.validatePassword("registration")) {
            System.out.println("Wrong password name");
            validStat = false;
        } else {
            User.passwordValidateStatus = "";
        }
        displayPasswordError();

        if(passwordConfirm.isEmpty()){
            validStat = false;
            studentConfirmPasswordLabel.setText("Confirm password cannot be empty");
        } else if (!confirmPassword.equals(password)){
            studentConfirmPasswordLabel.setText("Wrong confirm password ");
            validStat = false;
        }else{
            studentConfirmPasswordLabel.setText(" ");
        }

        System.out.println(validStat + " : Valid Stat");
        if (validStat) {
            Student studentData = new Student(userName, password, firstName, lastName);
            Student.studentDetailArray.add(studentData);

            this.DirectToLoginPane(event);
        }
        System.out.println("\n\n\n");

        String insertingQuery
                = "insert into Student('studentAdmissionNum','studentFName','studentLName','studentGrade','studentContactNum','Gender') values(?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(insertingQuery)) {
            preparedStatement.setString(1,admissionNum);
            preparedStatement.setString(2,firstName);
            preparedStatement.setString(3,lastName);
            preparedStatement.setString(4,grade);
            preparedStatement.setString(5,contactNum);
            preparedStatement.setString(6,gender);
            statement.executeUpdate(insertingQuery);
        }
    }

    public void displayNameError(String nameType) {
        if (nameType.equals("FName")) {
            if(Student.fNameValidateStatus.equals("empty")) {
                System.out.println("First name cannot be empty.");
            } else if (Student.fNameValidateStatus.equals("format")) {
                System.out.println("First name can only contain letters.");
            } else {
                System.out.println(" ");
            }
        } else if (nameType.equals("LName")) {
            if(Student.lNameValidateStatus.equals("empty")) {
                System.out.println("Last name cannot be empty.");
            } else if (Student.lNameValidateStatus.equals("format")) {
                System.out.println("Last name can only contain letters.");
            } else {
                System.out.println(" ");
            }
        }
    }

    public void displayContactValError() {
        if (User.contactNumberValidateStatus.equals("empty")) {
            System.out.println("Contact number cannot be empty.");
        } else if (User.contactNumberValidateStatus.equals("length")) {
            System.out.println("Contact number should consist of 10 digits.");
        } else if (User.contactNumberValidateStatus.equals("format")) {
            System.out.println("Contact number should contain only numbers.");
        } else {
            System.out.println(" ");
        }
    }

    public void displayAdmissionNumError() {
        if (Student.admissionNumStatus.equals("empty")) {
            System.out.println("Admission number cannot be empty.");
        } else if (Student.admissionNumStatus.equals("length")) {
            System.out.println("Admission number can have a maximum of 4 digits.");
        } else if (Student.admissionNumStatus.equals("exist")) {
            System.out.println("Admission number already exists.");
        } else if (Student.admissionNumStatus.equals("format")) {
            System.out.println("Admission number should contain only numeric values.");
        } else {
            System.out.println(" ");
        }
    }

    public void displayUserNameError() {
        if (User.userNameValidateStatus.equals("empty")) {
            System.out.println("Username cannot be empty.");
        } else if (User.userNameValidateStatus.equals("exists")) {
            System.out.println("The entered username already exists.");
        } else if (User.userNameValidateStatus.equals("blank")) {
            System.out.println("Username cannot contain blank spaces.");
        } else if (User.userNameValidateStatus.equals("length")) {
            System.out.println("Length of username should be 5 to 10 digits.");
        } else {
            System.out.println(" ");
        }
    }

    public void displayPasswordError() {
        if (User.passwordValidateStatus.equals("empty")) {
            System.out.println("Password cannot be empty.");
        } else if (User.passwordValidateStatus.equals("format")) {
            System.out.println("Password should consist of 8 characters including numbers and special characters.");
        }else {
            System.out.println(" ");
        }
    }




}
