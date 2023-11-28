package LoginDashboardManager;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

// work done by- Lakshan
public class LoginNavigator {
    // this method is used to close the application
    public void close(){
        // Alert box to confirm the exit
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.initModality(Modality.APPLICATION_MODAL);
        exitAlert.setTitle("Club Management System");
        exitAlert.setHeaderText("Do you really want to exit the program ?");
        // if user press ok button then the application will be closed
        Optional<ButtonType> resultExit = exitAlert.showAndWait();
        if(resultExit.get() == ButtonType.OK){
            Platform.exit();
        }
    }
}
