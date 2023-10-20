package LoginManager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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
       Stage stage = (Stage) studentLoginButton.getScene().getWindow();
       stage.setIconified(true);
    }

    @FXML
    void ExitTheProgram(ActionEvent event) {
       LoginNavigator loginNavigator = new LoginNavigator();
       loginNavigator.close();
    }
}
