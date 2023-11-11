package LoginManager;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

public class LoginNavigator {
    public void close(){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.initModality(Modality.APPLICATION_MODAL);
        exitAlert.setTitle("Club Management System");
        exitAlert.setHeaderText("Do you really want to exit the program ?");

        Optional<ButtonType> resultExit = exitAlert.showAndWait();
        if(resultExit.get() == ButtonType.OK){
            Platform.exit();
        }
    }
}
