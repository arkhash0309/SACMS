package StudentManager;

import com.example.clubmanagementsystem.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentDashboardController {
    private double xPosition;

    private double yPosition;
    @FXML
    private StackPane StudentDashboard;

    private Scene scene;
    private Stage stage;

    private Parent root;

    @FXML
    private AnchorPane EventStudentPane;

    @FXML
    private AnchorPane JoinLeaveClubPane;

    @FXML
    private AnchorPane StudentDashBoardPane;

    @FXML
    private AnchorPane StudentProfilePane;
    @FXML
    public Button dashboardButton;

    @FXML
    private Button ViewEventButton;

    @FXML
    private Button ManageclubButton;

    @FXML
    private Button ProfileDirectorButton;

    @FXML
    void StudentLogout(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginManager/StudentLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void StudentDashboardDragDetected(MouseEvent mouseEvent) {
        Stage stage =  (Stage)StudentDashboard.getScene().getWindow();
        stage.setX(mouseEvent.getScreenX()- xPosition);
        stage.setY(mouseEvent.getScreenY() - yPosition);
    }

    public void StudentPanePressed(MouseEvent mouseEvent) {
        xPosition = mouseEvent.getSceneX();
        yPosition = mouseEvent.getSceneY();
    }

    @FXML
    void MinimizePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(StudentDashboard);
    }


    @FXML
    void ClosePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    public void makeAllStudentDashBoardPanesInvisible(){
       EventStudentPane.setVisible(false);
       JoinLeaveClubPane.setVisible(false);
       StudentDashBoardPane.setVisible(false);
        StudentProfilePane.setVisible(false);
    }

    @FXML
    void GoToDashBoard(ActionEvent event) {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        StudentDashBoardPane.setVisible(true);
        dashboardButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }
    @FXML
    public void GoToJoinLeaveClub(ActionEvent actionEvent) {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        JoinLeaveClubPane.setVisible(true);
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @FXML
    public void GoToEvents(ActionEvent actionEvent) {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        EventStudentPane.setVisible(true);
        ViewEventButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @FXML
    void GoToStudentProfile(ActionEvent event) {
        makeAllStudentDashBoardPanesInvisible();
        makeAllStudentButtonsColoured();
        StudentProfilePane.setVisible(true);
        ProfileDirectorButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");

    }

    public void makeAllStudentButtonsColoured(){
        dashboardButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ViewEventButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ProfileDirectorButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
    }
}
