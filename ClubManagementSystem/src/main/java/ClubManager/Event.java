package ClubManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {
    private String eventName;
    private String eventDescription;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String eventLocation;
    private String eventType;
    private String eventDeliveryType;
    private Club hostingClub;
    String hostingClubName;
    public static ArrayList<Event> evenDetails = new ArrayList<>();

    public Event(String eventName, String eventLocation, String eventType,
                 String eventDeliveryType, LocalDate eventDate, LocalTime eventTime, Club hostingClub,
                 String eventDescription){
        this.eventName  = eventName;
        this.eventLocation = eventLocation;
        this.eventType = eventType;
        this.eventDeliveryType = eventDeliveryType;
        this.eventDate = eventDate;
        this.setEventTime(eventTime);
        this.setHostingClub(hostingClub);
        this.eventDescription = eventDescription;
        hostingClubName = getClubName();
    }

    public String getClubName() {
        if(hostingClub != null){
            return hostingClub.getClubName();
        }else{
            return null;
        }
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDeliveryType() {
        return eventDeliveryType;
    }

    public void setEventDeliveryType(String eventDeliveryType) {
        this.eventDeliveryType = eventDeliveryType;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public Club getHostingClub() {
        return hostingClub;
    }

    public void setHostingClub(Club hostingClub) {
        this.hostingClub = hostingClub;
    }
}
