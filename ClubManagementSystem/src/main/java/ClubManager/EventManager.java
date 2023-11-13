package ClubManager;

import SystemDataValidator.EventValidator;
import SystemUsers.ClubAdvisor;

import java.time.LocalDate;
import java.time.LocalTime;


public class EventManager implements EventValidator {
    public static boolean eventNameStatus;
    public static boolean eventLocationStatus;
    public static boolean eventTypeStatus;
    public static boolean eventDeliveryTypeStatus;
    public static boolean eventDateStatus;
    public static boolean eventClubNameStatus;
    static Club userSelectedClub;

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


    public boolean validateAllEventDetails(String eventName, String eventLocation,
        String eventType, String eventDeliveryType, LocalDate eventDate, String clubName,
        String eventHour,  String eventMinutes, String status){

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

        if(EventManager.eventNameStatus && EventManager.eventLocationStatus &&
                EventManager.eventTypeStatus && EventManager.eventDeliveryTypeStatus &&
                EventManager.eventDateStatus && EventManager.eventClubNameStatus){
            LocalTime eventTime = makeDateTime(eventHour, eventMinutes);
            if(status.equals("create")){
                ClubAdvisor.createEvent(eventName,eventLocation, eventType, eventDeliveryType, eventDate, eventTime,clubName);
                return true;
            }
        }

        return false;

    }


    public static Club userSelectedClubChooser(String clubName){
        for(Club club : Club.clubDetailsList){
            if(club.getClubName().equals(clubName)){
                return club;
            }
        }

        return null;
    }

}
