package SystemUsers;

import ClubAdvisorDashboardManager.ClubAdvisorActivityController;
import ClubManager.Attendance;
import ClubManager.Club;
import ClubManager.Event;
import ClubManager.EventManager;
import DataBaseManager.ClubAdvisorDataBaseManager;
import SystemDataValidator.ClubAdvisorValidator;
import com.example.clubmanagementsystem.HelloApplication;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;


import static ClubManager.Club.clubDetailsList;

// work done by- Lakshan, Pramuditha and some parts by Arkhash
public class ClubAdvisor extends User implements ClubAdvisorValidator {
    private int clubAdvisorId;
    public static ArrayList<ClubAdvisor> clubAdvisorDetailsList = new ArrayList<>();
    public static String advisorIdStatus = "";

    public ArrayList<Club> createdClubDetailsList = new ArrayList<>();

    public ClubAdvisor(String userName,String password, String firstName, String lastName, String contactNumber, int clubAdvisorId){
        super(userName, password, firstName, lastName, contactNumber);
        this.clubAdvisorId = clubAdvisorId;
    }


    public ClubAdvisor(String userName, String password){
        super(userName, password);
    }
    public ClubAdvisor(String contactNumber){
        super(contactNumber);
    }

    public ClubAdvisor(){

    }

    @Override
    public void registerToSystem() {

    }


//    @Override
//    public String studentRegisteringToSystem(){
//        return null;
//    }


