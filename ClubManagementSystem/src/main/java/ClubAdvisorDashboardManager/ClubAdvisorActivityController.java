package ClubAdvisorDashboardManager;

import ClubManager.Club;
import SystemUsers.ClubAdvisor;
import SystemUsers.Student;
import SystemUsers.User;
import com.example.clubmanagementsystem.ApplicationController;
import ClubManager.Attendance;
import ClubManager.Event;
import ClubManager.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.HttpCookie;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.*;
import static ClubManager.Club.clubDetailsList;
import static SystemUsers.ClubAdvisor.clubAdvisorDetailsList;

import java.time.LocalDate;
import java.time.LocalTime;


public class ClubAdvisorActivityController extends ClubAdvisorDashboardControlller{
    public static String username;
    public static boolean validStat = true;
    public static int selectedEventId;
    public static Event selectedEventValue;
    final FileChooser fileChooser = new FileChooser();
    public static String imagePath;
    public static int clubIdSetterValue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scheduleEventDatePicker.setEditable(false);
        updateEventDateDatePicker.setEditable(false);
        populateComboBoxes();
        findMaleFemaleStudentCount();
        displayEnrolledStudentCount();
        //Set cell value factories for the columns of the Create Club Table
        createClubTableId.setCellValueFactory(new PropertyValueFactory<>("clubId"));
        createClubTableName.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        createClubTableDescription.setCellValueFactory(new PropertyValueFactory<>("clubDescription"));
        createClubTableLogo.setCellValueFactory(new PropertyValueFactory<>("absoluteImage"));

        // the columns are initialized for the attendance tracking table
        attendanceClubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        attendanceEventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        attendanceStudentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        attendanceStudentAdmissionNumColumn.setCellValueFactory(new PropertyValueFactory<>("studentAdmissionNum"));
        attendanceStatusColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceStatus"));

//        Club club1 = new Club(0001, "Rotaract", "Done with the work", "lkt.img");
//        clubDetailsList.add(club1);
//        ObservableList<Club> observableClubDetailsList = FXCollections.observableArrayList();
        for (Club club : clubDetailsList){
            if (clubDetailsList == null){
                return;
            }
//            observableClubDetailsList.add(club);
        }
//        createClubDetailsTable.setItems(observableClubDetailsList);


        //Set cell value factories for the columns of the Update Club  Table
        updateClubTableId.setCellValueFactory(new PropertyValueFactory<>("clubId"));
        updateClubTableName.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        updateClubTableDescription.setCellValueFactory(new PropertyValueFactory<>("clubDescription"));
        updateClubTableLogo.setCellValueFactory(new PropertyValueFactory<>("absoluteImage"));

        displayNumberOfScheduledEvents();
        getNextEventDate();
        displayStudentUpdateDetails();
        displayExistingPassword();
//        updateClubDetailsTable.setItems(observableClubDetailsList);
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

        for (int hour = 0; hour < 24; hour++) {
            updateHourComboBox.getItems().add(String.format("%02d", hour));
        }
        updateHourComboBox.getSelectionModel().selectFirst();

        for(int minutes = 0; minutes < 60; minutes++){
            updateMinuteComboBox.getItems().add(String.format("%02d", minutes));
        }
        updateMinuteComboBox.getSelectionModel().selectFirst();

        for (int hour = 0; hour < 24; hour++) {
            scheduleEventHour.getItems().add(String.format("%02d", hour));
        }
        scheduleEventHour.getSelectionModel().selectFirst();


        for(int minutes = 0; minutes < 60; minutes++){
            scheduleEventMinutes.getItems().add(String.format("%02d", minutes));
        }
        scheduleEventMinutes.getSelectionModel().selectFirst();

        createEventClubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        createEventEventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        createEventEventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        createEventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));
        createEventTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        createEventDeliveryTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventDeliveryType"));
        createEventDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        createEventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("eventTime"));

        updateClubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        updateEventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        updateEventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        updateEventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));
        updateEventTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        updateDeliveryTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventDeliveryType"));
        updateEventDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        updateEventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("eventTime"));

        cancelEventClubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        cancelEventEventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        cancelEventEventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        cancelEventEventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));
        cancelEventEventTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        cancelEventDeliveryTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventDeliveryType"));
        cancelEventEventDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        cancelEventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("eventTime"));

        viewEventClubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        viewEventEventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        viewEventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        viewEventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));
        viewEventTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        viewEventDeliveryTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventDeliveryType"));
        viewEventDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        viewEventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("eventTime"));

        atColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceStatus"));
        stColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceTracker"));

    }
  
     public void setCreateTable(){
        // Check whether the sortedList is null and return the method, if it is null
        if(clubDetailsList == null){
            return;
        }
        // Clear the UpdateViewTable
        createClubDetailsTable.getItems().clear();

        // Add Item details to the UpdateView Table using Sorted List
        for(Club club : clubDetailsList) {

            // Create an Item details object with the item details
            Club tableClub = new Club(club.getClubId() , String.valueOf(club.getClubName()) , String.valueOf(club.getClubDescription()) , String.valueOf(club.getClubLogo()));

            // Add the item details to the UpdateViewTable
            ObservableList<Club> observableCreateClubList = createClubDetailsTable.getItems();
            observableCreateClubList.add(tableClub);
            createClubDetailsTable.setItems(observableCreateClubList);
        }
    }

    public void setUpdateTable(){
        // Check whether the sortedList is null and return the method, if it is null
        if(clubDetailsList == null){
            return;
        }
        // Clear the UpdateViewTable
        updateClubDetailsTable.getItems().clear();

        // Add Item details to the UpdateView Table using Sorted List
        for(Club club : clubDetailsList) {

            // Create an Item details object with the item details
            Club tableClub = new Club(club.getClubId() , String.valueOf(club.getClubName()) , String.valueOf(club.getClubDescription()) , String.valueOf(club.getClubLogo()));

            // Add the item details to the UpdateViewTable
            ObservableList<Club> observableUpdateClubList = updateClubDetailsTable.getItems();
            observableUpdateClubList.add(tableClub);
            updateClubDetailsTable.setItems(observableUpdateClubList);
        }
    }



    public void populateEventsTables(){
       if(Event.eventDetails == null){
           return;
       }

        scheduleCreatedEventTable.getItems().clear();
        updateEventTable.getItems().clear();
        cancelEventTable.getItems().clear();
        viewCreatedEventsTable.getItems().clear();

        scheduleCreatedEventTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        updateEventTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        cancelEventTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        viewCreatedEventsTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);


       for(Event value : Event.eventDetails){
           Club hostingClub = value.getHostingClub();
           Event event = new Event(value.getEventName(), value.getEventLocation(),
                   value.getEventType(),value.getEventDeliveryType(), value.getEventDate(),
                   value.getEventTime(), hostingClub, value.getEventDescription());

           ObservableList<Event> viewScheduledEvents = scheduleCreatedEventTable.getItems();
           viewScheduledEvents.add(event);
           scheduleCreatedEventTable.setItems(viewScheduledEvents );

           ObservableList<Event> updateScheduledEvents = updateEventTable.getItems();
           updateScheduledEvents.add(event);
           updateEventTable.setItems(updateScheduledEvents );

           ObservableList<Event> cancelScheduledEvents = cancelEventTable.getItems();
           cancelScheduledEvents.add(event);
           cancelEventTable.setItems(cancelScheduledEvents );

           ObservableList<Event> viewCreatedScheduledEvents = viewCreatedEventsTable.getItems();
           viewCreatedScheduledEvents.add(event);
           viewCreatedEventsTable.setItems(viewCreatedScheduledEvents);
           viewCreatedEventsSortComboBox.getSelectionModel().selectFirst(); // select the first item of the view Events
       }
    }

    @Override
    public void clubCreationChecker(ActionEvent event) {
//        Club club1 = new Club(0001, "Rotract", "Done with the work", "lkt.img");
//        clubDetailsList.add(club1);
        validStat = true;

        int clubId = Integer.parseInt(this.clubId.getText());
        String clubName = this.clubName.getText();
        String clubDescription = this.clubDescription.getText();
        String clubLogo = this.createClubImage.getImage().getUrl();

        System.out.println(clubId);

        Club club = new Club(clubId,clubName,clubDescription);

        if (!club.validateClubName()){
            System.out.println("Wrong Club Name");
            validStat = false;
        }
        displayClubNameError(clubNameError);

        if (!club.validateClubDescription()){
            System.out.println("Wrong Club Description");
            validStat = false;
        }
        displayClubDecriptionError(clubDescriptionError);

        System.out.println("Valid Stat :" + validStat );
        if (validStat) {
            Club clubData = new Club(clubId, clubName, clubDescription, imagePath);
            clubDetailsList.add(clubData);

            setCreateTable();
            setUpdateTable();

            Alert clubUpdateAlert = new Alert(Alert.AlertType.INFORMATION);
            clubUpdateAlert.initModality(Modality.APPLICATION_MODAL);
            clubUpdateAlert.setTitle("School Club Management System");
            clubUpdateAlert.setHeaderText(clubName + " Club created successfully!");
            clubUpdateAlert.show();

            clubIdSetterValue += 1;
            this.clubId.setText(String.valueOf(clubIdSetterValue));

            this.clubName.setText("");
            this.clubDescription.setText("");
//            Image defaultImage = new Image("C:/Users/Asus/Desktop/OOD CW/OOD-Coursework/ClubManagementSystem/src/main/resources/Images/360_F_93856984_YszdhleLIiJzQG9L9pSGDCIvNu5GEWCc.jpg");
//            this.createClubImage.setImage(defaultImage);

            //Update database
        }else {
            Alert clubUpdateAlert = new Alert(Alert.AlertType.WARNING);
            clubUpdateAlert.initModality(Modality.APPLICATION_MODAL);
            clubUpdateAlert.setTitle("School Club Management System");
            clubUpdateAlert.setHeaderText("Please fill the club details properly!");
            clubUpdateAlert.show();
        }
    }

    @Override
    void clubCreationReset(ActionEvent event) {
        clubName.setText("");
        clubDescription.setText("");
//        Image defaultImage = new Image("C:/Users/Asus/Desktop/OOD CW/OOD-Coursework/ClubManagementSystem/src/main/resources/Images/360_F_93856984_YszdhleLIiJzQG9L9pSGDCIvNu5GEWCc.jpg");
//        createClubImage.setImage(defaultImage);

        clubNameError.setText("");
        clubDescriptionError.setText("");
    }

    public void clubUpdateChecker(ActionEvent event) {
//        Club club1 = new Club(0001, "Rotract", "Done with the work", "lkt.img");
//        clubDetailsList.add(club1);
        validStat = true;

        int clubId = Integer.parseInt(updateClubID.getText());
        String clubName = updateClubName.getText();
        String clubDescription = updateClubDescription.getText();

        Club club = new Club(clubId,clubName,clubDescription);

        if (!club.validateClubName()){
            System.out.println("Wrong Club Name");
            validStat = false;
        }
        displayClubNameError(updateClubNameError);

        if (!club.validateClubDescription()){
            System.out.println("Wrong Club Description");
            validStat = false;
        }
        displayClubDecriptionError(updateClubDescriptionError);


        System.out.println("Valid state : " + validStat);
        if (validStat){
            for (Club foundClub : clubDetailsList){
                if (clubId == foundClub.getClubId()){
                    foundClub.setClubName(clubName);
                    foundClub.setClubDescription(clubDescription);
                    //Set club logo
                    String clubLogo = this.updateClubImage.getImage().getUrl();
                    foundClub.setClubLogo(clubLogo);

                    Alert clubUpdateAlert = new Alert(Alert.AlertType.INFORMATION);
                    clubUpdateAlert.initModality(Modality.APPLICATION_MODAL);
                    clubUpdateAlert.setTitle("School Club Management System");
                    clubUpdateAlert.setHeaderText("Club details successfully updated!!!");
                    clubUpdateAlert.show();

                    //Clear the update text-fields
                    this.updateClubID.setText(String.valueOf(""));
                    this.updateClubName.setText("");
                    this.updateClubDescription.setText("");

//                    Image defaultImage = new Image("C:/Users/Asus/Desktop/OOD CW/OOD-Coursework/ClubManagementSystem/src/main/resources/Images/360_F_93856984_YszdhleLIiJzQG9L9pSGDCIvNu5GEWCc.jpg");
//                    this.updateClubImage.setImage(defaultImage);

                    //Updating club details tables
                    setCreateTable();
                    setUpdateTable();

                    //Update database
                }
            }
        }else {
            Alert clubUpdateAlert = new Alert(Alert.AlertType.WARNING);
            clubUpdateAlert.initModality(Modality.APPLICATION_MODAL);
            clubUpdateAlert.setTitle("School Club Management System");
            clubUpdateAlert.setHeaderText("Please fill the club details properly!");
            clubUpdateAlert.show();
        }
    }
    @FXML
    void clubUpdationReset(ActionEvent event) {
        updateClubID.setText(String.valueOf(""));
        updateClubName.setText("");
        updateClubDescription.setText("");
//        Image defaultImage = new Image("C:/Users/Asus/Desktop/OOD CW/OOD-Coursework/ClubManagementSystem/src/main/resources/Images/360_F_93856984_YszdhleLIiJzQG9L9pSGDCIvNu5GEWCc.jpg");
//        updateClubImage.setImage(defaultImage);

        updateClubNameError.setText("");
        updateClubDescriptionError.setText("");
    }

    @FXML
    void searchUpdateTable(ActionEvent event) {
        //Get the club name to search from the search bar
        String clubName = updateClubSearch.getText();
        System.out.println(clubName);

        // Search for the club name and handle non-existent club name
        Club foundClub = null;
        for (Club club : updateClubDetailsTable.getItems()) {
            if (club.getClubName().equals(clubName)) {
                foundClub = club;
                break;
            }
        }

        if (foundClub != null) {
            // Select the row with the found club in the updateClubDetailsTable
            updateClubDetailsTable.getSelectionModel().select(foundClub);
            updateClubDetailsTable.scrollTo(foundClub);
            updateClubTableSelect();

            // Update the input fields with the selected item's details for updating

        } else {
            // Show alert for non-existent item code
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Club Not Found");
            alert.setHeaderText(null);
            alert.setContentText("The Club with name " + clubName + " does not exist.");
            alert.showAndWait();
        }
    }

    public void displayClubNameError(Label labelID){
        if (Club.clubNameValidateStatus.equals("empty")){
            labelID.setText("Club Name cannot be empty");
        } else if (Club.clubNameValidateStatus.equals("format")) {
            labelID.setText("Club Name can contain only letters");
        }else if (Club.clubNameValidateStatus.equals("exist")){
            labelID.setText("That club name already exists !!!");
        }else {
            labelID.setText("");
        }
    }
    public void displayClubDecriptionError(Label labelID){
        if (Club.clubDescriptionValidateStatus.equals("empty")){
            labelID.setText("Club Description cannot be empty");
        }else{
            labelID.setText("");
        }
    }

    @FXML
    public void updateClubTableSelect(MouseEvent event) {
        updateClubTableSelect();

        updateClubNameError.setText("");
        updateClubDescriptionError.setText("");
    }

    public void updateClubTableSelect(){
        int row = updateClubDetailsTable.getSelectionModel().getSelectedIndex();
        System.out.println(row);

        String clubID = String.valueOf(clubDetailsList.get(row).getClubId());
        updateClubID.setText(clubID);
        updateClubName.setText(clubDetailsList.get(row).getClubName());
        updateClubDescription.setText(clubDetailsList.get(row).getClubDescription());
        updateClubImage.setImage(clubDetailsList.get(row).getAbsoluteImage().getImage());
    }


    public void OpenImageHandler(ActionEvent event){
        fileChooser.setTitle("File Chooser"); //Set the title of the file chooser dialog

        //Set the initial directory of the fileChooser to the user's home directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        //
        fileChooser.getExtensionFilters().clear();
        //
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
        //
        File file = fileChooser.showOpenDialog(null);

        //Check whether if a file is selected by the user
        if(file != null){
            //get the button that handles the event
            Button clickedButton = (Button) event.getSource();

            //Take the fxID of the button
            String fxID = clickedButton.getId();
            //Get the selected image path
            imagePath = file.getPath();

            //Check whether the image imported is from the update or from the adding pane
            if (fxID.equals("createClubImageButton")){
                //Set the input image view as the selected image
                createClubImage.setImage(new Image(String.valueOf(file.toURI())));
            }else{
                //Set the update image view as the selected image
                updateClubImage.setImage(new Image(String.valueOf(file.toURI())));
            }
        }else {
            //Show the import image error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Image not imported !!!");
            alert.show(); //Display the error
        }
    }

    public void updateOpenImageHandler(ActionEvent event){
        fileChooser.setTitle("File Chooser"); //Set the title of the file chooser dialog

        //Set the initial directory of the fileChooser to the user's home directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        //
        fileChooser.getExtensionFilters().clear();
        //
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
        //
        File file = fileChooser.showOpenDialog(null);

        //Check whether if a file is selected by the user
        if(file != null){
            //get the button that handles the event
            Button clickedButton = (Button) event.getSource();

            //Take the fxID of the button
            String fxID = clickedButton.getId();
            //Get the selected image path
            imagePath = file.getPath();

            //Check whether the image imported is from the update or from the adding pane
            if (fxID.equals("updateClubImageButton")){
                //Set the input image view as the selected image
                updateClubImage.setImage(new Image(String.valueOf(file.toURI())));
            }else{
                //Set the update image view as the selected image
                updateClubImage.setImage(new Image(String.valueOf(file.toURI())));
            }
        }else {
            //Show the import image error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Import Image Error !!!");
            alert.show(); //Display the error
        }
    }




    @Override
    public void clearScheduleEventFields(ActionEvent event){
        clearEventScheduleFieldsDefault();
    }

    public void clearEventScheduleFieldsDefault(){
        scheduleEventNameTextField.setText("");
        scheduleEventsLocationTextField.setText("");
        scheduleEventDescriptionTextField.setText("");
        scheduleEventDatePicker.setValue(null);
        scheduleEventTypeCombo.getSelectionModel().selectFirst();
        ScheduleEventsDeliveryType.getSelectionModel().selectFirst();
        scheduleEventMinutes.getSelectionModel().selectFirst();
        scheduleEventHour.getSelectionModel().selectFirst();
        scheduleEventsClubName.getSelectionModel().selectFirst();
        clearAllScheduleEventLabels();
    }

    public void clearUpdateEventFields(){
        updateEventClubCombo.getSelectionModel().selectFirst();
        updateEventTypeCombo.getSelectionModel().selectFirst();
        updateEventDeliveryTypeCombo.getSelectionModel().selectFirst();
        updateEventLocationTextField.setText("");
        updateEventNameTextField.setText("");
        updateEventDescription.setText("");
        updateEventDateDatePicker.setValue(null);
        updateHourComboBox.getSelectionModel().selectFirst();
        updateMinuteComboBox.getSelectionModel().selectFirst();
        updateEventClubCombo.getSelectionModel().selectFirst();
        updateEventClubCombo.getSelectionModel().selectFirst();
        clearAllUpdateEventLabels();
    }

   @Override
    protected void clearUpdateEventFields(ActionEvent event){
       clearUpdateEventFields();
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
            if(!eventManager.validateEventDate(selectedDate)){
                scheduleErrorLabelEventDate.setText("Event date cannot be a past date");
            }else{
                scheduleErrorLabelEventDate.setText(" ");
            }
        }else{
            selectedDate = updateEventDateDatePicker.getValue();

            System.out.println(event.getTarget());
            if(!eventManager.validateEventDate(selectedDate)){
                updateErrorLabelEventDate.setText("Event date cannot be a past date");
            }else{
                updateErrorLabelEventDate.setText(" ");
            }

        }

    }




    @FXML
    void checkClubName(ActionEvent event) {
        String targetClub = "ComboBox[id=scheduleEventsClubName, styleClass=combo-box-base combo-box eventField]";
        String selectedClub;
        EventManager eventManager = new EventManager();
        System.out.println(event.getTarget());

        if(String.valueOf(event.getTarget()).equals(targetClub)){
            selectedClub= scheduleEventsClubName.getSelectionModel().getSelectedItem();
            if(eventManager.validateEventType(selectedClub)){
                scheduleErrorLabelClubName.setText("Club Name cannot be None");
            }else{
                scheduleErrorLabelClubName.setText(" ");
            }
        }else{
            System.out.println("Hello World !!!");
            selectedClub = updateEventClubCombo.getSelectionModel().getSelectedItem();
            if(eventManager.validateEventType(selectedClub)){
                 updateErrorLabelClubName.setText("Club Name cannot be None");
            }else{
                 updateErrorLabelClubName.setText(" ");
            }
        }
        System.out.println(event.getTarget());
    }


    public void getCreatedClubs(){

        if(!scheduleEventsClubName.getItems().contains("None")){
            scheduleEventsClubName.getItems().add("None");
        }

        if(!updateEventClubCombo.getItems().contains("None")){
            updateEventClubCombo.getItems().add("None");
        }

        if(!viewCreatedEventsSortComboBox.getItems().contains("All Clubs")){
            viewCreatedEventsSortComboBox.getItems().add("All Clubs");
        }

        for(Club club: Club.clubDetailsList){
            String clubName;
            clubName = club.getClubName();
            boolean scheduleContainStatus =  scheduleEventsClubName.getItems().contains(clubName);
            boolean updateContainsStatus =   updateEventClubCombo.getItems().contains(clubName);
            boolean viewContainsStatus = viewCreatedEventsSortComboBox.getItems().contains(clubName);

            if(!scheduleContainStatus){
                scheduleEventsClubName.getItems().add(clubName);
            }

            if(!updateContainsStatus){
                updateEventClubCombo.getItems().add(clubName);
            }

            if(!viewContainsStatus){
                viewCreatedEventsSortComboBox.getItems().add(clubName);
            }

        }

        scheduleEventsClubName.getSelectionModel().selectFirst();
        scheduleErrorLabelClubName.setText(" ");

        updateEventClubCombo.getSelectionModel().selectFirst();
        updateErrorLabelClubName.setText(" ");

        viewCreatedEventsSortComboBox.getSelectionModel().selectFirst();
    }



    @FXML
    void scheduleEventController(ActionEvent event) {
        String eventName = scheduleEventNameTextField.getText();
        String eventLocation = scheduleEventsLocationTextField.getText();
        LocalDate eventDate = scheduleEventDatePicker.getValue();
        String deliveryType = ScheduleEventsDeliveryType.getValue();
        String eventType = scheduleEventTypeCombo.getValue();
        String clubName = scheduleEventsClubName.getValue();
        String eventStartHour = scheduleEventHour.getValue();
        String eventStartMinute = scheduleEventMinutes.getValue();
        String eventDescription = scheduleEventDescriptionTextField.getText();

        EventManager eventManager = new EventManager();
        boolean stat = eventManager.validateAllEventDetails(eventName, eventLocation, eventType, deliveryType,
                eventDate, clubName, eventStartHour, eventStartMinute, "create", eventDescription);
        if(stat){
            clearEventScheduleFieldsDefault();
            populateEventsTables();
            displayNumberOfScheduledEvents();
            getNextEventDate();
        }else{
            Alert eventCreateAlert = new Alert(Alert.AlertType.WARNING);
            eventCreateAlert.initModality(Modality.APPLICATION_MODAL);
            eventCreateAlert.setTitle("School Club Management System");
            eventCreateAlert.setHeaderText("Please enter values properly to create an event!!!");
            eventCreateAlert.show();
        }
        DisplayEventErrors();
        System.out.println("\n\n");

    }


    public void DisplayEventErrors(){
        if(!EventManager.eventDateStatus){
            scheduleErrorLabelEventDate.setText("It is compulsory to set a future date");
            updateErrorLabelEventDate.setText("It is compulsory to set a future date");
        }else{
            scheduleErrorLabelEventDate.setText(" ");
            updateErrorLabelEventDate.setText(" ");
        }

        if(!EventManager.eventTypeStatus){
            scheduleErrorLabelEventType.setText("Event type cannot be None");
            updateErrorLabelEventType.setText("Event type cannot be None");
        }else{
            scheduleErrorLabelEventType.setText(" ");
            updateErrorLabelEventType.setText(" ");
        }

        if(!EventManager.eventDeliveryTypeStatus){
            scheduleErrorLabelEventDeliveryType.setText("Event delivery type cannot be None");
            updateErrorLabelDeliveryType.setText("Event delivery type cannot be None");
        }else{
            scheduleErrorLabelEventDeliveryType.setText("");
            updateErrorLabelDeliveryType.setText(" ");
        }

        if(!EventManager.eventLocationStatus){
            scheduleErrorLabelEventLocation.setText("Event Location cannot be empty");
            updateErrorLabelEventLocation.setText("Event Location cannot be empty");
        }else{
            scheduleErrorLabelEventLocation.setText(" ");
            updateErrorLabelEventLocation.setText(" ");
        }

        if(!EventManager.eventNameStatus){
            scheduleErrorLabelEventName.setText("Event name cannot be empty");
            updateErrorLabelEventName.setText("Event name cannot be empty");
        }else{
            scheduleErrorLabelEventName.setText(" ");
            updateErrorLabelEventName.setText(" ");
        }

        if(!EventManager.eventClubNameStatus){
            scheduleErrorLabelClubName.setText("Club Name cannot be None");
            updateErrorLabelClubName.setText("Club Name cannot be None");
        }else{
            scheduleErrorLabelClubName.setText("");
            updateErrorLabelClubName.setText(" ");
        }
    }



    public void clearAllScheduleEventLabels(){
        scheduleErrorLabelEventName.setText("");
        scheduleErrorLabelEventLocation.setText(" ");
        scheduleErrorLabelEventDate.setText(" ");
        scheduleErrorLabelEventDeliveryType.setText(" ");
        scheduleErrorLabelEventType.setText(" ");
        scheduleErrorLabelClubName.setText(" ");
    }

    public void clearAllUpdateEventLabels(){
        updateErrorLabelEventDate.setText(" ");
        updateErrorLabelDeliveryType.setText(" ");
        updateErrorLabelEventType.setText(" ");
        updateErrorLabelEventLocation.setText(" ");
        updateErrorLabelEventName.setText(" ");
        updateErrorLabelClubName.setText(" ");
    }

    @FXML
    public void updateRowSelection(MouseEvent event) {
        updateRowSelection();
    }

    public void updateRowSelection(){
        try{
            if(!(updateEventTable.getSelectionModel().getSelectedItem() == null)){
                enableAllUpdateEventFields();
            }
            updateEventFieldButton.setDisable(false);
            clearEventFieldButton.setDisable(false);

            selectedEventValue =  updateEventTable.getSelectionModel().getSelectedItem();
            selectedEventId = updateEventTable.getSelectionModel().getSelectedIndex();

            updateEventClubCombo.setValue(String.valueOf(selectedEventValue.getClubName()));
            updateEventTypeCombo.setValue(String.valueOf(selectedEventValue.getEventType()));
            updateEventDeliveryTypeCombo.setValue(String.valueOf(selectedEventValue.getEventDeliveryType()));
            updateEventLocationTextField.setText(String.valueOf(selectedEventValue.getEventLocation()));
            updateEventNameTextField.setText(String.valueOf(selectedEventValue.getEventName()));
            updateEventDescription.setText(String.valueOf(selectedEventValue.getEventDescription()));
            updateEventDateDatePicker.setValue(selectedEventValue.getEventDate());

            LocalTime startTime = selectedEventValue.getEventTime();
            int hour = startTime.getHour();

            if(hour < 10){
                String hourVal = "0" + hour;
                updateHourComboBox.setValue(hourVal);
            }else{
                updateHourComboBox.setValue(String.valueOf(hour));
            }


            int minute = startTime.getMinute();
            if(minute < 10){
                String minuteVal = "0" + minute;
                updateMinuteComboBox.setValue(minuteVal);
            }else{
                updateMinuteComboBox.setValue(String.valueOf(minute));
            }


            System.out.println(selectedEventValue.getClubName());

        }catch(NullPointerException E){
            System.out.println("No values");
        }

    }


    public void disableAllUpdateEventFields(){
        updateEventClubCombo.setDisable(true);
        updateEventTypeCombo.setDisable(true);
        updateEventDeliveryTypeCombo.setDisable(true);
        updateEventLocationTextField.setDisable(true);
        updateEventNameTextField.setDisable(true);
        updateEventDescription.setDisable(true);
        updateEventDateDatePicker.setDisable(true);
        updateHourComboBox.setDisable(true);
        updateMinuteComboBox.setDisable(true);
        updateEventClubCombo.setDisable(true);
        updateEventClubCombo.setDisable(true);
    }

    public void enableAllUpdateEventFields(){
        updateEventClubCombo.setDisable(false);
        updateEventTypeCombo.setDisable(false);
        updateEventDeliveryTypeCombo.setDisable(false);
        updateEventLocationTextField.setDisable(false);
        updateEventNameTextField.setDisable(false);
        updateEventDescription.setDisable(false);
        updateEventDateDatePicker.setDisable(false);
        updateHourComboBox.setDisable(false);
        updateMinuteComboBox.setDisable(false);
        updateEventClubCombo.setDisable(false);
    }

    @FXML
    void updateEventsController(ActionEvent event) {
        String eventName = updateEventNameTextField.getText();
        String eventLocation = updateEventLocationTextField.getText();
        LocalDate eventDate = updateEventDateDatePicker.getValue();
        String deliveryType = updateEventDeliveryTypeCombo.getValue();
        String eventType = updateEventTypeCombo.getValue();
        String clubName = updateEventClubCombo.getValue();
        String eventStartHour = updateHourComboBox.getValue();
        String eventStartMinute = updateMinuteComboBox.getValue();
        String eventDescription = updateEventDescription.getText();

        EventManager eventManager = new EventManager();

        boolean stat = eventManager.validateAllEventDetails(eventName, eventLocation, eventType, deliveryType,
                eventDate, clubName, eventStartHour, eventStartMinute, "update", eventDescription);

        if(stat){
            selectedEventValue.setEventName(eventName);
            selectedEventValue.setEventLocation(eventLocation);
            selectedEventValue.setEventDate(eventDate);
            selectedEventValue.setEventDeliveryType(deliveryType);
            selectedEventValue.setEventType(eventType);
            selectedEventValue.setHostingClub(EventManager.userSelectedClubChooser(clubName));
            selectedEventValue.setEventTime(eventManager.makeDateTime(eventStartHour, eventStartMinute));
            selectedEventValue.setEventDescription(eventDescription);

            LocalTime eventStaringTime = eventManager.makeDateTime(eventStartHour, eventStartMinute);
            selectedEventValue.setEventTime(eventStaringTime);

            ClubAdvisor clubAdvisor = new ClubAdvisor();
            clubAdvisor.updateEventDetails(selectedEventValue, selectedEventId);
            populateEventsTables();
            getNextEventDate();
            disableAllUpdateEventFields();
            clearUpdateEventFields();
        }else{
            Alert eventUpdateAlert = new Alert(Alert.AlertType.WARNING);
            eventUpdateAlert.initModality(Modality.APPLICATION_MODAL);
            eventUpdateAlert.setTitle("School Club Management System");
            eventUpdateAlert.setHeaderText("Please enter values properly to update an event!!!");
            eventUpdateAlert.show();
        }

        DisplayEventErrors();
        System.out.println(stat);
    }

    @FXML
    void cancelEventController(ActionEvent event) {
       try{
           Event selectedEvent = cancelEventTable.getSelectionModel().getSelectedItem();
           selectedEventId = cancelEventTable.getSelectionModel().getSelectedIndex();
           System.out.println(selectedEvent.getEventName());

           Alert cancelEvent = new Alert(Alert.AlertType.CONFIRMATION);
           cancelEvent.initModality(Modality.APPLICATION_MODAL);
           cancelEvent.setTitle("School Activity Club Management System");
           cancelEvent.setHeaderText("Do you really want to delete the event ?");

           Optional<ButtonType> result = cancelEvent.showAndWait();
           if(result.get() != ButtonType.OK){
               return;
           }

           ClubAdvisor clubAdvisor = new ClubAdvisor();
           clubAdvisor.cancelEvent(selectedEvent, selectedEventId);
           populateEventsTables();
           displayNumberOfScheduledEvents();
           getNextEventDate();

       }catch(NullPointerException error){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("School Club Management System");
           alert.setHeaderText("Select an event from table to cancel the event");
           alert.show();
       }

    }

    @FXML
    void searchCancelEvent(ActionEvent event) {
        searchEvents(cancelEventTable, cancelEventSearchBar);
    }

    @FXML
    void searchUpdateEventDetails(ActionEvent event) {
        searchEvents(updateEventTable, updateEventSearchBar);
    }

    @FXML
    void searchScheduledEventsInCreate(ActionEvent event) {
        searchEvents(scheduleCreatedEventTable, createdEventSearchBar);
    }

    public void searchEvents(TableView<Event> tableView, TextField searchBar){
        String eventName = searchBar.getText();
        System.out.println(eventName);

        Event foundEvent = null;
        for(Event eventVal : tableView.getItems()){
            if(eventVal.getEventName().equals(eventName)){
                foundEvent = eventVal;
                break;
            }
        }

        if(foundEvent != null){
            System.out.println(foundEvent.getEventName() + "Hello");
            tableView.getSelectionModel().select(foundEvent);
            selectedEventId = tableView.getSelectionModel().getSelectedIndex();
            tableView.scrollTo(foundEvent);

            if(tableView == updateEventTable){
                updateRowSelection();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("School Club Management System");
            alert.setHeaderText("The event " + eventName + " does not found");
            alert.showAndWait();
        }
    }

    public void displayNumberOfScheduledEvents(){
        numberOfScheduledEvents.setText(String.valueOf(Event.eventDetails.size()));
    }

    public  void getNextEventDate() {
        if (Event.eventDetails.isEmpty()) {
            nextEventDate.setText("   No events");
            return;
        }

        LocalDate currentDate = LocalDate.now();

        LocalDate nextDate = null;

        for (Event event : Event.eventDetails) {
            LocalDate eventDate = event.getEventDate();
            if ((eventDate.isAfter(currentDate) || eventDate.isEqual(currentDate)) &&
                    (nextDate == null || eventDate.isBefore(nextDate))) {
                nextDate = eventDate;
            }
        }

        if (nextDate != null) {
            nextEventDate.setText("   " + nextDate);
        }
    }

    public void displayNumberOfClubAdvisors(){
        numberOfClubs.setText(String.valueOf(Club.clubDetailsList.size()));
    }

    @FXML
    void filterSelectedClubEvents(ActionEvent event) {

          viewCreatedEventsTable.getItems().clear();
          ArrayList<Event> filteredEvents = new ArrayList<>();
          String selectedClub = viewCreatedEventsSortComboBox.getSelectionModel().getSelectedItem();
          System.out.println(selectedClub + " bro");

          if(selectedClub.equals("All Clubs")){
              populateEventsTables();
              return;
          }else{
              for(Event events : Event.eventDetails){
                  if(events.getClubName().equals(selectedClub)){
                      filteredEvents.add(events);
                  }
              }
          }

         ClubAdvisor clubAdvisor = new ClubAdvisor();
         clubAdvisor.viewEvent(); // Override scene eka


          for(Event value : filteredEvents){
              Club hostingClubDetail = value.getHostingClub();
              Event requiredEvent = new Event(value.getEventName(), value.getEventLocation(),
                      value.getEventType(),value.getEventDeliveryType(), value.getEventDate(),
                      value.getEventTime(), hostingClubDetail, value.getEventDescription());

              ObservableList<Event> viewScheduledEvents = viewCreatedEventsTable.getItems();
              viewScheduledEvents.add(requiredEvent);
              viewCreatedEventsTable.setItems(viewScheduledEvents );
          }

    }






    public void populateAttendanceTable() {
        // Assuming Attendance.atdTracker is a list of Attendance objects
        ObservableList<Attendance> viewScheduledEvents = FXCollections.observableArrayList();

        for (Attendance atd : Attendance.atdTracker) {
            // Assuming you have a copy constructor in the Attendance class
            Attendance atd2 = new Attendance(atd.isAttendanceStatus(), atd.getAttendanceTracker());
            viewScheduledEvents.add(atd2);

            // Add a ChangeListener to the CheckBox
            atd2.getAttendanceTracker().selectedProperty().addListener((obs, oldVal, newVal) -> {
                // Update the attendanceStatus property in the Attendance class
                atd2.setAttendanceStatus(newVal);

                // Print a message or perform any other actions as needed
                System.out.println("Attendance status for student "  + " updated to: " + newVal);
            });
        }

        // Set the items of the table view
        tb1.setItems(viewScheduledEvents);

        // Set column widths
        TableColumn<Attendance, Boolean> attendanceColumn = new TableColumn<>("Attendance");
        // the column is initialized respectively
        attendanceColumn.setCellValueFactory(data -> data.getValue().attendanceStatusProperty());

        attendanceColumn.setPrefWidth(100); // Adjust the value as needed

        // Set custom row factory to control row height
        tb1.setRowFactory(tv -> {
            // a new row is created
            TableRow<Attendance> row = new TableRow<>();
            row.setPrefHeight(30); // Adjust the value as needed
            return row;
        });

        // Add columns to the table view
        tb1.getColumns().addAll(attendanceColumn);
    }

    public void findMaleFemaleStudentCount(){
        int maleRate = 0;
        int femaleRate = 0;
        for(Student student : Student.studentDetailArray){
            if(student.getGender() == 'M'){
                maleRate ++;
            }else{
                femaleRate++;
            }
        }

        XYChart.Series setOfData = new XYChart.Series();
        setOfData.getData().add(new XYChart.Data<>("Male", maleRate));
        setOfData.getData().add(new XYChart.Data<>("Female", femaleRate));
        GenderRatioChart.getData().addAll(setOfData);

    }


    public void displayEnrolledStudentCount(){
        HashMap<Integer, Integer> studentGrade = new HashMap<>();
        for(Student student : Student.studentDetailArray){
            int grade = student.getStudentGrade();
            studentGrade.put(grade, studentGrade.getOrDefault(grade, 0) + 1);
        }

        XYChart.Series setOfData = new XYChart.Series();
        for (Map.Entry<Integer, Integer> entry : studentGrade.entrySet()) {
            setOfData.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }

        EnrollStudentCountEachGrade.getData().addAll(setOfData);

    }

    public void populateAttendanceClubNameComboBox() {
        // the club name combo box is cleared
        attendanceClubNameComboBox.getItems().clear();

        if(!attendanceClubNameComboBox.getItems().contains("Please Select")){
            // the default option of please select is added to the combo box
            attendanceClubNameComboBox.getItems().add("Please select");
        }

        // the following is done for every club in the clubDetailsList
        for(Club club : clubDetailsList){
            // the club names are added to the combo box from the array list
            attendanceClubNameComboBox.getItems().add(club.getClubName());
        }
        // retrieves the current selection in the combo box
        attendanceClubNameComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    void populateEventList(ActionEvent event) {
        // a new array list to hold the events filtered to the respective club
        ArrayList<Event> filteredEvents = new ArrayList<>();
        // the selected club name is retrieved from the club name combo box
        String selectedClub = attendanceClubNameComboBox.getSelectionModel().getSelectedItem();
        System.out.println(selectedClub + "ding dong bell");

        // if no club is selected, the method is returned (not executed)
        if(selectedClub == null){
            return;
        }

        // if "Please Select" is chosen, the method is returned (not executed)
        if(selectedClub.equals("Please Select")){
            return;
        }else{
            // a object of data type Event is created to iterate over the eventDetails array list
            for(Event events : Event.eventDetails){
                if(events.getClubName().equals(selectedClub)){
                    filteredEvents.add(events); // the events for the respective club is added to the areay list
                }
            }
        }
        // the event name combo box is cleared
        attendanceEventNameComboBox.getItems().clear();

        // check if the option "Please select" is already available
        if(!attendanceEventNameComboBox.getItems().contains("Please Select")){
            // option is added if not available
            attendanceEventNameComboBox.getItems().add("Please select");
        }

        // an object event1 of data type Event is created to iterate over the filteredEvents array list
        for(Event event1 : filteredEvents){
            // the events are retrieved and added
            attendanceEventNameComboBox.getItems().add(event1.getEventName());
        }
        // retrieves the current selection in the combo box
        attendanceEventNameComboBox.getSelectionModel().selectFirst();
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
        displayNumberOfClubAdvisors();
    }

    @Override
    void GoToManageClubPane(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        ManageClubPane.setVisible(true);
        ManageclubButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        clubId.setText(String.valueOf(clubIdSetterValue));
        setCreateTable();
        setUpdateTable();
    }

    @Override
    void GoToScheduleEvents(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        ScheduleEventsPane.setVisible(true);
        ScheduleEventsButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        getCreatedClubs();
        clearAllUpdateEventLabels();
        clearAllScheduleEventLabels();
    }

    @Override
    void GoToTrackAttendance(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        AttendancePane.setVisible(true);
        AttendanceButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        populateAttendanceClubNameComboBox();
    }

    @Override
    void GoToGenerateReports(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        GenerateReportsPane.setVisible(true);
        GenerateReportsButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        populateAttendanceTable();
    }

    @Override
    void GoToClubAdvisorProfile(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        ProfilePane.setVisible(true);
        AdvisorProfileButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        displayStudentUpdateDetails();
        displayExistingPassword();
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
        RegistrationReportPane.setVisible(false);
        GoToClubMembershipButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
                "-fx-text-fill: black;");
        GoToEventAttendanceButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
                "-fx-text-fill: black;");
        GoToClubActivitiesButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
                "-fx-text-fill: black;");
        GoToRegistrationButton.setStyle("-fx-background-color: linear-gradient(to right, #165a6d, #6aa9bc, #6aa9bc, #165a6d);" +
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
        getCreatedClubs();
        clearAllUpdateEventLabels();
        clearUpdateEventFields();
        disableAllUpdateEventFields();
        updateEventFieldButton.setDisable(true);
        clearEventFieldButton.setDisable(true);
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
        getCreatedClubs();
        clearAllScheduleEventLabels();
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

        clubNameError.setText("");
        clubDescriptionError.setText("");
    }

    @Override
    void GoToUpdateClubDetailsPane(ActionEvent event) {
        makeAllClubCreationPanesInvisible();
        UpdateClubDetailPane.setVisible(true);
        UpdateClubDirectorButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");

        updateClubNameError.setText("");
        updateClubDescriptionError.setText("");
    }

    @FXML
    void GoToRegistration(ActionEvent event) {
        makeAllPanesInvisibleGeneratingReport();
        RegistrationReportPane.setVisible(true); // wrong
        GoToRegistrationButton.setStyle("-fx-text-fill: white; " +
                "-fx-background-color: linear-gradient(to right, #2b6779, #003543, #003543, #2b6779);");
    }


    static {
        clubIdSetterValue = 100;
    }


    @FXML
    void advisorProfileUpdateChecker(ActionEvent event) {
        validStat = true;
        int advisorId = Integer.parseInt(profileAdvisorId.getText());
        String advisorFirstName = profileAdvisorFname.getText();
        String advisorLastName = profileAdvisorLname.getText();
        String advisorUsername = profileAdvisorUsername.getText();
        String advisorContactNumber = profileAdvisorCnumber.getText();
        String advisorPassword = profileAdvisorpw.getText();

        ClubAdvisor clubAdvisor = new ClubAdvisor(advisorUsername, advisorPassword, advisorFirstName, advisorLastName, advisorContactNumber, advisorId);

        ClubAdvisor.fNameValidateStatus = "correct";
        ClubAdvisor.lNameValidateStatus = "correct";
        ClubAdvisor.contactNumberValidateStatus = "correct";
        ClubAdvisor.passwordValidateStatus = "correct";
        ClubAdvisor.userNameValidateStatus = "correct";

        if (!clubAdvisor.validateFirstName()) {
            System.out.println("Incorrect First Name.");
            System.out.println(ClubAdvisor.fNameValidateStatus + " : First Name");
            validStat = false;
        }
        displayNameError("firstName");

        if (!clubAdvisor.validateLastName()) {
            System.out.println("Incorrect Last Name.");
            System.out.println(Student.lNameValidateStatus);
            validStat = false;
        }
        displayNameError("lastName");

        try{
            String tempContactNum = advisorContactNumber;
            if (tempContactNum.isEmpty()) {
                User.contactNumberValidateStatus = "empty";
                throw new Exception();
            }
            Double.parseDouble(advisorContactNumber.trim());
            ClubAdvisor clubAdvisor1 = new ClubAdvisor(tempContactNum);

            if (!clubAdvisor1.validateContactNumber()) {
                validStat = false;
                System.out.println("Invalid Contact Number 1");
            } else {
                User.contactNumberValidateStatus = "";
            }
        } catch(NumberFormatException e) {
            System.out.println("Invalid ContactNumber 2");
            User.contactNumberValidateStatus = "format";
            validStat = false;
        } catch (Exception e) {
            validStat = false;
        }
        displayContactNumError();

        if (!clubAdvisor.validateUserName("updation", "advisor")) {
            System.out.println("Wrong user name.");
            validStat = false;
        } else {
            User.userNameValidateStatus = "";
        }
        displayUserNameError();

        System.out.println("Valid state : " + validStat);
        if (validStat){
            for (ClubAdvisor foundClubAdvisor : clubAdvisorDetailsList){
                if (advisorId == foundClubAdvisor.getClubAdvisorId()){
                    foundClubAdvisor.setClubAdvisorId(advisorId);
                    foundClubAdvisor.setFirstName(advisorFirstName);
                    foundClubAdvisor.setLastName(advisorLastName);
                    foundClubAdvisor.setUserName(advisorUsername);
                    foundClubAdvisor.setContactNumber(advisorContactNumber);

                    Alert clubUpdateAlert = new Alert(Alert.AlertType.INFORMATION);
                    clubUpdateAlert.initModality(Modality.APPLICATION_MODAL);
                    clubUpdateAlert.setTitle("School Club Management System");
                    clubUpdateAlert.setHeaderText("Profile details successfully updated!!!");
                    clubUpdateAlert.show();

                    //Update database
                }
            }
        }
    }

    @FXML
    void advisorProfilePasswordChecker(ActionEvent event) throws SQLException {
        validStat = true;
        int advisorId = Integer.parseInt(profileAdvisorId.getText());
        String advisorFirstName = profileAdvisorFname.getText();
        String advisorLastName = profileAdvisorLname.getText();
        String advisorUsername = profileAdvisorUsername.getText();
        String advisorContactNumber = profileAdvisorCnumber.getText();
//        String advisorpw = profileAdvisorpw.getText();
        String advisorNewPassword = profileAdvisorNewpwError.getText();
        String advisorConfirmPassword = profileAdvisorConfirmpwError.getText();

        ClubAdvisor clubAdvisor = new ClubAdvisor(advisorUsername, advisorNewPassword, advisorFirstName, advisorLastName,
                advisorContactNumber, advisorId);

        if (!clubAdvisor.validatePassword("update")) {
            System.out.println("Wrong password.");
            validStat = false;
        } else {
            User.passwordValidateStatus = "";
        }
        displayPasswordError();

        if (advisorConfirmPassword.isEmpty()) {
            profileAdvisorConfirmpwError.setText("Cannot be empty.");
            validStat = false;
        } else if (!advisorConfirmPassword.equals(advisorNewPassword)) {
            profileAdvisorConfirmpwError.setText("Passwords do not match");
            validStat = false;
        } else {
            profileAdvisorConfirmpwError.setText("");
        }

        System.out.println("Valid state : " + validStat);
        if (validStat){
            for (ClubAdvisor foundClubAdvisor : clubAdvisorDetailsList){
                if (advisorId == foundClubAdvisor.getClubAdvisorId()){
                    foundClubAdvisor.setPassword(advisorNewPassword);

                    Alert clubUpdateAlert = new Alert(Alert.AlertType.INFORMATION);
                    clubUpdateAlert.initModality(Modality.APPLICATION_MODAL);
                    clubUpdateAlert.setTitle("School Club Management System");
                    clubUpdateAlert.setHeaderText("Profile password successfully changed!!!");
                    clubUpdateAlert.show();

                    profileAdvisorpw.setText(advisorNewPassword);
                    profileAdvisorNewpw.setText("");
                    profileAdvisorConfirmpw.setText("");

                    //Update database
                }
            }
        }
    }

    public void displayUserNameError() {
        if (User.userNameValidateStatus.equals("empty")) {
            profileAdvisorUsernameError.setText("User name cannot be empty.");
        } else if (ClubAdvisor.userNameValidateStatus.equals("exists")) {
            profileAdvisorUsernameError.setText("Entered username already exists.");
        } else if (User.userNameValidateStatus.equals("blank")) {
            profileAdvisorUsernameError.setText("Username cannot contain spaces.");
        } else if (User.userNameValidateStatus.equals("length")) {
            profileAdvisorUsernameError.setText("The length should be 5 to 10 characters.");
        } else {
            profileAdvisorUsernameError.setText("");
        }
    }
    public void displayContactNumError() {
        if (User.contactNumberValidateStatus.equals("empty")) {
            profileAdvisorCnumberError.setText("Contact number cannot be empty.");
        } else if (User.contactNumberValidateStatus.equals("length")){
            profileAdvisorCnumberError.setText("Contact number should be 10 digits.");
        } else if (User.contactNumberValidateStatus.equals("format")) {
            profileAdvisorCnumberError.setText("It should contain only numbers.");
        } else {
            profileAdvisorCnumberError.setText("");
        }
    }
    public void displayNameError(String nameType) {
        if (nameType.equals("firstName")) {
            if (ClubAdvisor.fNameValidateStatus.equals("empty")) {
                profileAdvisorFnameError.setText("First Name cannot be empty.");
            } else if (ClubAdvisor.fNameValidateStatus.equals("format")) {
                profileAdvisorFnameError.setText("First Name can contain only letters.");
            } else {
                profileAdvisorFnameError.setText("");
            }
        } else if (nameType.equals("lastName")) {
            if (ClubAdvisor.lNameValidateStatus.equals("empty")) {
                profileAdvisorLnameError.setText("Last Name cannot be empty.");
            } else if (ClubAdvisor.lNameValidateStatus.equals("format")) {
                profileAdvisorLnameError.setText("Last name can contain only letters.");
            } else {
                profileAdvisorLnameError.setText("");
            }
        }
    }
  
    public void displayPasswordError() {
        if (User.passwordValidateStatus.equals("empty")) {
            profileAdvisorNewpwError.setText("Password cannot be empty.");
        } else if (User.passwordValidateStatus.equals("format")) {
            profileAdvisorNewpwError.setText("Password should consists of 8 characters including numbers and special characters.");
        } else {
            profileAdvisorNewpwError.setText("");
        }
    }


    public void displayStudentUpdateDetails(){
        profileAdvisorId.setText(String.valueOf(ClubAdvisor.clubAdvisorDetailsList.get(0).getClubAdvisorId()));
        profileAdvisorFname.setText(String.valueOf(ClubAdvisor.clubAdvisorDetailsList.get(0).getFirstName()));
        profileAdvisorLname.setText(String.valueOf(ClubAdvisor.clubAdvisorDetailsList.get(0).getLastName()));
        profileAdvisorUsername.setText(String.valueOf(String.valueOf(ClubAdvisor.clubAdvisorDetailsList.get(0).getUserName())));
        String contactNumber = makeTenDigitsForNumber(Integer.parseInt(ClubAdvisor.clubAdvisorDetailsList.get(0).getContactNumber()));
        profileAdvisorCnumber.setText((contactNumber));
        username =  String.valueOf(ClubAdvisor.clubAdvisorDetailsList.get(0).getUserName());
    }

    public void displayExistingPassword(){
        profileAdvisorpw.setText(String.valueOf(ClubAdvisor.clubAdvisorDetailsList.get(0).getPassword()));
    }



    public static String makeTenDigitsForNumber(int number) {
        String strNumber = Integer.toString(number);

        if (strNumber.length() < 10) {
            StringBuilder zeros = new StringBuilder();
            for (int i = 0; i < 10 - strNumber.length(); i++) {
                zeros.append('0');
            }
            return zeros.toString() + strNumber;
        } else {
            return strNumber.substring(0, 10);
        }
    }

}

