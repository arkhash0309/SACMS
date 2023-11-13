package ClubManager;

import SystemDataValidator.EventValidator;

import java.time.LocalDate;
import java.time.LocalTime;


public class EventManager implements EventValidator {

    static boolean eventNameStatus;
    static boolean eventLocationStatus;
    static boolean eventTypeStatus;
    static boolean eventDeliveryTypeStatus;
    static boolean eventDateStatus;
    static boolean eventClubNameStatus;
    static String selectedClubName;

    public EventManager(){

    }
    
    @Override
    public boolean validateEventName(String EventName) {
        if(EventName == null){
            return false;
        }
        return !EventName.isEmpty();
    }

    @Override
    public boolean validateEventLocation(String EventLocation) {
        return !EventLocation.isEmpty();
    }

    @Override
    public boolean validateEventType(String EventType) {
        return (EventType.equals("None"));
    }

    @Override
    public boolean validateEventDeliveryType(String EventDeliveryType) {
        return (EventDeliveryType.equals("None"));
    }

    @Override
    public boolean validateEventDate(LocalDate eventDate) {
        if (eventDate == null) {
            // Handle the case where eventDate is null (not selected)
            System.out.println("EventDate is null");
            return false;
        }

        LocalDate currentDate = LocalDate.now();

        if (eventDate.isBefore(currentDate)) {
            // EventDate is in the past
            System.out.println("EventDate is in the past");
            return false;
        } else {
            // EventDate is on or after the current date
            return true;
        }
    }


    @Override
    public boolean validateClubNameEvent(String ClubName) {
        if(ClubName == null){
            return false;
        }
        return (ClubName.equals("None"));
    }

    public LocalTime makeDateTime(String eventHour, String eventMinute){
        LocalTime time = null;
        try{
            time = LocalTime.of(Integer.parseInt(eventHour), Integer.parseInt(eventMinute));
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        
        return time;
    }


    public void validateAllEventDetails(String eventName, String eventLocation,
        String eventType, String eventDeliveryType, LocalDate eventDate, String clubName){

        eventNameStatus = validateEventName(eventName);
        System.out.println("Name" + eventNameStatus);

        eventLocationStatus = validateEventLocation(eventLocation);
        System.out.println("Location" + eventLocationStatus);

        eventTypeStatus = !validateEventType(eventType);
        System.out.println("Type" + eventTypeStatus);

        eventDeliveryTypeStatus= !validateEventDeliveryType(eventDeliveryType);
        System.out.println("DeliveryType" + eventDeliveryTypeStatus);

        eventDateStatus = validateEventDate(eventDate);
        System.out.println("Date" + eventDateStatus);

        eventClubNameStatus = !validateClubNameEvent(clubName);
        System.out.println("Club" + eventClubNameStatus);

    }

}
