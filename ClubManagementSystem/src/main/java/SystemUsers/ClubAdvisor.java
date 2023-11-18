package SystemUsers;

import ClubManager.Club;
import ClubManager.Event;
import ClubManager.EventManager;
import SystemDataValidator.ClubAdvisorValidator;
import com.example.clubmanagementsystem.HelloApplication;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ClubAdvisor extends User implements ClubAdvisorValidator {
    private int clubAdvisorId;
    public static ArrayList<ClubAdvisor> clubAdvisorDetailsList = new ArrayList<>();
    public static String advisorIdStatus = "";

    public ArrayList<Club> createdClubDetailsList = new ArrayList<>();

    public ClubAdvisor(String userName,String password,
                       String firstName, String lastName,
                       String contactNumber, int clubAdvisorId){
        super(userName, password, firstName, lastName, contactNumber);
        this.clubAdvisorId = clubAdvisorId;
    }

    public ClubAdvisor(){

    }
    public ClubAdvisor(String contactNumber){
        super(contactNumber);
    }

    @Override
    public void registerToSystem() {

    }

    @Override
    public void loginToSystem() {

    }

    @Override
    public void viewEvent() {

    }

    public  void createEvent(String eventName, String eventLocation,
                            String eventType, String eventDeliveryType,
                            LocalDate eventDate, LocalTime eventTime,
                            String clubName, String eventDescription){
        Club selectedClub = EventManager.userSelectedClubChooser(clubName);

        Event event = new Event(eventName, eventLocation, eventType,eventDeliveryType, eventDate, eventTime,
                selectedClub, eventDescription);
        Event.eventDetails.add(event);
        System.out.println("Event successfully Scheduled !!!");

        Alert eventCreateAlert = new Alert(Alert.AlertType.INFORMATION);
        eventCreateAlert.initModality(Modality.APPLICATION_MODAL);
        eventCreateAlert.setTitle("School Club Management System");
        eventCreateAlert.setHeaderText("Event successfully created !!!");
        eventCreateAlert.show();
    }


    public void updateEventDetails(Event event, int eventId){
          Event.eventDetails.set(eventId, event);
          Alert eventUpdateAlert = new Alert(Alert.AlertType.INFORMATION);
          eventUpdateAlert.initModality(Modality.APPLICATION_MODAL);
          eventUpdateAlert.setTitle("School Club Management System");
          eventUpdateAlert.setHeaderText("Event details successfully updated!!!");
          eventUpdateAlert.show();
    }

    public void cancelEvent(Event event, int selectedEventId){
        for(Event eventVal : Event.eventDetails){
            if(eventVal.getEventName().equals(event.getEventName())){
                Event.eventDetails.remove(selectedEventId);
                for(Event x : Event.eventDetails){
                    System.out.println(x);
                }
                break;
            }
        }

        Alert deletedEvent = new Alert(Alert.AlertType.INFORMATION);
        deletedEvent.setHeaderText("Event successfully cancelled !!!");
        deletedEvent.setTitle("School Club Management System");
        deletedEvent.show();

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
}
