package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @BeforeEach
    public void setup() {
        this.testQueue = new PatientQueue();
        this.patient1 = new Patient("A", 30, "Mild", 30);
        this.patient2 = new Patient("B", 25, "Moderate", 30);
        this.patient3 = new Patient("C", 20, "Severe", 30);
        this.patient4 = new Patient("D", 15, "Mild", 60);
        this.patient5 = new Patient("E", 10, "Moderate", 60);
        this.patient6 = new Patient("F", 5, "Severe", 60);
        this.patient7 = new Patient("G", 4, "Mild", 80);
        this.patient8 = new Patient("H", 90, "Moderate", 80);
        this.patient9 = new Patient("I", 100, "Severe", 80);

        testQueue.addPatient(patient1);
        testQueue.addPatient(patient2);
        testQueue.addPatient(patient3);
        testQueue.addPatient(patient4);
        testQueue.addPatient(patient5);
        testQueue.addPatient(patient6);
        testQueue.addPatient(patient7);
        testQueue.addPatient(patient8);
        testQueue.addPatient(patient9);
    }

    @Test
    void testConstructor(){
        assertEquals(9, testQueue.getTotalNumberOfPatients());
    }

    @Test
    void testIsPatientSevere() {
        setup();
        assertEquals(3, testQueue.getTotalNumberOfSeverePatients());
    }

    @Test
    void testIsPatientModerate() {
        setup();
        assertEquals(3, testQueue.getTotalNumberOfModeratePatients());
    }

    @Test
    void testIsPatientMild() {
        setup();
        assertEquals(3, testQueue.getTotalNumberOfMildPatients());
    }

    @Test
    void testSortAndViewQueue() {
        setup();
        assertEquals("I", testQueue.viewQueue().get(0).getName());
        assertEquals("F", testQueue.viewQueue().get(1).getName());
        assertEquals("C", testQueue.viewQueue().get(2).getName());
        assertEquals("H", testQueue.viewQueue().get(3).getName());
        assertEquals("E", testQueue.viewQueue().get(4).getName());
        assertEquals("B", testQueue.viewQueue().get(5).getName());
        assertEquals("G", testQueue.viewQueue().get(6).getName());
        assertEquals("D", testQueue.viewQueue().get(7).getName());
        assertEquals("A", testQueue.viewQueue().get(8).getName());
    }

    @Test
    void testRemovePatient() {
        setup();
        testQueue.viewQueue();
        testQueue.removePatient();
        assertEquals(8, testQueue.remainingPatient());

    }
}