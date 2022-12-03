package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {

    private PatientQueue testQueue;
    private Patient patient1;
    private Patient patient2;
    private Patient patient3;
    private Event e1, e2, e3, e4;

    @BeforeEach
    public void setup() {
        testQueue = new PatientQueue("Patient Queue");
        patient1 = new Patient("Emma", 27, "Mild", 30);
        patient2 = new Patient("Ben", 72, "Moderate", 30);
        patient3 = new Patient("Jenny", 27, "Severe", 30);

        testQueue.addPatient(patient1);
        testQueue.addPatient(patient2);
        testQueue.addPatient(patient3);
        testQueue.removePatient(0);


        e1 = new Event("Added patient: " + patient1.getPatientName());
        e2 = new Event("Added patient: " + patient2.getPatientName());
        e3 = new Event("Added patient: " + patient3.getPatientName());
        e4 = new Event("Removed patient: Jenny");

    }

    @Test
    void testConstructor() {
        assertEquals(2, testQueue.getTotalNumberOfPatients());
        assertEquals("Patient Queue", testQueue.getName());
    }

    @Test
    void testLogEvent() {
        List<Event> l = new ArrayList<>();

        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }

        assertTrue(l.toString().contains(e1.toString()));
        assertTrue(l.toString().contains(e2.toString()));
        assertTrue(l.toString().contains(e3.toString()));
        assertTrue(l.toString().contains(e4.toString()));
    }
}

