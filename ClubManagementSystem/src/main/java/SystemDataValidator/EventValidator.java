package SystemDataValidator;


import java.time.LocalDate;
import java.util.Date;

public interface EventValidator {
    boolean validateEventName(String EventName);
    boolean validateEventLocation(String EventLocation);
    boolean validateEventType(String EventType);
    boolean validateEventDeliveryType(String EventDeliveryType);
    boolean validateEventDate(LocalDate EventDate);

}
