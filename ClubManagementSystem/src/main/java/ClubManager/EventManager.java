package ClubManager;

import SystemDataValidator.EventValidator;
import SystemUsers.ClubAdvisor;

import java.time.LocalDate;
import java.time.LocalTime;

// work done by- Lakshan
public class EventManager implements EventValidator {

    // keep track on event name validation status
    public static boolean eventNameStatus;

    // keep track on event Location validation status
    public static boolean eventLocationStatus;

    // keep track on event Type validation status
    public static boolean eventTypeStatus;

    // keep track on event delivery type validation status
    public static boolean eventDeliveryTypeStatus;

    // keep track on event Date validation status
    public static boolean eventDateStatus;

    // keep track on event club name validate status
    public static boolean eventClubNameStatus;

    // Default constructor for the EventManger class
    public EventManager(){

    }

    // Override method from EventValidator interface to validate event name
    @Override
    public boolean validateEventName(String EventName) {
        // Check if the event name is null
        if(EventName == null){
            return false;
        }

        // return true if the event name is not empty
        return !EventName.isEmpty();
    }

    // Override method from EventValidator interface to validate event location
    @Override
    public boolean validateEventLocation(String EventLocation) {
        // return true if the event location is not empty
        return !EventLocation.isEmpty();
    }

    // Override method from EventValidator interface to validate event type
    @Override
    public boolean validateEventType(String EventType) {
        // return true if the event type is not "None"
        return (EventType == null || EventType.equals("None"));
    }


    // Override method from EventValidator interface to validate event delivery type
    @Override
    public boolean validateEventDeliveryType(String EventDeliveryType) {
        // return true if the event delivery type is not "None"
        return (EventDeliveryType.equals("None"));
    }

    // Override method from EventValidator interface to validate event date
    @Override
    public boolean validateEventDate(LocalDate eventDate) {
        if (eventDate == null) {
            // If event date is null it will return false
            System.out.println("EventDate is null");
            return false;
        }

        // Get the current date of the event
        LocalDate currentDate = LocalDate.now();

        // Check whether the event date is a past or a future date
        if (eventDate.isBefore(currentDate)) {
            // EventDate is in the past
            System.out.println("EventDate is in the past");
            return false;
        } else {
            // EventDate is on or after the current date
            return true;
        }
    }


    // Override method from EventValidator interface to validate event club name
    @Override
    public boolean validateClubNameEvent(String ClubName) {
        if(ClubName == null){
            // return false if the hosting club name is null
            return false;
        }
        // return true if the hosting club name is not "None"
        return (ClubName.equals("None"));
    }

    // method that returns the LocalTime data when event hour and minutes are given separately
    public LocalTime makeDateTime(String eventHour, String eventMinute){
        // Initialize the time variable
        LocalTime time = null;
        try{
            // convert the given hour and minute value to LocalDate
            time = LocalTime.of(Integer.parseInt(eventHour), Integer.parseInt(eventMinute));
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        
        return time;
    }

    // This method os responsible on validating all event related details
    public boolean validateAllEventDetails(String eventName, String eventLocation,
        String eventType, String eventDeliveryType, LocalDate eventDate, String clubName,
        String eventHour,  String eventMinutes, String status, String eventDescription){

        // validate event name
        eventNameStatus = validateEventName(eventName);
        System.out.println("Name" + eventNameStatus);

        // validate event location
        eventLocationStatus = validateEventLocation(eventLocation);
        System.out.println("Location" + eventLocationStatus);

        // validate event type
        eventTypeStatus = !validateEventType(eventType);
        System.out.println("Type" + eventTypeStatus);

        // validate event delivery type
        eventDeliveryTypeStatus= !validateEventDeliveryType(eventDeliveryType);
        System.out.println("DeliveryType" + eventDeliveryTypeStatus);

        // validate event date
        eventDateStatus = validateEventDate(eventDate);
        System.out.println("Date" + eventDateStatus);

        // validate event hosting club name
        eventClubNameStatus = !validateClubNameEvent(clubName);
        System.out.println("Club" + eventClubNameStatus);

        // Check whether user given event details are upto event detail validation standards
        if(EventManager.eventNameStatus && EventManager.eventLocationStatus &&
                EventManager.eventTypeStatus && EventManager.eventDeliveryTypeStatus &&
                EventManager.eventDateStatus && EventManager.eventClubNameStatus){

            // get the event time when event hour and minutes are given separately
            LocalTime eventTime = makeDateTime(eventHour, eventMinutes);

            // Check whether the if the user is going to create a club
            if(status.equals("create")){
                // if it is club creation calling the create club method from club advisor class
                ClubAdvisor clubAdvisor = new ClubAdvisor();

                // Event scheduling sequence : 1.1.1.1 : createEvent()
                clubAdvisor.createEvent(eventName,eventLocation, eventType, eventDeliveryType, eventDate,
                        eventTime,clubName, eventDescription);
                // If the process is successful return true
                return true;
            } else if (status.equals("update")) {
                // If the process is event data update, return true
                return true;
            }
        }

        return false;

    }

   // This method is responsible on returning the user selected club Object by its name
    public static Club userSelectedClubChooser(String clubName){
        for(Club club : Club.clubDetailsList){
            // If club name is found return club object
            if(club.getClubName().equals(clubName)){
                return club;
            }
        }

        // If club name is not found return null
        return null;
    }

}
