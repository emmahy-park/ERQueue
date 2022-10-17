package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatientQueueTest {
    private PatientQueue testQueue;
    private Patient patient1;
    private Patient patient2;
    private Patient patient3;
    private Patient patient4;
    private Patient patient5;
    private Patient patient6;

    @BeforeEach
    public void setup() {
        this.testQueue = new PatientQueue();
        this.patient1 = new Patient("A", 30, "Mild", 30);
        this.patient2 = new Patient("B", 25, "Moderate", 30);
        this.patient3 = new Patient("C", 20, "Severe", 30);
        this.patient4 = new Patient("D", 15, "Mild", 60);
        this.patient5 = new Patient("E", 10, "Moderate", 60);
        this.patient6 = new Patient("F", 5, "Severe", 60);

        testQueue.addPatient(patient1);
        testQueue.addPatient(patient2);
        testQueue.addPatient(patient3);
        testQueue.addPatient(patient4);
        testQueue.addPatient(patient5);
        testQueue.addPatient(patient6);
    }

    @Test
    void testConstructor(){
        assertEquals(6, testQueue.getTotalNumberOfPatients());
    }

    @Test
    void testIsPatientSevere() {
        setup();
        assertEquals(2, testQueue.getTotalNumberOfSeverePatients());
    }

    @Test
    void testIsPatientModerate() {
        setup();
        assertEquals(2, testQueue.getTotalNumberOfModeratePatients());
    }

    @Test
    void testIsPatientMild() {
        setup();
        assertEquals(2, testQueue.getTotalNumberOfMildPatients());
    }

    @Test
    void testSortAndViewQueue() {
        setup();
        assertEquals("F", testQueue.viewQueue().get(0).getName());
        assertEquals("C", testQueue.viewQueue().get(1).getName());
        assertEquals("E", testQueue.viewQueue().get(2).getName());
        assertEquals("B", testQueue.viewQueue().get(3).getName());
        assertEquals("D", testQueue.viewQueue().get(4).getName());
        assertEquals("A", testQueue.viewQueue().get(5).getName());
    }

    @Test
    void testRemovePatient() {
        setup();
        testQueue.viewQueue();
        testQueue.removePatient();
        assertEquals(5, testQueue.remainingPatient());

    }
}