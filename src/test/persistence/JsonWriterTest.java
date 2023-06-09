package persistence;

import model.Patient;
import model.PatientQueue;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    private PatientQueue patientQueue;
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            PatientQueue patientQueue = new PatientQueue("Patient Queue");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPatientQueue() {
        try {
            PatientQueue patientQueue = new PatientQueue("Patient Queue");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPatientQueue.json");
            writer.open();
            writer.write(patientQueue);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPatientQueue.json");
            patientQueue = reader.read();
            assertEquals("Patient Queue", patientQueue.getName());
            assertEquals(0, patientQueue.getTotalNumberOfPatients());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterPatientQueue() {
        try {
            patientQueue = new PatientQueue("Patient Queue");
            patientQueue.addPatient(new Patient("Emma", 27, "Severe", 30));
            patientQueue.addPatient(new Patient("Ray", 80, "Moderate", 30));
            patientQueue.addPatient(new Patient("Jen", 10, "Mild", 30));
            patientQueue.addPatient(new Patient("Sarah", 3, "Severe", 30));
            patientQueue.addPatient(new Patient("Mary", 20, "Moderate", 30));
            patientQueue.addPatient(new Patient("Nicole", 90, "Mild", 30));

            JsonWriter writer = new JsonWriter("./data/testWriterPatientQueue.json");
            writer.open();
            writer.write(patientQueue);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterPatientQueue.json");
            patientQueue = reader.read();
            assertEquals("Patient Queue", patientQueue.getName());
            List<Patient> patients = patientQueue.viewQueue();
            assertEquals(6, patients.size());
            checkPatient("Emma", 27, "B", 30, patients.get(1));
            checkPatient("Ray", 80, "C", 30, patients.get(2));
            checkPatient("Jen", 10, "F", 30, patients.get(5));
            checkPatient("Sarah", 3, "A", 30,patients.get(0));
            checkPatient("Mary", 20, "D", 30,patients.get(3));
            checkPatient("Nicole", 90, "E", 30,patients.get(4));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}