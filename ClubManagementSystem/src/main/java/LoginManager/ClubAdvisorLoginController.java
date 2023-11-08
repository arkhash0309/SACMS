package LoginManager;

import SystemUsers.ClubAdvisor;
import com.example.clubmanagementsystem.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    private Label advisorLabel;

    @FXML
    private Label advisorIdLabel;

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
    void DirectToStudentDashBoard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/clubmanagementsystem/ClubAdvisorDashboard.fxml"));
        Parent root = loader.load();
        ClubAdvisorManager.ClubAdvisorDashboardControlller clubAdvisorDashboardControlller = loader.getController();
        clubAdvisorDashboardControlller.dashboardButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2);");
        clubAdvisorDashboardControlller.ViewEventButton.setStyle("-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779); " +
                "-fx-text-fill: white");
        clubAdvisorDashboardControlller.GoToClubMembershipButton.setStyle("-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779); " +
                "-fx-text-fill: white");
        clubAdvisorDashboardControlller.CreateClubDirectorButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
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
        Parent root = FXMLLoader.load(getClass().getResource("/LoginManager/ClubAdvisorLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void AdvisorRegistrationChecker(MouseEvent event) throws SQLException, IOException {
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

        if(!clubAdvisor.validateLastName()){
            System.out.println("Wrong Last Name");
            validStat = false;
        }

        if(!clubAdvisor.validateUserName("registration", "student")){
            System.out.println("Wrong User Name");
            validStat = false;
        }

        try {
            String tempContactVal = contactNum;
            Integer.parseInt(contactNum.trim());
            ClubAdvisor cb = new ClubAdvisor(tempContactVal);

            if (!cb.validateContactNumber()) {
                validStat = false;
                System.out.println("Invalid Contact Number 1");
            }
        } catch (Exception e) {
            System.out.println("Invalid Contact Number 2");
            ClubAdvisor.advisorIdStatus = "format";
            validStat = false;
        }


        try {
            if(advisorId.isEmpty()){
                validStat = false;
                ClubAdvisor.advisorIdStatus ="empty";
            }
            int advisorIdValue = Integer.parseInt(advisorId);
            ClubAdvisor cb2 = new ClubAdvisor(advisorIdValue);

            if (!cb2.validateClubAdvisorId()) {
                System.out.println("Invalid Advisor Id");
                validStat = false;
            }else{
                ClubAdvisor.advisorIdStatus = "";
            }
        } catch (Exception e) {
            System.out.println("Invalid Advisor Id");
            validStat = false;
        }

        displayIdError();


        if(!clubAdvisor.validatePassword("registration")){
            System.out.println("Wrong Password Name");
            validStat = false;
        }

        if(!confirmPassword.equals(password)){
            System.out.println("Wrong Confirm password");
            validStat = false;
        }

        if(validStat){
            ClubAdvisor clubAdvisorData = new ClubAdvisor(userName, password, firstName, lastName,
                    contactNum, Integer.parseInt(advisorId));
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

    public void clearAllValidateLabels(){

    }

    public void work(){

    }








}
