package LoginDashboardManager;

import com.example.clubmanagementsystem.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    private double xPosition; // x position of the mouse

    private double yPosition; // y position of the mouse

    private Scene scene; // scene of the current stage
    private Stage stage; // current stage

    private Parent root;

    @FXML
    private StackPane LoginPane; //

    @FXML
    private Circle clubAdvisorCircle;

    @FXML
    private Circle studentCircle;


    // work done by- Lakshan
    // This method is responsible for dragging the login window
    @FXML
    void LoginDragDetected(MouseEvent event) {
        // Getting the current stage associated with the provided stackPane
        Stage stage = (Stage) LoginPane.getScene().getWindow();
        stage.setX(event.getScreenX() - xPosition);
        stage.setY(event.getScreenY() - yPosition);
    }
    
    // work done by- Lakshan
    // This method is responsible for dragging the login window
    @FXML
    void LoginPanePressed(MouseEvent event) {
        // Getting the current stage associated with the provided stackPane
        xPosition = event.getSceneX();
        yPosition = event.getSceneY();
    }

    // work done by- Lakshan and Deelaka
    // Login sequence diagram : 1.1 : DirectClubAdvisor()
    // This method is responsible for directing the user to the club advisor login page
    // Registration sequence : 1.1 : DirectClubAdvisor()
    @FXML
    void DirectClubAdvisor(ActionEvent event) throws IOException {
        System.out.println("Direct to club Advisor");
        // Loading the club advisor login page
        Parent root = FXMLLoader.load(getClass().getResource("/LoginDashboardManager/ClubAdvisorLogin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // work done by- Lakshan and Deelaka
    // This method is responsible for directing the user to the student login page
    // Registration sequence : 1.1.1.1.1.4.1.1.1.1 : DirectStudent()
    // Login student :  1.1.2.1.1.1.2.1.1.1 : DirectStudent()
    @FXML
    void DirectStudent(ActionEvent event) throws IOException {
        System.out.println("Direct to student");
        // Loading the student login page
        Parent root = FXMLLoader.load(getClass().getResource("/LoginDashboardManager/StudentLogin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // work done by- Lakshan
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // work done by- Lakshan
    // This method is responsible for closing the application
    @FXML
    void ClosePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    // work done by- Lakshan
    // This method is responsible for minimizing the application
    @FXML
    void MinimizePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(LoginPane);
    }
}