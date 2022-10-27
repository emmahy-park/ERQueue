package persistence;

import model.Patient;
import model.PatientQueue;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            PatientQueue wr = new PatientQueue("Patient Queue");
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
    void testWriterGeneralPatientQueue() {
        try {
            PatientQueue patientQueue = new PatientQueue("Patient Queue");
            patientQueue.addPatient(new Patient("D", 20, "Severe", 40));
            patientQueue.addPatient(new Patient("E", 20, "Moderate", 40));
            patientQueue.addPatient(new Patient("F", 20, "Mild", 40));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPatientQueue.json");
            writer.open();
            writer.write(patientQueue);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPatientQueue.json");
            patientQueue = reader.read();
            assertEquals("Patient Queue", patientQueue.getName());
            List<Patient> patients = patientQueue.getPatients();
            assertEquals(3, patients.size());
            checkPatient("D", 20, "Severe", 40, patients.get(0));
            checkPatient("E", 20, "Moderate", 40, patients.get(1));
            checkPatient("F", 20, "Mild", 40, patients.get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}