package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    private Event e;
    private Date d;
    private Patient patient;

    @BeforeEach
    public void setUp() {
        this.patient = new Patient("A", 5, "Mild", 30);

        e = new Event("Added patient: " + patient.getPatientName());
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("Added patient: A", e.getDescription());
        assertEquals(d.toString(), e.getDate().toString());
    }
}
