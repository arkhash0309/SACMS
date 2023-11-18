package ClubAdvisorDashboardManager;

import ClubManager.Club;
import SystemUsers.ClubAdvisor;
import com.example.clubmanagementsystem.ApplicationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static ClubManager.Club.clubDetailsList;

public class ClubAdvisorActivityController extends ClubAdvisorDashboardControlller {
    final FileChooser fileChooser = new FileChooser();
    public static String imagePath;
    public static int clubIdSetterValue;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set cell value factories for the columns of the Create Club Table
        createClubTableId.setCellValueFactory(new PropertyValueFactory<>("clubId"));
        createClubTableName.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        createClubTableDescription.setCellValueFactory(new PropertyValueFactory<>("clubDescription"));
        createClubTableLogo.setCellValueFactory(new PropertyValueFactory<>("absoluteImage"));

//        Club club1 = new Club(0001, "Rotract", "Done with the work", "lkt.img");
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


//        updateClubDetailsTable.setItems(observableClubDetailsList);

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

    @FXML
    protected void clearScheduleEventFields(ActionEvent event) {

    }


    @Override
    public void clubCreationChecker(ActionEvent event) {
//        Club club1 = new Club(0001, "Rotract", "Done with the work", "lkt.img");
//        clubDetailsList.add(club1);

        boolean validState = true;
        int clubId = Integer.parseInt(this.clubId.getText());
        String clubName = this.clubName.getText();
        String clubDescription = this.clubDescription.getText();
        String clubLogo = this.createClubImage.getImage().getUrl();

        System.out.println(clubId);

        Club club = new Club(clubId,clubName,clubDescription);

        if (!club.validateClubName()){
            System.out.println("Wrong Club Name");
            validState = false;
        }
        displayClubNameError(clubNameError);

        if (!club.validateClubDescription()){
            System.out.println("Wrong Club Description");
            validState = false;
        }
        displayClubDecriptionError(clubDescriptionError);

        System.out.println("Valid Stat :" + validState );
        if (validState){
            Club clubData = new Club(clubId,clubName,clubDescription,imagePath);
            clubDetailsList.add(clubData);
            setCreateTable();
            setUpdateTable();
            clubIdSetterValue += 1;
            this.clubId.setText(String.valueOf(clubIdSetterValue));
        }
    }

    public void clubUpdateChecker(ActionEvent event) {
//        Club club1 = new Club(0001, "Rotract", "Done with the work", "lkt.img");
//        clubDetailsList.add(club1);

        boolean validState = true;
        int clubId = Integer.parseInt(updateClubID.getText());
        String clubName = updateClubName.getText();
        String clubDescription = updateClubDescription.getText();

        Club club = new Club(clubId,clubName,clubDescription);

        if (!club.validateClubName()){
            System.out.println("Wrong Club Name");
            validState = false;
        }
        displayClubNameError(updateClubNameError);

        if (!club.validateClubDescription()){
            System.out.println("Wrong Club Description");
            validState = false;
        }
        displayClubDecriptionError(updateClubDescriptionError);


        System.out.println("Valid state : " + validState);
        if (validState){
            for (Club foundClub : clubDetailsList){
                if (clubId == foundClub.getClubId()){
                    foundClub.setClubName(clubName);
                    foundClub.setClubDescription(clubDescription);
                    //Set club logo

                    //Updating club details tables
                    setCreateTable();
                    setUpdateTable();

                    //Update database
                }
            }
        }

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

    @Override
    void clubCreationReset(ActionEvent event) {
        clubName.setText("");
        clubDescription.setText("");
    }


    @FXML
    public void updateClubTableSelect(MouseEvent event) {
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
            alert.setTitle("Import Image Error !!!");
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
        clubId.setText(String.valueOf(clubIdSetterValue));

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

    static {
        clubIdSetterValue = 100;
    }

}
