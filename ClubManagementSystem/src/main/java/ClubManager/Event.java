package ClubManager;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Event {
    private String eventName;
    private String eventDescription;
    private Date eventDate;
    private LocalTime eventTime;
    private String eventLocation;
    private String eventType;
    private String eventDeliveryType;
    Club hostingClub;

    public Event(String eventName, String eventLocation, String eventType,
                 String eventDeliveryType, Date eventDate, LocalTime eventTime, Club hostingClub){
        this.eventName  = eventName;
        this.eventLocation = eventLocation;
        this.eventType = eventType;
        this.eventDeliveryType = eventDeliveryType;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.hostingClub = hostingClub;
    }
    public static ArrayList<Event> evenDetails = new ArrayList<>();

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

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
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
}
