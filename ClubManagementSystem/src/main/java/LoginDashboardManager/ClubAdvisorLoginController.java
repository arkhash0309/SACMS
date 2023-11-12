package LoginDashboardManager;

import SystemUsers.ClubAdvisor;
import SystemUsers.User;
import com.example.clubmanagementsystem.ApplicationController;
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
import java.sql.SQLException;

public class ClubAdvisorLoginController {
    @FXML
    private StackPane clubAdvisorStackPane;
    private Scene scene;
    private Stage stage;

    private Parent root;

    private double xPosition;

    private double yPosition;

    @FXML
    private Button ClubAdvisorLoginMinimizer;

    @FXML
    private TextField advisorUserName;

    @FXML
    private PasswordField advisorPassword;

    @FXML
    private PasswordField advisorConfirmPassword;

    @FXML
    private TextField advisorLastName;

    @FXML
    private TextField advisorId;

    @FXML
    private TextField advisorFirstName;

    @FXML
    private TextField advisorContactNumber;

    @FXML
    private Label advisorIdLabel;

    @FXML
    private Label advisorFirstNameLabel;

    @FXML
    private Label advisorLastNameLabel;

    @FXML
    private Label contactNumberLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private CheckBox showPassword;

    @FXML
    private PasswordField PasswordFieldLogin;

    @FXML
    private TextField PasswordTextField;

    @FXML
    private Label passwordCommentLogin;


    public static boolean validStat = true;

