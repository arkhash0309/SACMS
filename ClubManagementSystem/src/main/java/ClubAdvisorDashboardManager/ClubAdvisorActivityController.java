package ClubAdvisorDashboardManager;

import ClubManager.Club;
import SystemUsers.ClubAdvisor;
import SystemUsers.Student;
import SystemUsers.User;
import com.example.clubmanagementsystem.ApplicationController;
import ClubManager.Attendance;
import ClubManager.Event;
import ClubManager.EventManager;
import com.example.clubmanagementsystem.HelloApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.HttpCookie;
import java.net.URL;
import java.sql.PreparedStatement;
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



    public LocalDate selectedUpcomingDate;

    public LocalDate selectedMostFutureDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scheduleEventDatePicker.setEditable(false);
        updateEventDateDatePicker.setEditable(false);
        populateComboBoxes();
        findMaleFemaleStudentCount();
        displayEnrolledStudentCount();
        displayNumberOfClubAdvisors();
        populateGenerateReportClubs(generateReportClubNameComboBox);
        populateGenerateReportEventsTable();

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

        generateReportClubName.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        generateReportEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        generateReportEventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        generateReportEventTime.setCellValueFactory(new PropertyValueFactory<>("eventTime"));
        generateReportEventLocation.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));
        generateReportEventType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        generateReportDeliveryType.setCellValueFactory(new PropertyValueFactory<>("eventDeliveryType"));
        generateReportEventDescription.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));

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


    // This method is responsible on populating various event tables with data from Event.event details list
    public void populateEventsTables(){
       // Check if Event.eventDetails is null, if it is return without populating tables
       if(Event.eventDetails == null){
           return;
       }

        // Clear currently populated items in each TableView to prepare for population
        scheduleCreatedEventTable.getItems().clear();
        updateEventTable.getItems().clear();
        cancelEventTable.getItems().clear();
        viewCreatedEventsTable.getItems().clear();
        generateReportEventViewTable.getItems().clear();

        // Setting the table column resize policy to view the tables
        scheduleCreatedEventTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        updateEventTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        cancelEventTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        viewCreatedEventsTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        generateReportEventViewTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

       // Iterate through each event in Event.eventDetails and put the details into Event related tables
       for(Event value : Event.eventDetails){
           // Extract relevant information from the Event Objects
           Club hostingClub = value.getHostingClub();
           Event event = new Event(value.getEventName(), value.getEventLocation(),
                   value.getEventType(),value.getEventDeliveryType(), value.getEventDate(),
                   value.getEventTime(), hostingClub, value.getEventDescription(), value.getEventId());

           // Add the event to the items of each TableView
           ObservableList<Event> viewScheduledEvents = scheduleCreatedEventTable.getItems();
           viewScheduledEvents.add(event);
           // Put the event items to schedule created event table
           scheduleCreatedEventTable.setItems(viewScheduledEvents );

           // Put the event items to update event table
           ObservableList<Event> updateScheduledEvents = updateEventTable.getItems();
           updateScheduledEvents.add(event);
           updateEventTable.setItems(updateScheduledEvents );

           // Put the event items to cancelEventTable
           ObservableList<Event> cancelScheduledEvents = cancelEventTable.getItems();
           cancelScheduledEvents.add(event);
           cancelEventTable.setItems(cancelScheduledEvents );

           // Put the event items to viewCreatedEvents table
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


//            Image defaultImage = new Image("C:/Users/Asus/Desktop/OOD CW/OOD-Coursework/ClubManagementSystem/src/main/resources/Images/360_F_93856984_YszdhleLIiJzQG9L9pSGDCIvNu5GEWCc.jpg");
//            this.createClubImage.setImage(defaultImage);

            //Update database
            String insertQuery = "INSERT INTO Club (clubId, clubName, clubDescription, clubLogo, teacherInChargeId) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(insertQuery)
            ) {
                preparedStatement.setInt(1, clubIdSetterValue); // Set clubId
                preparedStatement.setString(2, clubData.getClubName()); // Set clubName
                preparedStatement.setString(3, clubData.getClubDescription()); // Set clubDescription
                preparedStatement.setString(4, clubData.getClubLogo()); // Set clubLogo
                preparedStatement.setInt(5, clubAdvisorId); // Set teacherInChargeId
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            clubIdSetterValue += 1;
            this.clubId.setText(String.valueOf(clubIdSetterValue));

            this.clubName.setText("");
            this.clubDescription.setText("");

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
                    String updateQuery = "UPDATE Club SET clubName=?, clubDescription=?, clubLogo=?, teacherInChargeId=? WHERE clubId=?";

                    try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(updateQuery)
                    ) {
                        preparedStatement.setString(1, clubName);
                        preparedStatement.setString(2, clubDescription);
                        preparedStatement.setString(3, clubLogo);
                        preparedStatement.setInt(4, clubAdvisorId);
                        preparedStatement.setInt(5, clubId);

                        preparedStatement.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("School Activity Club Management System");
            alert.setHeaderText(null);
            alert.setContentText("Image is not imported!");
            alert.showAndWait();
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


    // This method is used to clear all schedule event fields in event scheduling
    @Override
    public void clearScheduleEventFields(ActionEvent event){
        clearEventScheduleFieldsDefault();
    }

    // This method will be used to clear scheduled event fields when creating event details
    public void clearEventScheduleFieldsDefault(){
        scheduleEventNameTextField.setText(""); // set schedule EventName field empty
        scheduleEventsLocationTextField.setText(""); // set schedule event location empty
        scheduleEventDescriptionTextField.setText(""); // set event description empty
        scheduleEventDatePicker.setValue(null); // set the event date picker value null
        scheduleEventTypeCombo.getSelectionModel().selectFirst(); // select the first selection item of event type
        // select the first selection item of event delivery type
        ScheduleEventsDeliveryType.getSelectionModel().selectFirst();
        // set the schedule event starting minute as default 0  by setting the first value
        scheduleEventMinutes.getSelectionModel().selectFirst();
        // set the scheduling event starting hour as default as 0  by setting the first value
        scheduleEventHour.getSelectionModel().selectFirst();
        // set the scheduling event club name as not selected  by setting the first value
        scheduleEventsClubName.getSelectionModel().selectFirst();
        clearAllScheduleEventLabels();
    }

    // This method will be used clear update event fields when updating event details
    public void clearUpdateEventFields(){
        // set the update event club as not selected by setting the first value
        updateEventClubCombo.getSelectionModel().selectFirst();
        // set the update event type combo box as not selected  by setting the first value
        updateEventTypeCombo.getSelectionModel().selectFirst();
        // set update event delivery type combo box as not selected  by setting the first value
        updateEventDeliveryTypeCombo.getSelectionModel().selectFirst();
        // set the update event location as empty
        updateEventLocationTextField.setText("");
        // set the update  event name as empty
        updateEventNameTextField.setText("");
        // set the update event description as empty
        updateEventDescription.setText("");
        // set the update event date as null
        updateEventDateDatePicker.setValue(null);
        // select the update event starting hour as 0 by setting the first value
        updateHourComboBox.getSelectionModel().selectFirst();
        // select the event starting minute as  0 by setting the first value
        updateMinuteComboBox.getSelectionModel().selectFirst();
        // select the club related to the event ads None by selecting the first value
        updateEventClubCombo.getSelectionModel().selectFirst();
        updateEventClubCombo.getSelectionModel().selectFirst();
        // call to clear all the update field labels
        clearAllUpdateEventLabels();
    }

    // This method will clear all the update related event fields
   @Override
    protected void clearUpdateEventFields(ActionEvent event){
       clearUpdateEventFields();
    }

    // This method will check whether there are validation errors in the user given event name in both create and update
    @FXML
    void CheckNameError(KeyEvent event) {
        // taking the id of the textfield to do validations when typing
        String targetName = "TextField[id=scheduleEventNameTextField, styleClass=text-input text-field eventField]";
        // declare the event name variable
        String eventName;
        // create an object of event manager to do the validations
        EventManager eventManager = new EventManager();

        // Check whether typing in the schedule event textfield
        if(String.valueOf(event.getTarget()).equals(targetName)){
            // if it is the schedule event text field get the text from text field
            eventName = scheduleEventNameTextField.getText();

            System.out.println(event.getTarget());
            // validate the event name using validate event name method in eventManager class
            if(!eventManager.validateEventName(eventName)){
                // set the error label if the event name is null
                scheduleErrorLabelEventName.setText("Event name cannot be empty");
            }else{
                // set the error label empty if the event name is correct
                scheduleErrorLabelEventName.setText("");
            }
        }else{
            // if the selected textfield is not event scheduling text field, update error labels
            // get the text input of update textfield
            eventName = updateEventNameTextField.getText();
            System.out.println(event.getTarget());

            // validate the event name using validate event name method in event manager class
            if(!eventManager.validateEventName(eventName)){
                // set the error label if the event name is empty
                updateErrorLabelEventName.setText("Event name cannot be empty");
            }else{
                // set the error label empty if the event name is correct
                updateErrorLabelEventName.setText(" ");
            }
        }
    }

    /* This method will check whether user given event location is according to the validation standards in
    both schedule and update text fields */
    @FXML
    void CheckLocationError(KeyEvent event) {
        // taking the id of the textfield to do validations when typing
        String targetLocation = "TextField[id=scheduleEventsLocationTextField, styleClass=text-input text-field eventField]";

        // declare the event location variable
        String eventLocation;

        // create an object of event manager to do the validations
        EventManager eventManager = new EventManager();

        // Check whether typing in the schedule event textfield
        if(String.valueOf(event.getTarget()).equals(targetLocation)){
            // if it is the schedule event text field get the text from text field
            eventLocation = scheduleEventsLocationTextField.getText();

            System.out.println(event.getTarget());
            // validate the event location using validate event location method in eventManager class
            if(!eventManager.validateEventLocation(eventLocation)){
                // set the error label if the event location is null
                scheduleErrorLabelEventLocation.setText("Event Location cannot be empty");
            }else{
                // set the error label empty if the event location is correct
                scheduleErrorLabelEventLocation.setText(" ");
            }
        }else{
            // if it is the update event text field get the text from text field
            eventLocation = updateEventLocationTextField.getText();

            // validate the event location using validate event location method in eventManager class
            if(!eventManager.validateEventLocation(eventLocation)){
                // set the error label if the update event location error label if update location is null
                updateErrorLabelEventLocation.setText("Event Location cannot be empty");
            }else{
                // set the update location error label empty if the given location is correct
                updateErrorLabelEventLocation.setText(" ");
            }
        }
    }

    /*This method will check whether user given event type is according to the event type validations*/
    @FXML
    void CheckEventTypeError(ActionEvent event) {
        // Taking the id of the create event to do validations when typing
        String targetType = "ComboBox[id=scheduleEventTypeCombo, styleClass=combo-box-base combo-box eventField]";

        // declare the selected event type option
        String selectedOption;

        // create an eventManager object to check whether using given event type is correct
        EventManager eventManager = new EventManager();

        // check the event location textfield belongs to event scheduling
        if(String.valueOf(event.getTarget()).equals(targetType)){
            // get the current event scheduling type value to declared variable
            selectedOption = scheduleEventTypeCombo.getSelectionModel().getSelectedItem();
            System.out.println(event.getTarget());

            // do the event type validation
            if(eventManager.validateEventType(selectedOption)){
                // if the event type validation is correct set the label value
                scheduleErrorLabelEventType.setText("Event type cannot be None");
            }else{
                // if the event type validation is incorrect set the label value as null
                scheduleErrorLabelEventType.setText(" ");
            }
        }else{
            // if the selected option is in update text field, set the value to selected option
            selectedOption = updateEventTypeCombo.getSelectionModel().getSelectedItem();
            if(eventManager.validateEventType(selectedOption)){
                // Show the update error label if the validation status is false(None)
                updateErrorLabelEventType.setText("Event type cannot be None");
            }else{
                // Show the update error label if the validation status is true
                updateErrorLabelEventType.setText(" ");
            }
        }
    }

    /* This method will check whether event delivery type is according to the validation standards*/
    @FXML
    void checkDeliveryTypeError(ActionEvent event) {
        // set the id of the delivery type text field
        String targetDelivery= "ComboBox[id=ScheduleEventsDeliveryType, styleClass=combo-box-base combo-box eventField]";
        // declare the selectedOption
        String selectedOption;

        // Create an event manager object to check whether event delivery type is according to the validation standard
        EventManager eventManager = new EventManager();
        System.out.println(event.getTarget());

        // check whether the user typing text field
        if(String.valueOf(event.getTarget()).equals(targetDelivery)){
            // set the selected option value if the event delivery type belongs to event scheduling
            selectedOption= ScheduleEventsDeliveryType.getSelectionModel().getSelectedItem();
            if(eventManager.validateEventType(selectedOption)){
                // set the error label if the user selected delivery type is None (type)
                scheduleErrorLabelEventDeliveryType.setText("Event delivery type cannot be None");
            }else{
                // set the error label empty if the selected delivery type is correct
                scheduleErrorLabelEventDeliveryType.setText(" ");
            }
        }else{
            // set the selected option value if the event delivery type belongs to event updating
            selectedOption= updateEventDeliveryTypeCombo.getSelectionModel().getSelectedItem();
            if(eventManager.validateEventType(selectedOption)){
                // set the error label if the user selected delivery type is None (type)
                updateErrorLabelDeliveryType.setText("Event delivery type cannot be None");
            }else{
                // set the error label empty if the selected delivery type is correct
                updateErrorLabelDeliveryType.setText(" ");
            }
        }

    }

    @FXML
    void checkSelectedEventDate(ActionEvent event) {
        // set the selected date picker
        String targetDate = "DatePicker[id=scheduleEventDatePicker, styleClass=combo-box-base date-picker eventField]";

        // declare the selected date
        LocalDate selectedDate;

        // Create an event manager object to check whether event date is according to the date validation status
        EventManager eventManager = new EventManager();

        // check the selected date is in the update or schedule field
        if(String.valueOf(event.getTarget()).equals(targetDate)){
            // If the selected date is in the scheduling get its value
            selectedDate = scheduleEventDatePicker.getValue();

            System.out.println(event.getTarget());
            if(!eventManager.validateEventDate(selectedDate)){
                // display error, if the event date is not valid
                scheduleErrorLabelEventDate.setText("Event date cannot be a past date");
            }else{
                // display None, if the event date is valid
                scheduleErrorLabelEventDate.setText(" ");
            }
        }else{
            // get the current date value if the update option is selected
            selectedDate = updateEventDateDatePicker.getValue();

            System.out.println(event.getTarget());
            if(!eventManager.validateEventDate(selectedDate)){
                // display error if the event date is not valid
                updateErrorLabelEventDate.setText("Event date cannot be a past date");
            }else{
                // display None if the event date is valid
                updateErrorLabelEventDate.setText(" ");
            }

        }

    }

    @FXML
    void checkClubName(ActionEvent event) {
        // set the selected club Name
        String targetClub = "ComboBox[id=scheduleEventsClubName, styleClass=combo-box-base combo-box eventField]";

        // declare the club name
        String selectedClub;

        // Create EventManger object to check whether selected club name is valid
        EventManager eventManager = new EventManager();
        System.out.println(event.getTarget());

        // Check the selected club is in the update or schedule field
        if(String.valueOf(event.getTarget()).equals(targetClub)){

            selectedClub= scheduleEventsClubName.getSelectionModel().getSelectedItem();
            if(eventManager.validateEventType(selectedClub)){
                // display error, if the event club name is not valid
                scheduleErrorLabelClubName.setText("Club Name cannot be None");
            }else{
                // display None if the club name is valid
                scheduleErrorLabelClubName.setText(" ");
            }
        }else{
            // set the event update Club as selectedClub
            selectedClub = updateEventClubCombo.getSelectionModel().getSelectedItem();

            if(eventManager.validateEventType(selectedClub)){
                 // display error, if the event club name is not valid
                 updateErrorLabelClubName.setText("Club Name cannot be None");
            }else{
                 // display None if the club name is valid
                 updateErrorLabelClubName.setText(" ");
            }
        }
        System.out.println(event.getTarget());
    }

    // Method to populate comboBoxes with their club names for scheduling and updating events
    public void getCreatedClubs(){
        // Check if None is already their in the scheduleEventsClubName ComboBox
        if(!scheduleEventsClubName.getItems().contains("None")){
            // If not exist add it to scheduleEventsClubName comboBox
            scheduleEventsClubName.getItems().add("None");
        }

        // Check if None is already their in the updateEventClubCombo ComboBox
        if(!updateEventClubCombo.getItems().contains("None")){
            // If not exist add it to updateEventClubCombo comboBox
            updateEventClubCombo.getItems().add("None");
        }

        // Check if None is already their in the viewCreatedEventsSortComboBox ComboBox
        if(!viewCreatedEventsSortComboBox.getItems().contains("All Clubs")){
            // If not exist add it to viewCreatedEventsSortComboBox comboBox
            viewCreatedEventsSortComboBox.getItems().add("All Clubs");
        }

        // Iterate through each Club in the clubDetails list to populate the combo box
        for(Club club: Club.clubDetailsList){
            String clubName;
            clubName = club.getClubName();

            // Check if the clubName is already present in each ComboBox
            boolean scheduleContainStatus =  scheduleEventsClubName.getItems().contains(clubName);
            boolean updateContainsStatus =   updateEventClubCombo.getItems().contains(clubName);
            boolean viewContainsStatus = viewCreatedEventsSortComboBox.getItems().contains(clubName);

            // If the clubName is not already present, add it to each ComboBox
            if(!scheduleContainStatus){
                // Add the club names to scheduleEventsClubName
                scheduleEventsClubName.getItems().add(clubName);
            }

            if(!updateContainsStatus){
                // Add the club names to updateEventClubCombo
                updateEventClubCombo.getItems().add(clubName);
            }

            if(!viewContainsStatus){
                // Add the club names to viewCreatedEventsSortComboBox
                viewCreatedEventsSortComboBox.getItems().add(clubName);
            }

        }

        // Select the first item in each ComboBox
        scheduleEventsClubName.getSelectionModel().selectFirst();
        scheduleErrorLabelClubName.setText(" ");

        updateEventClubCombo.getSelectionModel().selectFirst();
        updateErrorLabelClubName.setText(" ");

        viewCreatedEventsSortComboBox.getSelectionModel().selectFirst();
    }


   /*This method is responsible on taking the user inputs and
   show error and information messages when scheduling events*/
    @FXML
    void scheduleEventController(ActionEvent event) {

        // Get event name for scheduling input
        String eventName = scheduleEventNameTextField.getText();

        // Get the event location for scheduling input
        String eventLocation = scheduleEventsLocationTextField.getText();

        // Get the eventDate for scheduling input
        LocalDate eventDate = scheduleEventDatePicker.getValue();

        // Get the eventDeliveryType for scheduling input
        String deliveryType = ScheduleEventsDeliveryType.getValue();

        // Get the eventType for scheduling input
        String eventType = scheduleEventTypeCombo.getValue();

        // Get the clubName for scheduling input
        String clubName = scheduleEventsClubName.getValue();

        // Get the eventStartHour for scheduling input
        String eventStartHour = scheduleEventHour.getValue();

        // Get the eventStartMinute for scheduling input
        String eventStartMinute = scheduleEventMinutes.getValue();

        // Get the eventDescription for scheduling input
        String eventDescription = scheduleEventDescriptionTextField.getText();

        // This EventManager object is used to user given event scheduling input
        EventManager eventManager = new EventManager();

        // stat variables checks whether all the event scheduling inputs are correct
        boolean stat = eventManager.validateAllEventDetails(eventName, eventLocation, eventType, deliveryType,
                eventDate, clubName, eventStartHour, eventStartMinute, "create", eventDescription);
        // If the validation status is true, populate all event related tables in event scheduling section
        if(stat){
            clearEventScheduleFieldsDefault(); // clear all event scheduling fields
            populateEventsTables(); // populating the tables
            displayNumberOfScheduledEvents(); // Update number of scheduled events
            getNextEventDate(); // get the next event date
        }else{
            // Show the event creation alert if event details are not valid
            Alert eventCreateAlert = new Alert(Alert.AlertType.WARNING);
            eventCreateAlert.initModality(Modality.APPLICATION_MODAL);
            eventCreateAlert.setTitle("School Club Management System");
            eventCreateAlert.setHeaderText("Please enter values properly to create an event!!!");
            eventCreateAlert.show();
        }
        // display the event related required fields
        DisplayEventErrors();
    }

    // This method is responsible on displaying event scheduling and updating error labels
    public void DisplayEventErrors(){
        // Check if the event date is not set to a future date
        if(!EventManager.eventDateStatus){
            // both update and scheduling date error labels will be displayed
            scheduleErrorLabelEventDate.setText("It is compulsory to set a future date");
            updateErrorLabelEventDate.setText("It is compulsory to set a future date");
        }else{
            // clear the error messages if the event date is valid
            scheduleErrorLabelEventDate.setText(" ");
            updateErrorLabelEventDate.setText(" ");
        }

        // Check if the event type is set to "None"
        if(!EventManager.eventTypeStatus){
            // both update and scheduling event type error will be displayed
            scheduleErrorLabelEventType.setText("Event type cannot be None");
            updateErrorLabelEventType.setText("Event type cannot be None");
        }else{
            // Clear the error messages if the event type is valid
            scheduleErrorLabelEventType.setText(" ");
            updateErrorLabelEventType.setText(" ");
        }

        // Check if the event delivery type is set to "None"
        if(!EventManager.eventDeliveryTypeStatus){
            // both and scheduling event type error will be displayed
            scheduleErrorLabelEventDeliveryType.setText("Event delivery type cannot be None");
            updateErrorLabelDeliveryType.setText("Event delivery type cannot be None");
        }else{
            // Clear the error messages if the event type is valid
            scheduleErrorLabelEventDeliveryType.setText("");
            updateErrorLabelDeliveryType.setText(" ");
        }

        // Check if the event location is empty
        if(!EventManager.eventLocationStatus){
            // both and scheduling event type error will be displayed
            scheduleErrorLabelEventLocation.setText("Event Location cannot be empty");
            updateErrorLabelEventLocation.setText("Event Location cannot be empty");
        }else{
            // Clear the error messages if the event location is valid
            scheduleErrorLabelEventLocation.setText(" ");
            updateErrorLabelEventLocation.setText(" ");
        }

        // Check if the event name is empty
        if(!EventManager.eventNameStatus){
            // both and scheduling event type error will be displayed
            scheduleErrorLabelEventName.setText("Event name cannot be empty");
            updateErrorLabelEventName.setText("Event name cannot be empty");
        }else{
            // Clear the error messages if the event name is valid
            scheduleErrorLabelEventName.setText(" ");
            updateErrorLabelEventName.setText(" ");
        }

        // Check if the club name is set to "None"
        if(!EventManager.eventClubNameStatus){
            // both and scheduling event type error will be displayed
            scheduleErrorLabelClubName.setText("Club Name cannot be None");
            updateErrorLabelClubName.setText("Club Name cannot be None");
        }else{
            // Clear the error messages if the club name is valid
            scheduleErrorLabelClubName.setText("");
            updateErrorLabelClubName.setText(" ");
        }
    }


   // This method clears all error labels in scheduling events
    public void clearAllScheduleEventLabels(){
        scheduleErrorLabelEventName.setText(""); // clear event name error label
        scheduleErrorLabelEventLocation.setText(" "); // clear event location error label
        scheduleErrorLabelEventDate.setText(" "); // clear event date error label
        scheduleErrorLabelEventDeliveryType.setText(" "); // clear event delivery type error label
        scheduleErrorLabelEventType.setText(" "); // clear event type error label
        scheduleErrorLabelClubName.setText(" ");  // clear event hosting club error label
    }

    // This method clear all error labels in updating events
    public void clearAllUpdateEventLabels(){
        updateErrorLabelEventDate.setText(" "); // clear update event date error label
        updateErrorLabelDeliveryType.setText(" "); // clear update delivery type error label
        updateErrorLabelEventType.setText(" "); // clear update event type error label
        updateErrorLabelEventLocation.setText(" "); // clear update event location error label
        updateErrorLabelEventName.setText(" "); // clear update event name error label
        updateErrorLabelClubName.setText(" "); // clear update event hosting club error label
    }

    // Overloading method of below updateRowSelection method to handle update row selections
    @FXML
    public void updateRowSelection(MouseEvent event) {
        updateRowSelection();
    }

    /*This table selects the row that has to be updated in updateEventTable,
    * and it is responsible on enabling updating fields for events and populate them with the selected event details.
    * and, its sets the selected event value and Id for do the updates in the database.*/
    public void updateRowSelection(){
        try{
            // Check if an item is selected in the update events table
            if(!(updateEventTable.getSelectionModel().getSelectedItem() == null)){
                // Enabling input fields for updating events
                enableAllUpdateEventFields();
            }

            // Enable buttons for updating and clearing event fields
            updateEventFieldButton.setDisable(false);
            clearEventFieldButton.setDisable(false);

            // Get the selected event details and index
            selectedEventValue =  updateEventTable.getSelectionModel().getSelectedItem();
            selectedEventId = updateEventTable.getSelectionModel().getSelectedIndex();

            // Set all the UI components including text-fields, combo boxes and text area with selected event details
            updateEventClubCombo.setValue(String.valueOf(selectedEventValue.getClubName()));
            updateEventTypeCombo.setValue(String.valueOf(selectedEventValue.getEventType()));
            updateEventDeliveryTypeCombo.setValue(String.valueOf(selectedEventValue.getEventDeliveryType()));
            updateEventLocationTextField.setText(String.valueOf(selectedEventValue.getEventLocation()));
            updateEventNameTextField.setText(String.valueOf(selectedEventValue.getEventName()));
            updateEventDescription.setText(String.valueOf(selectedEventValue.getEventDescription()));
            updateEventDateDatePicker.setValue(selectedEventValue.getEventDate());

            // Set hour and minute values from the event's start time
            LocalTime startTime = selectedEventValue.getEventTime();
            int hour = startTime.getHour();

            // Check if the hour is less than 10
            if(hour < 10){
                // If the hour is a single digit, prepend '0' before setting the value in the combo box
                String hourVal = "0" + hour;
                updateHourComboBox.setValue(hourVal);
            }else{
                // If the hour is two digits, set the value in the combo box as a string
                updateHourComboBox.setValue(String.valueOf(hour));
            }


            int minute = startTime.getMinute();
            // Check if the minute is less than 10
            if(minute < 10){
                // If the minute is a single digit, prepend '0' before setting the value in the combo box
                String minuteVal = "0" + minute;
                updateMinuteComboBox.setValue(minuteVal);
            }else{
                // If the minute is two digits, set the value in the combo box as a string
                updateMinuteComboBox.setValue(String.valueOf(minute));
            }


            System.out.println(selectedEventValue.getClubName());

        }catch(NullPointerException E){
            // Handling the case where no values are selected
            System.out.println("No values");
        }

    }

   // This method disables all update fields in event updating
    public void disableAllUpdateEventFields(){
        updateEventClubCombo.setDisable(true); // Disable update event club combo box
        updateEventTypeCombo.setDisable(true); // Disable update event type combo box
        updateEventDeliveryTypeCombo.setDisable(true); // Disable update event delivery type combo box
        updateEventLocationTextField.setDisable(true); // Disable update event location text field
        updateEventNameTextField.setDisable(true); // Disable update event name text field
        updateEventDescription.setDisable(true); //  Disable update event description field
        updateEventDateDatePicker.setDisable(true); // Disable update event date picker
        updateHourComboBox.setDisable(true); // Disable update hour combo box
        updateMinuteComboBox.setDisable(true); // Disable update minute combo box
        updateEventClubCombo.setDisable(true); // Disable update event club name combo box
        updateEventClubCombo.setDisable(true); // Disable update event club name combo box
    }

    // This method enables all update fields in event updating
    public void enableAllUpdateEventFields(){
        updateEventClubCombo.setDisable(false); // Enable update event club combo box
        updateEventTypeCombo.setDisable(false); // Enable update event type combo box
        updateEventDeliveryTypeCombo.setDisable(false); // Enable update event delivery type combo box
        updateEventLocationTextField.setDisable(false); // Enable update event location text field
        updateEventNameTextField.setDisable(false); // Enable update event name text field
        updateEventDescription.setDisable(false); // Enable update event description field
        updateEventDateDatePicker.setDisable(false); // Enable update event date picker
        updateHourComboBox.setDisable(false); // Enable update hour combo box
        updateMinuteComboBox.setDisable(false); // Enable update minute combo box
        updateEventClubCombo.setDisable(false); // Enable update event club name combo box
    }

    /*This method is responsible on  updating event related details*/
    @FXML
    void updateEventsController(ActionEvent event) {
        // set the event name by getting the value from updateEventNameTextField
        String eventName = updateEventNameTextField.getText();

        // set the event location by getting the value from updateEventLocationTextField
        String eventLocation = updateEventLocationTextField.getText();

        // set the event date by getting the value from updateEventDateDatePicker
        LocalDate eventDate = updateEventDateDatePicker.getValue();

        // set the event delivery type by getting the value from updateEventDeliveryTypeCombo
        String deliveryType = updateEventDeliveryTypeCombo.getValue();

        // set the event type by getting the value from updateEventTypeCombo
        String eventType = updateEventTypeCombo.getValue();

        // set the club Name type by getting the value from updateEventClubCombo
        String clubName = updateEventClubCombo.getValue();

        // set the event start hour by getting the value from updateHourComboBox
        String eventStartHour = updateHourComboBox.getValue();

        // set the event start minute by getting the value from updateMinuteComboBox
        String eventStartMinute = updateMinuteComboBox.getValue();

        // set the event description by getting the value from updateEventDescription
        String eventDescription = updateEventDescription.getText();

        // Calling event manager object to check whether all the event update details are according to validation standards
        EventManager eventManager = new EventManager();

        // calling the validateAllDetails method in event manager class to validate details
        boolean stat = eventManager.validateAllEventDetails(eventName, eventLocation, eventType, deliveryType,
                eventDate, clubName, eventStartHour, eventStartMinute, "update", eventDescription);

        if(stat){
            // Passing the updated eventName to Event class using its setter
            selectedEventValue.setEventName(eventName);
            // Passing the updated eventLocation to Event class using its setter
            selectedEventValue.setEventLocation(eventLocation);
            // Passing the updated eventDate to Event class using its setter
            selectedEventValue.setEventDate(eventDate);
            // Passing the updated eventDeliveryType to Event class using its setter
            selectedEventValue.setEventDeliveryType(deliveryType);
            // Passing the updated eventType to Event class using its setter
            selectedEventValue.setEventType(eventType);
            // Passing the updated eventHostingClub to Event class using its setter
            selectedEventValue.setHostingClub(EventManager.userSelectedClubChooser(clubName));
            // Passing the updated eventTime to Event class using its setter
            selectedEventValue.setEventTime(eventManager.makeDateTime(eventStartHour, eventStartMinute));
            // Passing the updated eventDescription to Event class using its setter
            selectedEventValue.setEventDescription(eventDescription);

            // create the given seperated hour and minute to Local time
            LocalTime eventStaringTime = eventManager.makeDateTime(eventStartHour, eventStartMinute);
            selectedEventValue.setEventTime(eventStaringTime);

            // creating a club advisor Object to update event details
            ClubAdvisor clubAdvisor = new ClubAdvisor();
            // call update event details method in clubAdvisor class
            clubAdvisor.updateEventDetails(selectedEventValue, selectedEventId);

            // Populate all the related event tables
            populateEventsTables();
            // Update next event date
            getNextEventDate();
            // call disable all update event fields
            disableAllUpdateEventFields();
            // Clear all update event fields
            clearUpdateEventFields();
        }else{
            // display alert if the user given update details are wrong
            Alert eventUpdateAlert = new Alert(Alert.AlertType.WARNING);
            eventUpdateAlert.initModality(Modality.APPLICATION_MODAL);
            eventUpdateAlert.setTitle("School Club Management System");
            eventUpdateAlert.setHeaderText("Please enter values properly to update an event!!!");
            eventUpdateAlert.show();
        }

        // Calling the method that displays update error labels
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
        populateEventList(viewCreatedEventsTable, viewCreatedEventsSortComboBox);
    }

    public void populateEventList(TableView<Event> table, ComboBox<String> comboBoxName) {
        ArrayList<LocalDate> selectedEventDates = new ArrayList<>();

        table.getItems().clear();
        ArrayList<Event> filteredEvents = new ArrayList<>();
        String selectedClub = comboBoxName.getSelectionModel().getSelectedItem();

        // Null check before comparing selectedClub
        if (selectedClub != null) {
            System.out.println(selectedClub + " bro");

            if (selectedClub.equals("All Clubs")) {
                if (table == generateReportEventViewTable) {
                    populateGenerateReportEventsTable();
                } else {
                    populateEventsTables();
                }
                return;
            } else {
                for (Event events : Event.eventDetails) {
                    if (events.getClubName().equals(selectedClub)) {
                        filteredEvents.add(events);
                    }
                }
            }

            int count = 0;
            for (Event value : filteredEvents) {
                Club hostingClubDetail = value.getHostingClub();
                Event requiredEvent = new Event(value.getEventName(), value.getEventLocation(),
                        value.getEventType(), value.getEventDeliveryType(), value.getEventDate(),
                        value.getEventTime(), hostingClubDetail, value.getEventDescription(), value.getEventId());

                ObservableList<Event> viewScheduledEvents = table.getItems();
                viewScheduledEvents.add(requiredEvent);
                table.setItems(viewScheduledEvents);

                count++;
                selectedEventDates.add(value.getEventDate());
            }

            if (!selectedEventDates.isEmpty()) {
                selectedUpcomingDate = findEarliestDate(selectedEventDates);
                selectedMostFutureDate = findMostFutureDate(selectedEventDates);
            }

            if (table == generateReportEventViewTable) {
                if(!selectedEventDates.isEmpty()){
                    UpcomingEventCountGenerateReports.setText("Total :  " + count);
                    eventDateRange.setText(selectedUpcomingDate + " - " + selectedMostFutureDate);
                }else{
                    UpcomingEventCountGenerateReports.setText("Total :  " + count);
                    eventDateRange.setText("No Events");
                }

            }
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
        populateGenerateReportEventsTable();
        populateGenerateReportClubs(generateReportClubNameComboBox);

    }

    @Override
    void GoToClubAdvisorProfile(ActionEvent event) {
        makeAllClubAdvisorPanesInvisible();
        makeAllButtonsColoured();
        ProfilePane.setVisible(true);
        AdvisorProfileButton.setStyle("-fx-background-color: linear-gradient(#fafada, #ffffd2)");
        displayStudentUpdateDetails();
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
        populateGenerateReportClubs(generateReportClubNameComboBox);
        populateGenerateReportEventsTable();
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
//        String advisorPassword = profileAdvisorpw.getText();

        ClubAdvisor clubAdvisor = new ClubAdvisor(advisorUsername, advisorFirstName, advisorLastName, advisorContactNumber, advisorId);

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
        String advisorExistingPassword = profileAdvisorExistingpw.getText();
        String advisorNewPassword = profileAdvisorNewpw.getText();
        String advisorConfirmPassword = profileAdvisorConfirmpw.getText();

        for (ClubAdvisor foundAdvisor : clubAdvisorDetailsList){
            if (advisorExistingPassword.equals(foundAdvisor.getPassword())){
                profileAdvisorExistingpwError.setText("");
                ClubAdvisor clubAdvisor = new ClubAdvisor(advisorUsername, advisorNewPassword, advisorFirstName, advisorLastName, advisorContactNumber, advisorId);

                if (!clubAdvisor.validatePassword("update")) {
                    System.out.println("Wrong password.");
                    validStat = false;
                }
//                else {
//                    User.passwordValidateStatus = "";
//                }
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

                            profileAdvisorExistingpw.setText("");
                            profileAdvisorNewpw.setText("");
                            profileAdvisorConfirmpw.setText("");

                            //Update database
                        }
                    }
                }
            }else {
                profileAdvisorExistingpwError.setText("Wrong password!");
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
            profileAdvisorNewpwError.setText("Password should consists of 8\ncharacters including numbers and\nspecial characters.");
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

//    public void displayExistingPassword(){
//        profileAdvisorpw.setText(String.valueOf(ClubAdvisor.clubAdvisorDetailsList.get(0).getPassword()));
//    }



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

    public void populateGenerateReportClubs(ComboBox<String> selectedCombo){
        selectedCombo.getItems().clear();
        if(!selectedCombo.getItems().contains("All Clubs")){
            selectedCombo.getItems().add("All Clubs");
        }

        for(Club club: clubDetailsList){
            selectedCombo.getItems().add(club.getClubName());
        }

        selectedCombo.getSelectionModel().selectFirst();
    }

    @FXML
    void populateGenerateReportsEventsFilteredTable(ActionEvent event) {
       populateEventList(generateReportEventViewTable, generateReportClubNameComboBox);
    }

    public void populateGenerateReportEventsTable() {
        ArrayList<LocalDate> selectedEventDates = new ArrayList<>();

        if (Event.eventDetails == null) {
            return;
        }

        generateReportEventViewTable.getItems().clear();
        generateReportEventViewTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        int count = 0;
        ObservableList<Event> viewCreatedScheduledEvents = FXCollections.observableArrayList();

        for (Event value : Event.eventDetails) {
            Club hostingClub = value.getHostingClub();
            Event event = new Event(value.getEventName(), value.getEventLocation(),
                    value.getEventType(), value.getEventDeliveryType(), value.getEventDate(),
                    value.getEventTime(), hostingClub, value.getEventDescription(), value.getEventId());

            viewCreatedScheduledEvents.add(event);
            selectedEventDates.add(event.getEventDate());
            count++;
        }

        generateReportEventViewTable.setItems(viewCreatedScheduledEvents);
        generateReportClubNameComboBox.getSelectionModel().selectFirst();

        if (selectedEventDates != null && !selectedEventDates.isEmpty()) {
            selectedUpcomingDate = findEarliestDate(selectedEventDates);
            selectedMostFutureDate = findMostFutureDate(selectedEventDates);
        }else{
            UpcomingEventCountGenerateReports.setText("Total: " + count);
            eventDateRange.setText("No Events");
        }

        UpcomingEventCountGenerateReports.setText("Total: " + count);
        eventDateRange.setText(selectedUpcomingDate + " - " + selectedMostFutureDate);
    }


    public static LocalDate findMostFutureDate(List<LocalDate> givenDateList) {
        if (givenDateList == null || givenDateList.isEmpty()) {
            throw new IllegalArgumentException("Date list cannot be null or empty.");
        }

        LocalDate mostFutureDate = givenDateList.get(0);

        for (LocalDate date : givenDateList) {
            if (date.isAfter(mostFutureDate)) {
                mostFutureDate = date;
            }
        }

        return mostFutureDate;
    }

    public static LocalDate findEarliestDate(List<LocalDate> givenDateList) {
        if (givenDateList == null || givenDateList.isEmpty()) {
            throw new IllegalArgumentException("Date list cannot be null or empty.");
        }

        LocalDate earliestDate = givenDateList.get(0);

        for (LocalDate date : givenDateList) {
            if (date.isBefore(earliestDate)) {
                earliestDate = date;
            }
        }

        return earliestDate;
    }


}

