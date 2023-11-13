package ClubManager;

import SystemDataValidator.EventValidator;

import java.time.LocalDate;


public class EventManager implements EventValidator {

    public EventManager(){

    }
    @Override
    public boolean validateEventName(String EventName) {
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
    public boolean validateEventDate(LocalDate EventDate) {
        if(EventDate == null){
            return false;
        }

        LocalDate currentDate = LocalDate.now();

        if(!EventDate.isBefore(currentDate)){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean validateClubNameEvent(String ClubName) {
        return (ClubName.equals("None"));
    }


}
