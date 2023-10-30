package LoginManager;

import com.example.clubmanagementsystem.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    private double xPosition;

    private double yPosition;

    private Scene scene;
    private Stage stage;

    private Parent root;

    @FXML
    private StackPane LoginPane;

    @FXML
    private Circle clubAdvisorCircle;

    @FXML
    private Circle studentCircle;

    @FXML
    void LoginDragDetected(MouseEvent event) {
        Stage stage =  (Stage)LoginPane.getScene().getWindow();
        stage.setX(event.getScreenX()- xPosition);
        stage.setY(event.getScreenY() - yPosition);
    }

    @FXML
    void LoginPanePressed(MouseEvent event) {
        xPosition = event.getSceneX();
        yPosition = event.getSceneY();
    }

    @FXML
    void DirectClubAdvisor(ActionEvent event) throws IOException {System.out.println("Direct to club Advisor");
        Parent root = FXMLLoader.load(getClass().getResource("/LoginManager/ClubAdvisorLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void DirectStudent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginManager/StudentLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String filePath = new File("").getAbsolutePath();
            String clubAdvisorImagePath = filePath + "/src/main/resources/Images/lecturer.jpeg";
            String studentImagePath = filePath + "/src/main/resources/Images/student.jpeg";

            URL clubAdvisorImageUrl = new File(clubAdvisorImagePath).toURI().toURL();
            URL studentImageUrl = new File(studentImagePath).toURI().toURL();

            Image imgClubAdvisor = new Image(clubAdvisorImageUrl.toString());
            Image imgStudent = new Image(studentImageUrl.toString());

            clubAdvisorCircle.setFill(new ImagePattern(imgClubAdvisor));
            studentCircle.setFill(new ImagePattern(imgStudent));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ClosePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    @FXML
    void MinimizePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(LoginPane);
    }



}




