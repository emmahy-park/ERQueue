package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PatientQueueTest {
    private PatientQueue testQueue;
    private PatientQueue jsonQueue;
    public Patient patient1;
    public Patient patient2;
    public Patient patient3;
    public Patient patient4;
    public Patient patient5;
    public Patient patient6;
    public Patient patient7;
    public Patient patient8;
    public Patient patient9;
    public Patient patient10;
    public Patient patient11;
    public Patient patient12;
    public Patient patient13;
    public Patient patient14;
    public Patient patient15;
    public Patient patient16;
    public Patient patient17;
    public Patient patient18;
    public ArrayList<Patient> testList;

    @BeforeEach
    public void setup() {
        this.testQueue = new PatientQueue("Patient Queue");
        this.patient1 = new Patient("A", 5, "Mild", 30);
        this.patient2 = new Patient("B", 5, "Mild", 60);
        this.patient3 = new Patient("C", 5, "Moderate", 30);
        this.patient4 = new Patient("D", 5, "Moderate", 60);
        this.patient5 = new Patient("E", 5, "Severe", 30);
        this.patient6 = new Patient("F", 5, "Severe", 60);
        this.patient7 = new Patient("G", 50, "Mild", 30);
        this.patient8 = new Patient("H", 50, "Mild", 60);
        this.patient9 = new Patient("I", 50, "Moderate", 30);
        this.patient10 = new Patient("J", 50, "Moderate", 60);
        this.patient11 = new Patient("K", 50, "Severe", 30);
        this.patient12 = new Patient("L", 50, "Severe", 60);
        this.patient13 = new Patient("M", 85, "Mild", 30);
        this.patient14 = new Patient("N", 85, "Mild", 60);
        this.patient15 = new Patient("O", 85, "Moderate", 30);
        this.patient16 = new Patient("P", 85, "Moderate", 60);
        this.patient17 = new Patient("Q", 85, "Severe", 30);
        this.patient18 = new Patient("R", 85, "Severe", 60);

        testQueue.addPatient(patient1);
        testQueue.addPatient(patient2);
        testQueue.addPatient(patient3);
        testQueue.addPatient(patient4);
        testQueue.addPatient(patient5);
        testQueue.addPatient(patient6);
        testQueue.addPatient(patient7);
        testQueue.addPatient(patient8);
        testQueue.addPatient(patient9);
        testQueue.addPatient(patient10);
        testQueue.addPatient(patient11);
        testQueue.addPatient(patient12);
        testQueue.addPatient(patient13);
        testQueue.addPatient(patient14);
        testQueue.addPatient(patient15);
        testQueue.addPatient(patient16);
        testQueue.addPatient(patient17);
        testQueue.addPatient(patient18);
    }


    // Test the constructor
    // There are 9 patients in total
    @Test
    void testConstructor() {
        assertEquals(18, testQueue.getTotalNumberOfPatients());
        assertEquals("Patient Queue",testQueue.getName());
    }

    // Test sorting
    @Test
    void testQueue() {
        testList = testQueue.viewQueue();
        assertEquals("F", testList.get(0).getPatientName());
        assertEquals(5, testList.get(0).getPatientAge());
        assertEquals("R", testList.get(1).getPatientName());
        assertEquals(85, testList.get(1).getPatientAge());
        assertEquals("Q", testList.get(2).getPatientName());
        assertEquals(85, testList.get(2).getPatientAge());
        assertEquals("E", testList.get(3).getPatientName());
        assertEquals(5, testList.get(3).getPatientAge());
        assertEquals("L", testList.get(4).getPatientName());
        assertEquals(50, testList.get(4).getPatientAge());
        assertEquals("K", testList.get(5).getPatientName());
        assertEquals(50, testList.get(5).getPatientAge());
        assertEquals("P", testList.get(6).getPatientName());
        assertEquals(85, testList.get(6).getPatientAge());
        assertEquals("D", testList.get(7).getPatientName());
        assertEquals(5, testList.get(7).getPatientAge());
        assertEquals("C", testList.get(8).getPatientName());
        assertEquals(5, testList.get(8).getPatientAge());
        assertEquals("O", testList.get(9).getPatientName());
        assertEquals(85, testList.get(9).getPatientAge());
        assertEquals("J", testList.get(10).getPatientName());
        assertEquals(50, testList.get(10).getPatientAge());
        assertEquals("I", testList.get(11).getPatientName());
        assertEquals(50, testList.get(11).getPatientAge());
        assertEquals("N", testList.get(12).getPatientName());
        assertEquals(85, testList.get(12).getPatientAge());
        assertEquals("B", testList.get(13).getPatientName());
        assertEquals(5, testList.get(13).getPatientAge());
        assertEquals("M", testList.get(14).getPatientName());
        assertEquals(85, testList.get(14).getPatientAge());
        assertEquals("A", testList.get(15).getPatientName());
        assertEquals(5, testList.get(15).getPatientAge());
        assertEquals("H", testList.get(16).getPatientName());
        assertEquals(50, testList.get(16).getPatientAge());
        assertEquals("G", testList.get(17).getPatientName());
        assertEquals(50, testList.get(17).getPatientAge());

    }

    @Test
    void testRemovePatient() {
        assertEquals(18, testQueue.getTotalNumberOfPatients());
        testQueue.removePatient();
        assertEquals(17, testQueue.getTotalNumberOfPatients());
    }

    @Test
    void testJson() {
        this.jsonQueue = new PatientQueue("Json Queue");
        jsonQueue.addPatient(patient1);
        assertEquals("Json Queue", jsonQueue.toJson().get("name"));
    }
}