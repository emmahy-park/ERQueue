package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PatientQueueTest {
    private PatientQueue testQueue;
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
    public ArrayList<String> testList;


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
    }

    // Test sorting
    @Test
    void testQueue() {
        testList = testQueue.viewQueue();
        assertEquals("F", testList.get(0));
        assertEquals("R", testList.get(1));
        assertEquals("Q", testList.get(2));
        assertEquals("E", testList.get(3));
        assertEquals("L", testList.get(4));
        assertEquals("K", testList.get(5));
        assertEquals("P", testList.get(6));
        assertEquals("D", testList.get(7));
        assertEquals("C", testList.get(8));
        assertEquals("O", testList.get(9));
        assertEquals("J", testList.get(10));
        assertEquals("I", testList.get(11));
        assertEquals("N", testList.get(12));
        assertEquals("B", testList.get(13));
        assertEquals("M", testList.get(14));
        assertEquals("A", testList.get(15));
        assertEquals("H", testList.get(16));
        assertEquals("G", testList.get(17));

    }

    @Test
    void testRemovePatient() {
        assertEquals(18, testQueue.getTotalNumberOfPatients());
        testQueue.removePatient();
        assertEquals(17, testQueue.getTotalNumberOfPatients());
    }
}