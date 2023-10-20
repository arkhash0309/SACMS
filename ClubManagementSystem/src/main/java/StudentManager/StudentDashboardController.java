package StudentManager;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StudentDashboardController {
    private double xPosition;

    private double yPosition;
    @FXML
    private StackPane StudentDashboard;
    @FXML
    void StudentDashboardDragDetected(MouseEvent event) {
        Stage stage =  (Stage)StudentDashboard.getScene().getWindow();
        stage.setX(event.getScreenX()- xPosition);
        stage.setY(event.getScreenY() - yPosition);
    }

    @FXML
    void StudentDashboardPressed(MouseEvent event) {
        xPosition = event.getSceneX();
        yPosition = event.getSceneY();
    }
}
