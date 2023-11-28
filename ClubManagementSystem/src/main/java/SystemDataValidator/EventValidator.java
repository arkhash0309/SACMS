package SystemDataValidator;


import java.time.LocalDate;

// work done by- Lakshan
// This interface shows the main validations done for event related details
public interface EventValidator {
    // method that event name
    boolean validateEventName(String EventName);

    // method that validate event location
    boolean validateEventLocation(String EventLocation);

    // method that validate event type
    boolean validateEventType(String EventType);

    // method that validate event delivery type
    boolean validateEventDeliveryType(String EventDeliveryType);

    // method that validate event date
    boolean validateEventDate(LocalDate EventDate);

    // method that validate event club name
    boolean validateClubNameEvent(String ClubName);

}