    @FXML
    void DirectToStartPane(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/clubmanagementsystem/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clubAdvisorPaneDragDetector(MouseEvent event) {
        Stage stage =  (Stage)clubAdvisorStackPane.getScene().getWindow();
        stage.setX(event.getScreenX()- xPosition);
        stage.setY(event.getScreenY() - yPosition);
    }

    @FXML
    void clubAdvisorPanePressDetector(MouseEvent event) {
        xPosition = event.getSceneX();
        yPosition = event.getSceneY();
    }

    @FXML
    void MinimizeClubAdvisorLogin(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(clubAdvisorStackPane);
    }


    @FXML
    void ClubAdvisorLoginExit(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    @FXML
    public void DirectToStudentDashBoard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/clubmanagementsystem/ClubAdvisorDashboard.fxml"));
        Parent root = loader.load();
        ClubAdvisorDashboardManager.ClubAdvisorActivityController clubAdvisorDashboardControlller = loader.getController();
        clubAdvisorDashboardControlller.dashboardButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2);");
        clubAdvisorDashboardControlller.ViewEventButton.setStyle("-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779); " +
                "-fx-text-fill: white");
        clubAdvisorDashboardControlller.GoToClubMembershipButton.setStyle("-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779); " +
                "-fx-text-fill: white");
        clubAdvisorDashboardControlller.CreateClubDirectorButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    @FXML
    void GoToRegisterForm(ActionEvent event) throws IOException {
        ClubAdvisor.advisorIdStatus = "";
        Parent root = FXMLLoader.load(getClass().getResource("/RegisterManager/ClubAdvisorRegistration.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void DirectToLoginPane(MouseEvent event) throws IOException {
        this.goToLoginPage(event);
    }

    public void goToLoginPage(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginDashboardManager/ClubAdvisorLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void AdvisorRegistrationChecker(MouseEvent event) throws SQLException, IOException {
        validStat = true;
        String firstName = this.advisorFirstName.getText();
        String lastName = this.advisorLastName.getText();
        String advisorId = this.advisorId.getText();
        String contactNum = this.advisorContactNumber.getText();
        String userName = this.advisorUserName.getText();
        String password = this.advisorPassword.getText();
        String confirmPassword = this.advisorConfirmPassword.getText();

        System.out.println(advisorId);

        ClubAdvisor clubAdvisor = new ClubAdvisor(userName, password, firstName, lastName);

        if(!clubAdvisor.validateFirstName()){
            System.out.println("Wrong First Name");
            validStat = false;
        }

        displayNameError("Fname");

        if(!clubAdvisor.validateLastName()){
            System.out.println("Wrong Last Name");
            validStat = false;
        }
        displayNameError("Lname");


        try {
            String tempContactVal = contactNum;
            if(tempContactVal.isEmpty()){
                User.contactNumberValidateStatus = "empty";
                throw new Exception();
            }

            Double.parseDouble(contactNum.trim());
            ClubAdvisor cb = new ClubAdvisor(tempContactVal);

            if (!cb.validateContactNumber()) {
                validStat = false;
                System.out.println("Invalid Contact Number 1");
            }else{
                User.contactNumberValidateStatus ="";
            }
        }catch(NumberFormatException e){
            System.out.println("Invalid Contact Number 2");
            User.contactNumberValidateStatus = "format";
            validStat = false;
        }
        catch (Exception e) {
            validStat = false;
        }
        displayContactValError();


        try {
            if(advisorId.isEmpty()){
                validStat = false;
                ClubAdvisor.advisorIdStatus ="empty";
                throw new Exception();
            }
            int advisorIdValue = Integer.parseInt(advisorId);
            ClubAdvisor cb2 = new ClubAdvisor(advisorIdValue);

            if (!cb2.validateClubAdvisorId()) {
                System.out.println("Invalid Advisor Id 111");
                validStat = false;
            }else{
                ClubAdvisor.advisorIdStatus = "";
            }
        }catch(NumberFormatException e){
            ClubAdvisor.advisorIdStatus ="format";
            System.out.println("Invalid Advisor Id");
            validStat = false;
        }
        catch (Exception e) {
            validStat = false;
        }

        displayIdError();

        if(!clubAdvisor.validateUserName("registration", "student")){
            System.out.println("Wrong User Name");
            validStat = false;
        }else{
            User.userNameValidateStatus = "";
        }

        displayUserNameError();


        if(!clubAdvisor.validatePassword("registration")){
            System.out.println("Wrong Password Name");
            validStat = false;
        }else{
            User.passwordValidateStatus = "";
        }
        displayPasswordError();

        if(confirmPassword.isEmpty()){
            validStat = false;
            confirmPasswordLabel.setText("Confirm password cannot be empty");
        } else if (!confirmPassword.equals(password)){
            confirmPasswordLabel.setText("Wrong confirm password ");
            validStat = false;
        }else{
            confirmPasswordLabel.setText(" ");
        }

        System.out.println(validStat + " : Valid Stat");
        if(validStat){
            ClubAdvisor clubAdvisorData = new ClubAdvisor(userName, password, firstName, lastName,
                    contactNum, Integer.parseInt(advisorId));
            ClubAdvisor.clubAdvisorDetailsList.add(clubAdvisorData);

            this.goToLoginPage(event);
        }
        System.out.println("\n\n\n");

    }

    public void displayIdError(){
        if(ClubAdvisor.advisorIdStatus.equals("empty")){
            advisorIdLabel.setText("Advisor Id cannot be empty");
        }else if(ClubAdvisor.advisorIdStatus.equals("length")){
            advisorIdLabel.setText("Advisor Id should have 6 digits");
        }else if(ClubAdvisor.advisorIdStatus.equals("exist")){
            advisorIdLabel.setText("Advisor Id already exists");
        }else if(ClubAdvisor.advisorIdStatus.equals("format")){
            advisorIdLabel.setText("Advisor Id should be in numbered format");
        }else{
            advisorIdLabel.setText("");
        }
    }

    public void displayNameError(String nameType){
        if(nameType.equals("Fname")){
            if(ClubAdvisor.fNameValidateStatus.equals("empty")){
                advisorFirstNameLabel.setText("First name cannot be empty");
            }else if(ClubAdvisor.fNameValidateStatus.equals("format")){
                advisorFirstNameLabel.setText("First name can contain only letters");
            }else{
                advisorFirstNameLabel.setText("");
            }
        }else if (nameType.equals("Lname")){
            if(ClubAdvisor.lNameValidateStatus.equals("empty")){
                advisorLastNameLabel.setText("Last name cannot be empty");
            }else if(ClubAdvisor.lNameValidateStatus.equals("format")){
                advisorLastNameLabel.setText("Last name contain only letters");
            }else{
                advisorLastNameLabel.setText("");
            }
        }
    }

    public void displayContactValError(){
        if(User.contactNumberValidateStatus.equals("empty")){
            contactNumberLabel.setText("Contact Number cannot be empty");
        } else if (User.contactNumberValidateStatus.equals("length")) {
            contactNumberLabel.setText("Contact Number should have 10 numbers.");
        }else if(User.contactNumberValidateStatus.equals("format")){
            contactNumberLabel.setText("it should consist with only numbers");
        }else{
            contactNumberLabel.setText("");
        }
    }

    public void displayUserNameError(){
        if(User.userNameValidateStatus.equals("empty")){
           userNameLabel.setText("User name cannot be empty");
        }else if(User.userNameValidateStatus.equals("exist")){
            userNameLabel.setText("User name already exists");
        }else if(User.userNameValidateStatus.equals("blank")){
            userNameLabel.setText("User name cannot contain spaces");
        } else if (User.userNameValidateStatus.equals("length")) {
            userNameLabel.setText("length should be 5 to 10 digits");
        }else{
            userNameLabel.setText("");
        }
    }

    public void displayPasswordError(){
        if(User.passwordValidateStatus.equals("empty")){
            passwordLabel.setText("Password cannot be empty");
        }else if(User.passwordValidateStatus.equals("format"))
        {
            passwordLabel.setText("""
                    Password should consist with at least 8
                    characters including numbers and special
                    characters""");
            passwordLabel.setStyle("-fx-text-alignment: justify;");
        }else{
            passwordLabel.setText("");
        }
    }

    @FXML
    void showTypedPassword(ActionEvent event) {
        if(showPassword.isSelected()){
            PasswordFieldLogin.setVisible(false);
            PasswordTextField.setVisible(true);
            PasswordTextField.setText(PasswordFieldLogin.getText());
        }else{
            PasswordTextField.setVisible(false);
            PasswordFieldLogin.setVisible(true);
            PasswordFieldLogin.setText(PasswordTextField.getText());
        }

    }



    public void clearAllValidateLabels(){

    }

    public void work(){

    }








}
