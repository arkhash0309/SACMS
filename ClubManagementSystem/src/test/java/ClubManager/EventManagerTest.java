package ClubManager;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EventManagerTest {
    // This method test the event name
    @Test
    public void testValidateEventName() {
        // Create event manager object
        EventManager eventManager = new EventManager();
        // Do validations for event name
        assertTrue(eventManager.validateEventName("EventName"));
        assertFalse(eventManager.validateEventName(null));
        assertFalse(eventManager.validateEventName(""));
    }

    // This method test event location
    @Test
    public void testValidateEventLocation() {
        // Create event manager object
        EventManager eventManager = new EventManager();
        // Do validations for event location
        assertTrue(eventManager.validateEventLocation("EventLocation"));
        assertFalse(eventManager.validateEventLocation(""));
    }

    // This method test event type
    @Test
    public void testValidateEventType() {
        // Create eventManager Object
        EventManager eventManager = new EventManager();
        // Do validations for event type
        assertFalse(eventManager.validateEventType("Type"));
        assertTrue(eventManager.validateEventType("None"));
    }

    // This method test event delivery type
    @Test
    public void testValidateEventDeliveryType() {
        // Create eventManager Object
        EventManager eventManager = new EventManager();
        // Do validations for event delivery type
        assertFalse(eventManager.validateEventDeliveryType("DeliveryType"));
        assertTrue(eventManager.validateEventDeliveryType("None"));
    }

    // This method test event date
    @Test
    public void testValidateEventDate() {
        // Create eventManager Object
        EventManager eventManager = new EventManager();
        // Do validations for event date
        assertTrue(eventManager.validateEventDate(LocalDate.now().plusDays(1)));
        assertFalse(eventManager.validateEventDate(LocalDate.now().minusDays(1)));
    }

    // This method  test event scheduling time
    @Test
    public void testMakeDateTime() {
        // Create eventManager Object
        EventManager eventManager = new EventManager();
        // Do validations for event time
        LocalTime time = eventManager.makeDateTime("12", "30");
        assertNotNull(time);
        assertEquals(12, time.getHour());
        assertEquals(30, time.getMinute());
    }

}