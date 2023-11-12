package ClubAdvisorDashboardManager;
import com.jfoenix.controls.JFXTimePicker;
import ClubManager.EventManager;
import com.example.clubmanagementsystem.ApplicationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class ClubAdvisorActivityController extends ClubAdvisorDashboardControlller{

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scheduleEventDatePicker.setEditable(false);
        updateEventDateDatePicker.setEditable(false);
        populateComboBoxes();
    }

    public void populateComboBoxes(){
        scheduleEventTypeCombo.getItems().addAll("None", "Meeting", "Activity");
        scheduleEventTypeCombo.getSelectionModel().selectFirst();
        ScheduleEventsDeliveryType.getItems().addAll("None", "Online", "Physical");
        ScheduleEventsDeliveryType.getSelectionModel().selectFirst();
        updateEventTypeCombo.getItems().addAll("None", "Meeting", "Activity");
        updateEventTypeCombo.getSelectionModel().selectFirst();
        updateEventDeliveryTypeCombo.getItems().addAll("None", "Online", "Physical");
        updateEventDeliveryTypeCombo.getSelectionModel().selectFirst();
    }

    @Override
    protected void clearScheduleEventFields(ActionEvent event){
        scheduleEventNameTextField.setText("");
        scheduleEventsLocationTextField.setText("");
        scheduleEventDescriptionTextField.setText("");
        scheduleEventDatePicker.setValue(null);
        scheduleEventTypeCombo.getSelectionModel().selectFirst();
        ScheduleEventsDeliveryType.getSelectionModel().selectFirst();
        clearAllScheduleEventLabels();
    }

   @Override
    protected void clearUpdateEventFields(ActionEvent event){
        updateEventClubCombo.getSelectionModel().selectFirst();
        updateEventTypeCombo.getSelectionModel().selectFirst();
        updateEventDeliveryTypeCombo.getSelectionModel().selectFirst();
        updateEventLocationTextField.setText("");
        updateEventNameTextField.setText("");
        updateEventDescription.setText("");
        updateEventDateDatePicker.setValue(null);
        clearAllUpdateEventLabels();
    }

    @FXML
    void CheckNameError(KeyEvent event) {
        String targetName = "TextField[id=scheduleEventNameTextField, styleClass=text-input text-field eventField]";
        String eventName;
        EventManager eventManager = new EventManager();

        if(String.valueOf(event.getTarget()).equals(targetName)){
            eventName = scheduleEventNameTextField.getText();

            System.out.println(event.getTarget());
            if(!eventManager.validateEventName(eventName)){
                scheduleErrorLabelEventName.setText("Event name cannot be empty");
            }else{
                scheduleErrorLabelEventName.setText("");
            }
        }else{
            eventName = updateEventNameTextField.getText();
            System.out.println(event.getTarget());
            if(!eventManager.validateEventName(eventName)){
                updateErrorLabelEventName.setText("Event name cannot be empty");
            }else{
                updateErrorLabelEventName.setText(" ");
            }
        }
    }

    @FXML
    void CheckLocationError(KeyEvent event) {
        String targetLocation = "TextField[id=scheduleEventsLocationTextField, styleClass=text-input text-field eventField]";
        String eventLocation;
        EventManager eventManager = new EventManager();
        if(String.valueOf(event.getTarget()).equals(targetLocation)){
            eventLocation = scheduleEventsLocationTextField.getText();

            System.out.println(event.getTarget());
            if(!eventManager.validateEventLocation(eventLocation)){
                scheduleErrorLabelEventLocation.setText("Event Location cannot be empty");
            }else{
                scheduleErrorLabelEventLocation.setText(" ");
            }
        }else{
            eventLocation = updateEventLocationTextField.getText();
            if(!eventManager.validateEventLocation(eventLocation)){
                updateErrorLabelEventLocation.setText("Event Location cannot be empty");
            }else{
                updateErrorLabelEventLocation.setText(" ");
            }
        }
    }

    @FXML
    void CheckEventTypeError(ActionEvent event) {
        String targetType = "ComboBox[id=scheduleEventTypeCombo, styleClass=combo-box-base combo-box eventField]";
        String selectedOption;
        EventManager eventManager = new EventManager();
        if(String.valueOf(event.getTarget()).equals(targetType)){
            selectedOption = scheduleEventTypeCombo.getSelectionModel().getSelectedItem();
            System.out.println(event.getTarget());
            if(eventManager.validateEventType(selectedOption)){
                System.out.println("Hello1");
                scheduleErrorLabelEventType.setText("Event type cannot be None");
            }else{
                System.out.println("Hello2");
                scheduleErrorLabelEventType.setText(" ");
            }
        }else{
            selectedOption = updateEventTypeCombo.getSelectionModel().getSelectedItem();
            if(eventManager.validateEventType(selectedOption)){
                System.out.println("Hello1");
                updateErrorLabelEventType.setText("Event type cannot be None");
            }else{
                System.out.println("Hello2");
                updateErrorLabelEventType.setText(" ");
            }
        }
    }

    @FXML
    void checkDeliveryTypeError(ActionEvent event) {
        String targetDelivery= "ComboBox[id=ScheduleEventsDeliveryType, styleClass=combo-box-base combo-box eventField]";
        String selectedOption;
        EventManager eventManager = new EventManager();
        System.out.println(event.getTarget());
        if(String.valueOf(event.getTarget()).equals(targetDelivery)){
            selectedOption= ScheduleEventsDeliveryType.getSelectionModel().getSelectedItem();
            if(eventManager.validateEventType(selectedOption)){
                scheduleErrorLabelEventDeliveryType.setText("Event delivery type cannot be None");
            }else{
                scheduleErrorLabelEventDeliveryType.setText(" ");
            }
        }else{
            selectedOption= updateEventDeliveryTypeCombo.getSelectionModel().getSelectedItem();
            if(eventManager.validateEventType(selectedOption)){
                updateErrorLabelDeliveryType.setText("Event delivery type cannot be None");
            }else{
                updateErrorLabelDeliveryType.setText(" ");
            }
        }

    }

    @FXML
    void checkSelectedEventDate(ActionEvent event) {
        String targetDate = "DatePicker[id=scheduleEventDatePicker, styleClass=combo-box-base date-picker eventField]";
        LocalDate selectedDate;
        EventManager eventManager = new EventManager();

        if(String.valueOf(event.getTarget()).equals(targetDate)){
            selectedDate = scheduleEventDatePicker.getValue();

            System.out.println(event.getTarget());
            if(eventManager.validateEventDate(selectedDate)){
                scheduleErrorLabelEventDate.setText("Event date cannot be a past date");
            }else{
                scheduleErrorLabelEventDate.setText(" ");
            }
        }else{
            selectedDate = updateEventDateDatePicker.getValue();

            System.out.println(event.getTarget());
            if(eventManager.validateEventDate(selectedDate)){
                updateErrorLabelEventDate.setText("Event date cannot be a past date");
            }else{
                updateErrorLabelEventDate.setText(" ");
            }

        }

    }

    public void clearAllScheduleEventLabels(){
        scheduleErrorLabelEventName.setText("");
        scheduleErrorLabelEventLocation.setText(" ");
        scheduleErrorLabelEventDate.setText(" ");
        scheduleErrorLabelEventDeliveryType.setText(" ");
        scheduleErrorLabelEventType.setText(" ");
    }

    public void clearAllUpdateEventLabels(){
        updateErrorLabelEventDate.setText(" ");
        updateErrorLabelDeliveryType.setText(" ");
        updateErrorLabelEventType.setText(" ");
        updateErrorLabelEventLocation.setText(" ");
        updateErrorLabelEventName.setText(" ");
    }






































































































































































































































    @Override
    void ClubAdvisorDashboardDetected(MouseEvent event) {
        Stage stage =  (Stage)ClubAdvisorDashboard.getScene().getWindow();
        stage.setX(event.getScreenX()- xPosition);
        stage.setY(event.getScreenY() - yPosition);
    }

    @Override
    void ClubAdvisorPanePressed(MouseEvent event) {
        xPosition = event.getSceneX();
        yPosition = event.getSceneY();
    }

    @Override
    void dashBoardLogOut(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginDashboardManager/ClubAdvisorLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    void MinimizePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.MinimizeApp(ClubAdvisorDashboard);
    }


    @Override
    void ClosePane(ActionEvent event) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.closingApp();
    }

    @Override
    public void makeAllClubAdvisorPanesInvisible(){
        dashboardMainPane.setVisible(false);
        ManageClubPane.setVisible(false);
        ScheduleEventsPane.setVisible(false);
        AttendancePane.setVisible(false);
        GenerateReportsPane.setVisible(false);
        ProfilePane.setVisible(false);
    }

    @Override
    public void makeAllButtonsColoured(){
        dashboardButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        ScheduleEventsButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        AttendanceButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        GenerateReportsButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
        AdvisorProfileButton.setStyle("-fx-background-color: linear-gradient(#ffffd2, #f6d59a, #f6d59a);");
    }

    @Override
    void GoToDashBoardClubAdvisor(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        dashboardMainPane.setVisible(true);
        dashboardButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @Override
    void GoToManageClubPane(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        ManageClubPane.setVisible(true);
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @Override
    void GoToScheduleEvents(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        ScheduleEventsPane.setVisible(true);
        ScheduleEventsButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @Override
    void GoToTrackAttendance(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        AttendancePane.setVisible(true);
        AttendanceButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @Override
    void GoToGenerateReports(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        GenerateReportsPane.setVisible(true);
        GenerateReportsButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
    }

    @Override
    void GoToClubAdvisorProfile(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        ProfilePane.setVisible(true);
        AdvisorProfileButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");

    }


    @Override
    void GoToEventAttendance(ActionEvent event) {
        makeAllPanesInvisibleGeneratingReport();
        EventAttendancePane.setVisible(true);
        GoToEventAttendanceButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");
    }

    @Override
    void GoToClubActivities(ActionEvent event) {
        makeAllPanesInvisibleGeneratingReport();
        ClubActivitiesPane.setVisible(true);
        GoToClubActivitiesButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");
    }

    @Override
    void GoToClubMembership(ActionEvent event) {
        makeAllPanesInvisibleGeneratingReport();
        MembershipReportPane.setVisible(true);
        GoToClubMembershipButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");
    }

    @Override
    public void makeAllPanesInvisibleGeneratingReport(){
        ClubActivitiesPane.setVisible(false);
        EventAttendancePane.setVisible(false);
        MembershipReportPane.setVisible(false);
        GoToClubMembershipButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
                "-fx-text-fill: black;");
        GoToEventAttendanceButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
                "-fx-text-fill: black;");
        GoToClubActivitiesButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
                "-fx-text-fill: black;");
    }

    @Override
    public void makeAllPanesInvisibleEventPane(){
        UpdatesEventPane.setVisible(false);
        ViewEventsPane.setVisible(false);
        ScheduleEventsInnerPane.setVisible(false);
        CancelEventsPane.setVisible(false);
        UpdateEventButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
                "-fx-text-fill: black;");
        ViewEventButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
                "-fx-text-fill: black");
        ScheduleEventButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d)" +
                ";-fx-text-fill: black");
        CancelEventButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
                "-fx-text-fill: black");
    }

    @Override
    void GoToUpdateEventsPanes(ActionEvent event) {
        makeAllPanesInvisibleEventPane();
        UpdatesEventPane.setVisible(true);
        UpdateEventButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");
    }

    @Override
    void GoToViewEventsPane(ActionEvent event) {
        makeAllPanesInvisibleEventPane();
        ViewEventsPane.setVisible(true);
        ViewEventButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");
    }

    @Override
    void GoToScheduleEventsPane(ActionEvent event) {
        makeAllPanesInvisibleEventPane();
        ScheduleEventsInnerPane.setVisible(true);
        ScheduleEventButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");
    }

    @Override
    void GoToCancelEventsPane(ActionEvent event) {
        makeAllPanesInvisibleEventPane();
        CancelEventsPane.setVisible(true);
        CancelEventButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");
    }

    @Override
    public void makeAllClubCreationPanesInvisible(){
        createClubPane.setVisible(false);
        UpdateClubDetailPane.setVisible(false);
        CreateClubDirectorButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
                "-fx-text-fill: black;");
        UpdateClubDirectorButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
                "-fx-text-fill: black;");

    }

    @Override
    void GoToCreateClubPane(ActionEvent event) {
        makeAllClubCreationPanesInvisible();
        createClubPane.setVisible(true);
        CreateClubDirectorButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");
    }

    @Override
    void GoToUpdateClubDetailsPane(ActionEvent event) {
        makeAllClubCreationPanesInvisible();
        UpdateClubDetailPane.setVisible(true);
        UpdateClubDirectorButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");

    }


}
