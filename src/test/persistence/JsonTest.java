package persistence;

import model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPatient(String name, int age, String los, int waitTime, Patient patient) {
        assertEquals(name, patient.getPatientName());
        assertEquals(age, patient.getPatientAge());
        assertEquals(los, patient.getLevelOfSeverity());
        assertEquals(waitTime, patient.getWaitTime());

    }
}
