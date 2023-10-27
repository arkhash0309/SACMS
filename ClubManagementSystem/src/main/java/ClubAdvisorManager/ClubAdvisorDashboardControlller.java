package ClubAdvisorManager;

import com.example.clubmanagementsystem.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClubAdvisorDashboardControlller {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane justAnchor;

    private double xPosition;

    private double yPosition;

    private Scene scene;
    private Stage stage;

    private Parent root;

    @FXML
    private StackPane ClubAdvisorDashboard;

    @FXML
    private AnchorPane dashboardMainPane;

    @FXML
    private AnchorPane ManageClubPane;

    @FXML
    private AnchorPane ScheduleEventsPane;

    @FXML
    void ClubAdvisorDashboardDetected(MouseEvent event) {
       Stage stage =  (Stage)ClubAdvisorDashboard.getScene().getWindow();
       stage.setX(event.getScreenX()- xPosition);
       stage.setY(event.getScreenY() - yPosition);
    }

    @FXML
    void ClubAdvisorPanePressed(MouseEvent event) {
       xPosition = event.getSceneX();
       yPosition = event.getSceneY();
    }

    @FXML
    void dashBoardLogOut(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginManager/ClubAdvisorLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void MinimizePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(ClubAdvisorDashboard);
    }


    @FXML
    void ClosePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    public void makeAllClubAdvisorPanesInvisible(){
        dashboardMainPane.setVisible(false);
        ManageClubPane.setVisible(false);
        ScheduleEventsPane.setVisible(false);
    }

    @FXML
    void GoToDashBoardClubAdvisor(ActionEvent event) {
       makeAllClubAdvisorPanesInvisible();
       dashboardMainPane.setVisible(true);

    }

    @FXML
    void GoToManageClubPane(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        ManageClubPane.setVisible(true);
    }

}