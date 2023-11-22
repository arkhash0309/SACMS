package ClubManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {
    // The following private variables representing the attributes of an event
    private String eventName; //Store name of the event variable
    private String eventDescription; // Store event description
    private LocalDate eventDate; // Store event date
    private LocalTime eventTime; // Store event Time
    private String eventLocation; // Store event Location
    private String eventType; // Store event Type
    private String eventDeliveryType; // Store event delivery Type
    private Club hostingClub; // Reference to the hosting club
    private int eventId; // Unique identifier for the event
    String hostingClubName; // Name of the hosting club to store in the table

    // The following arraylist will store the all event related details
    public static ArrayList<Event> eventDetails = new ArrayList<>();

    // Constructor for creating an Event object to create and update events
    public Event(String eventName, String eventLocation, String eventType,
                 String eventDeliveryType, LocalDate eventDate, LocalTime eventTime, Club hostingClub,
                 String eventDescription, int eventId){
        this.eventName  = eventName;
        this.eventLocation = eventLocation;
        this.eventType = eventType;
        this.eventDeliveryType = eventDeliveryType;
        this.eventDate = eventDate;
        this.setEventTime(eventTime);
        this.setHostingClub(hostingClub);
        this.eventDescription = eventDescription;
        hostingClubName = getClubName();
        this.eventId = eventId;
    }

    // Getter method to retrieve the name of the hosting club
    public String getClubName() {
        if(hostingClub != null){
            return hostingClub.getClubName();
        }else{
            return null;
        }
    }

    // Getter method for retrieving the event name
    public String getEventName() {
        return eventName;
    }

    // Setter method for setting the event name
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    // Getter method for retrieving the event description
    public String getEventDescription() {
        return eventDescription;
    }

    // Setter method for setting the event Description
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    // Getter method for retrieving the event date
    public LocalDate getEventDate() {
        return eventDate;
    }

    // Setter method for setting the event date
    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    // Getter method for retrieving the event Location
    public String getEventLocation() {
        return eventLocation;
    }

    // Setter method for setting the event Location
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    // Getter method for retrieving the event type
    public String getEventType() {
        return eventType;
    }

    // Setter method for setting the event type
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    // Getter method for retrieving the event delivery type
    public String getEventDeliveryType() {
        return eventDeliveryType;
    }

    // Setter method for setting the event delivery type
    public void setEventDeliveryType(String eventDeliveryType) {
        this.eventDeliveryType = eventDeliveryType;
    }

    // Getter method for retrieving the event time
    public LocalTime getEventTime() {
        return eventTime;
    }

    // Setter method for setting the event time
    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    // Getter method for retrieving the hosting club
    public Club getHostingClub() {
        return hostingClub;
    }

    // Setter method for setting the hosting club
    public void setHostingClub(Club hostingClub) {
        this.hostingClub = hostingClub;
    }

    // Getter method for retrieving the event ID
    public int getEventId() {
        return eventId;
    }

    // Setter method for setting the event ID
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
