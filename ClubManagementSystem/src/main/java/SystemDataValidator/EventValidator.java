package SystemDataValidator;


import java.time.LocalDate;

// work done by- Lakshan
// This interface shows the main validations done for event related details
public interface EventValidator {
    // method that event name
    boolean validateEventName(String EventName); // Method to validate the event name

    // method that validate event location
    boolean validateEventLocation(String EventLocation); // Method to validate the event location

    // method that validate event type
    boolean validateEventType(String EventType); // Method to validate the event type

    // method that validate event delivery type
    boolean validateEventDeliveryType(String EventDeliveryType); // Method to validate the event delivery type

    // method that validate event date
    boolean validateEventDate(LocalDate EventDate); // Method to validate the event date

    // method that validate event club name
    boolean validateClubNameEvent(String ClubName); // Method to validate the club name

}
