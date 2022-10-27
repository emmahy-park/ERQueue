package persistence;

import model.Patient;
import model.PatientQueue;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PatientQueue patientQueue = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPatientQueue() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPatientQueue.json");
        try {
            PatientQueue patientQueue = reader.read();
            assertEquals("Patient Queue", patientQueue.getName());
            assertEquals(0, patientQueue.getTotalNumberOfPatients());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderPatientQueue() {
        JsonReader reader = new JsonReader("./data/testReaderPatientQueue.json");
        try {
            PatientQueue patientQueue = reader.read();
            assertEquals("Patient Queue", patientQueue.getName());
            List<Patient> patients = patientQueue.getPatients();
            assertEquals(1, patients.size());
            checkPatient("Emma", 27, "Severe", 30, patients.get(0));
            //checkPatient("Ray", 80, "Moderate", 30, patients.get(1));
            //checkPatient("Jen", 10, "Mild", 30, patients.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}