    @Override
    public String LoginToSystem(){
        String correctPassword = null; // store correct password from database
        String credentialChdeckQuery = "SELECT teacherPassword FROM TeacherCredentials WHERE teacherUserName = ?";
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(credentialChdeckQuery)) { // prepare the statement to execute the code
            preparedStatement.setString(1, this.getUserName()); // we are setting the clubAdvisortLoginPageUserName to where the question mark is
            try (ResultSet results = preparedStatement.executeQuery()) { // results variable will store all the rows in Student table
                while (results.next()) { // this will loop the rows
                    correctPassword = results.getString("teacherPassword"); // get the password
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return correctPassword;
    }


    public void createClub(int clubId, String clubName, String clubDescription, String imagePath, int clubAdvisorId){
        //Creating a new club object with the correct user given data
        Club clubData = new Club(clubId, clubName, clubDescription, imagePath);
        //Adding that club to the club details list
        clubDetailsList.add(clubData);

        String insertQuery = "INSERT INTO Club (clubId, clubName, clubDescription, clubLogo, teacherInChargeId) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(insertQuery)
        ) {
            preparedStatement.setInt(1, clubId); // Set clubId
            preparedStatement.setString(2, clubName); // Set clubName
            preparedStatement.setString(3, clubDescription); // Set clubDescription
            preparedStatement.setString(4, imagePath); // Set clubLogo
            preparedStatement.setInt(5, clubAdvisorId); // Set teacherInChargeId
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClub(int clubId, String clubName, String clubDescription, String imagePath, int clubAdvisorId){
//        //Update database
        String updateQuery = "UPDATE Club SET clubName=?, clubDescription=?, clubLogo=?, teacherInChargeId=? WHERE clubId=?";

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(updateQuery)
        ) {
            preparedStatement.setString(1, clubName);
            preparedStatement.setString(2, clubDescription);
            preparedStatement.setString(3, imagePath);
            preparedStatement.setInt(4, clubAdvisorId);
            preparedStatement.setInt(5, clubId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   // This method is responsible on creating events, taking the parameters of all data types
    public  void createEvent(String eventName, String eventLocation,
                            String eventType, String eventDeliveryType,
                            LocalDate eventDate, LocalTime eventTime,
                            String clubName, String eventDescription){
        // Creating a club object to get the hosting club
        Club selectedClub = EventManager.userSelectedClubChooser(clubName);

        // Creating an event object to track the event
        Event event = new Event(eventName, eventLocation, eventType,eventDeliveryType, eventDate, eventTime,
                selectedClub, eventDescription, 0);

        System.out.println("Event successfully Scheduled !!!");

        // This query is written to insert the event related details to the database
        String EventsQuery = "INSERT INTO EventDetails (eventName, eventDate, eventTime, eventLocation, eventType, eventDeliveryType, eventDescription, clubId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(EventsQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, eventName); // set the event name
            preparedStatement.setDate(2, Date.valueOf(eventDate)); // set the event date
            preparedStatement.setTime(3, Time.valueOf(eventTime)); // set the event time
            preparedStatement.setString(4, eventLocation); // set the event location
            preparedStatement.setString(5, eventType); // set the event type
            preparedStatement.setString(6, eventDeliveryType); // set the event delivery type
            preparedStatement.setString(7, eventDescription); // set event description
            assert selectedClub != null;
            preparedStatement.setInt(8, selectedClub.getClubId()); // set the club id

            // Execute the insert and retrieve the generated keys
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the generated keys
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    // This is written to track the event id of the made event
                    if (generatedKeys.next()) {
                        int eventId = generatedKeys.getInt(1);
                        event.setEventId(eventId);  // set the event id
                        System.out.println("This is my : " + eventId);
                    }
                }
            }
        // Handle the exception to creating an event
        } catch (Exception e) {
            System.out.println("Wrong !!!");
            System.out.println(e);
            return;
        }

        // Adding the created event details to event details list
        Event.eventDetails.add(event);

        // Show alert to user that event successfully scheduled
        Alert eventCreateAlert = new Alert(Alert.AlertType.INFORMATION);
        eventCreateAlert.initModality(Modality.APPLICATION_MODAL);
        eventCreateAlert.setTitle("School Club Management System");
        eventCreateAlert.setHeaderText("Event successfully created !!!");
        eventCreateAlert.show();

        System.out.println("Correct");
        // Make an attendance sheet for all the students
        studentAttendanceMaker(event, selectedClub);
    }

    // This method update the event details when user wants to update event
    public void updateEventDetails(Event event, int eventId){
        // set event updated event details in eventDetails using table id and its object
        Event.eventDetails.set(eventId, event);
        // get the hosting club of the event to be updated
        Club selectedClub = event.getHostingClub();

        // just for testing
        System.out.println("Lakhan  event name" +  event.getEventName() + " Id : " + event.getEventId());

        // This statement update all event related details of the selected event
        String updateEventQuery = "UPDATE EventDetails SET eventName = ?, eventDate = ?, eventTime = ?, eventLocation = ?, eventType = ?, eventDeliveryType = ?, eventDescription = ?, clubId = ? WHERE EventId = ?";
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(updateEventQuery)) {
            preparedStatement.setString(1, event.getEventName());  // get the event name and set
            preparedStatement.setDate(2, Date.valueOf(event.getEventDate())); //get the event date and set
            preparedStatement.setTime(3, Time.valueOf(event.getEventTime())); // get the event time and set
            preparedStatement.setString(4, event.getEventLocation()); // get the event location and set
            preparedStatement.setString(5, event.getEventType()); // get the event type and set
            preparedStatement.setString(6, event.getEventDeliveryType()); // get the event delivery type and set
            preparedStatement.setString(7, event.getEventDeliveryType()); // get the event delivery type and set

            assert selectedClub != null;
            preparedStatement.setInt(8, selectedClub.getClubId()); // get and set the club id
            preparedStatement.setInt(9, event.getEventId()); // get and set the event id
            preparedStatement.executeUpdate();  // execute the update of the query
            // Handle the exceptions when running the sql statement
        } catch (Exception e) {
            System.out.println("Error updating event!");
            System.out.println(e);
            return;
        }

        // If the event details are successfully updated show the updated alert
        Alert eventUpdateAlert = new Alert(Alert.AlertType.INFORMATION);
        eventUpdateAlert.initModality(Modality.APPLICATION_MODAL);
        eventUpdateAlert.setTitle("School Club Management System");
        eventUpdateAlert.setHeaderText("Event details successfully updated!!!");
        eventUpdateAlert.show();

        // call the update attendance update query ti the system
        updateAttendanceDetails(event, selectedClub);

    }


    public void updateAttendanceDetails(Event event , Club club){
        String query = "UPDATE StudentAttendance SET clubId = ? WHERE EventId = ?";

        try (PreparedStatement statement = HelloApplication.connection.prepareStatement(query)) {

            // Set parameters for the update
            statement.setInt(1, club.getClubId());
            statement.setInt(2, event.getEventId());

            // Execute the update query
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Update successful event!");
            } else {
                System.out.println("No rows updated. Check the provided IDs.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // This method handles updating all event details
    public void cancelEvent(Event event, int selectedEventId){
        // delete the attendance sheet details made for the deleting event
        cancelAttendanceSheet(event);


        // This print statement is done just for the testing
        for(Event eventVal : Event.eventDetails){
            if(eventVal.getEventName().equals(event.getEventName())){
                Event.eventDetails.remove(selectedEventId);
                for(Event x : Event.eventDetails){
                    System.out.println(x);
                }
                break;
            }
        }

        // This query deletes the event details using the event id
        String deleteEventQuery = "DELETE FROM EventDetails WHERE EventId = ?";
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(deleteEventQuery)) {
            preparedStatement.setInt(1, event.getEventId());
            // execute the delete statement of the event
            preparedStatement.executeUpdate();

        // Handle the exceptions for deleting event details
        } catch (Exception e) {
            System.out.println("Error deleting event!");
            System.out.println(e);
            return;
        }

        // Show event successfully deleted massage
        Alert deletedEvent = new Alert(Alert.AlertType.INFORMATION);
        deletedEvent.setHeaderText("Event successfully cancelled !!!");
        deletedEvent.setTitle("School Club Management System");
        deletedEvent.show();

    }

    public void cancelAttendanceSheet(Event event){
        // SQL delete query
        String sql = "DELETE FROM StudentAttendance WHERE EventId = ?";

        try (PreparedStatement statement = HelloApplication.connection.prepareStatement(sql)) {

            // Set parameter to delete
            statement.setInt(1, event.getEventId());

            // Execute the delete query
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Deletion successful for Attendance!");
            } else {
                System.out.println("No rows deleted. Check the provided EventId.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ClubAdvisor(String userName,String password,
                       String firstName, String lastName){
        super(userName, password, firstName, lastName);
    }

    public ClubAdvisor(int clubAdvisorId){
        super();
        this.clubAdvisorId = clubAdvisorId;
    }

    public int getClubAdvisorId() {
        return clubAdvisorId;
    }

    public void setClubAdvisorId(int clubAdvisorId) {
        this.clubAdvisorId = clubAdvisorId;
    }

    public void passwordChecker(){

    }

    @Override
    public boolean validateClubAdvisorId() throws SQLException {
        if(String.valueOf(this.getClubAdvisorId()).isEmpty()){
            advisorIdStatus = "empty";
            System.out.println("Empty");
            return false;
        }

        if(String.valueOf(this.getClubAdvisorId()).length() != 6){
            advisorIdStatus = "length";
            System.out.println("more than 6");
            return false;
        }

        String sql = "SELECT * FROM TeacherInCharge  WHERE teacherInChargeId = ?";
        PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(this.getClubAdvisorId()));
        ResultSet results = preparedStatement.executeQuery();

        int dbClubAdvisorId = 0;
        while(results.next()){
            dbClubAdvisorId = Integer.parseInt(results.getString(1));
            System.out.println(dbClubAdvisorId);
        }

        if(this.getClubAdvisorId() == dbClubAdvisorId){
            System.out.println("Club Advisor already exists !!!");
            advisorIdStatus = "exist";
            return false;
        }else{
            return true;
        }
    }

    public void generateMembershipDetailReport(TableView<Student> tableView, Stage stage) throws IOException {
        ClubAdvisorActivityController.generateMembershipCsv(tableView, stage);
    }

    // This method generates the event details report
    public void generateEventDetailReport(TableView<Event> tableView, Stage stage) throws IOException {
        ClubAdvisorActivityController.generateCsv(tableView, stage);
    }

    public void generateClubAdvisorRegistrationDetailReport(TableView<ClubAdvisor> tableView, Stage stage) throws IOException {
        ClubAdvisorActivityController.generateAdvisorCsv(tableView, stage);
    }

    public void generateStudentRegistrationReport(TableView<Student> tableView, Stage stage) throws IOException {
        ClubAdvisorActivityController.generateMembershipCsv(tableView, stage);
    }

    public void generateStudentAttendanceReport(TableView<Attendance> tableView, Stage stage) throws IOException{
        ClubAdvisorActivityController.generateAttendanceCsv(tableView, stage);
    }

    public void TrackAttendance(Event trackingEvent, ObservableList<Attendance> attendanceData) {
        // Update the eventAttendance list in the selected event
        trackingEvent.eventAttendance.clear();
        trackingEvent.eventAttendance.addAll(attendanceData);

        // Find the index of the trackingEvent in the static eventDetails list
        int eventIndex = Event.eventDetails.indexOf(trackingEvent);

        // Replace the old trackingEvent with the updated one in the static eventDetails list
        Event.eventDetails.set(eventIndex, trackingEvent);

        Club eventClub = trackingEvent.getHostingClub();

        for(Attendance attendance : trackingEvent.eventAttendance){
            String sql = "UPDATE StudentAttendance SET attendanceStatus = ? WHERE studentAdmissionNum = ? AND clubId = ? AND EventId = ?";

            try (PreparedStatement statement = HelloApplication.connection.prepareStatement(sql)) {
                System.out.println("Laka" + attendance.attendanceStatusProperty() + " " +
                        attendance.getStudent().getFirstName() + " " + attendance.getStudent().getLastName());
                // Set values for the parameters in the prepared statement
                statement.setBoolean(1, attendance.attendanceStatusProperty());
                statement.setInt(2, attendance.getStudent().getStudentAdmissionNum());
                statement.setInt(3, eventClub.getClubId());
                statement.setInt(4, trackingEvent.getEventId());

                System.out.println(attendance.attendanceStatusProperty() + " " + attendance.getStudent().getStudentAdmissionNum()
                 + " " + eventClub.getClubId() + " " + trackingEvent.getEventId());
                // Execute the update query
                int rowsUpdated = statement.executeUpdate();

                // Check if the update was successful
                if (rowsUpdated > 0) {
                    System.out.println("Attendance status updated successfully!");
                } else {
                    System.out.println("Update failed. No matching record found.");
                }

                System.out.println(attendance.getStudentName() + " " + attendance.attendanceStatusProperty());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void studentAttendanceMaker(Event event, Club club) {
        for (Map.Entry<Student, ArrayList<Club>> entry : ClubAdvisorDataBaseManager.joinedClubForEachStudent.entrySet()) {
            Student student = entry.getKey();
            ArrayList<Club> clubs = entry.getValue();

            for (Club clubVal : clubs) {
                if (clubVal.getClubName().equals(event.getClubName())) {
                    // SQL query to insert values into StudentAttendance table
                    String sql = "INSERT INTO StudentAttendance (studentAdmissionNum, clubId, EventId, attendanceStatus) VALUES (?, ?, ?, ?)";

                    try (PreparedStatement statement = HelloApplication.connection.prepareStatement(sql)) {
                        // Set values for the parameters in the prepared statement
                        statement.setInt(1, student.getStudentAdmissionNum());
                        statement.setInt(2, club.getClubId());
                        statement.setInt(3, event.getEventId());
                        statement.setBoolean(4, false);

                        // Execute the insert query
                        statement.executeUpdate();
                        System.out.println("Done and dusted");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }





}
