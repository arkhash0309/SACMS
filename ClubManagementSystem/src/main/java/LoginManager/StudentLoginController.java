package LoginManager;

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


public class StudentLoginController {
    private Scene scene;
    private Stage stage;

    private Parent root;

    private double xPosition;

    private double yPosition;

    @FXML
    private StackPane StudentLoginForm;

    @FXML
    private Button studentLoginButton;

    @FXML
    private PasswordField studentPassword;

    @FXML
    private PasswordField studentConfirmPassword;

    @FXML
    private TextField studentLastName;

    @FXML
    private TextField studentAdmissionNumber;

    @FXML
    private TextField studentFirstName;

    @FXML
    private TextField studentContactNumber;

    @FXML
    private ComboBox<?> studentGrade;

    @FXML
    private ComboBox<?> studentGender;

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

    @FXML
    void DirectToStudentDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/clubmanagementsystem/StudentDashboard.fxml"));
        Parent root = loader.load();
        StudentManager.StudentDashboardController studentDashboardController = loader.getController();
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
        Parent root = FXMLLoader.load(getClass().getResource("/LoginManager/StudentLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void StudentRegistrationChecker(ActionEvent event) {

    }



}
