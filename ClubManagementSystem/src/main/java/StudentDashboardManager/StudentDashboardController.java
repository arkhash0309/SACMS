package StudentDashboardManager;

import com.example.clubmanagementsystem.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

abstract public class StudentDashboardController implements Initializable {
    protected double xPosition;

    protected double yPosition;
    @FXML
    protected StackPane StudentDashboard;

    protected Scene scene;
    protected Stage stage;
    private Parent root;
    @FXML
    protected AnchorPane EventStudentPane;
    @FXML
    protected AnchorPane JoinLeaveClubPane;
    @FXML
    protected AnchorPane StudentDashBoardPane;
    @FXML
    protected AnchorPane StudentProfilePane;
    @FXML
    public TextField studentUpdateProfileID;
    @FXML
    public TextField studentUpdateProfileUserName;
    @FXML
    public TextField studentUpdateProfileContactNum;
    @FXML
    public PasswordField studentUpdateProfileExistingPassword;
    @FXML
    public PasswordField studentUpdateProfileNewPassword;
    @FXML
    public PasswordField studentUpdateProfileConfirmPassword;
    @FXML
    public TextField studentUpdateProfileFName;
    @FXML
    public TextField studentUpdateProfileLName;
    @FXML
    public Label studentUpdateIDLabel, studentUpdateFNameLabel, studentUpdateLNameLabel, studentUpdateUserNameLabel,
            studentUpdateContactNumLabel, studentUpdateExistingPasswordLabel, studentUpdateNewPasswordLabel,
            studentUpdateConfirmPasswordLabel, updateGradeLabel;
    @FXML
    public ComboBox<String> studentUpdateProfileGrade;
    @FXML
    public Button dashboardButton;

    @FXML
    protected Button ViewEventButton;

    @FXML
    protected Button ManageclubButton;

    @FXML
    protected Button ProfileDirectorButton;
    @FXML
    abstract void StudentLogout(MouseEvent event) throws IOException;

    abstract public void StudentDashboardDragDetected(MouseEvent mouseEvent);

    abstract public void StudentPanePressed(MouseEvent mouseEvent);

    @FXML
    abstract void MinimizePane(ActionEvent event);


    @FXML
    abstract void ClosePane(ActionEvent event);

    abstract public void makeAllStudentDashBoardPanesInvisible();
    @FXML
    abstract void GoToDashBoard(ActionEvent event);
    @FXML
    abstract public void GoToJoinLeaveClub(ActionEvent actionEvent) throws ClassNotFoundException, SQLException;

    @FXML
    abstract public void GoToEvents(ActionEvent actionEvent);

    abstract public void makeAllStudentButtonsColoured();

}
