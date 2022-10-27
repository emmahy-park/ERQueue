package ui;

import model.Patient;
import model.PatientQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QueueApp {

    private Patient patient1;
    private Patient patient2;
    private Patient patient3;

    private String nextPatient;
    private PatientQueue patientQueue;
    private ArrayList<String> patientList;

    private Scanner input;

    // EFFECTS: runs the Queue application
    public QueueApp() {
        runQueue();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runQueue() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddPatients();
        } else if (command.equals("v")) {
            doViewQueue();
        } else if (command.equals("r")) {
            doRemovePatient();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes queue
    private void init() {
        this.patient1 = new Patient("A", 27, "Mild", 30);
        this.patient2 = new Patient("B", 72, "Moderate", 30);
        this.patient3 = new Patient("C", 27, "Severe", 30);

        this.patientQueue = new PatientQueue("Patient Queue");
        patientQueue.addPatient(patient1);
        patientQueue.addPatient(patient2);
        patientQueue.addPatient(patient3);

        this.input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add new patient");
        System.out.println("\tv -> view queue");
        System.out.println("\tr -> remove patient");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: add patient
    private void doAddPatients() {
        System.out.print("Enter patient information: \n");
        System.out.print("Patient name: ");
        String name = input.next();

        System.out.print("Patient age: ");
        int age = input.nextInt();

        System.out.print("Level of Severity (Mild/Moderate/Severe): ");
        String los = input.next();

        System.out.print("Wait Time (minutes): ");
        int time = input.nextInt();

        Patient patient = new Patient(name, age, los, time);
        patientQueue.addPatient(patient);

        System.out.println("\nPatient has been added successfully!\n");
    }

    // MODIFIES: this
    // EFFECTS: view patients in queue
    private void doViewQueue() {
        patientList = patientQueue.viewQueue();
        nextPatient = patientList.get(0);

        for (int i = 0; i < patientQueue.getTotalNumberOfPatients(); i++) {
            System.out.println(patientList.get(i));
        }

        printQueue(nextPatient);
        printNumber(patientList.size());
    }

    // MODIFIES: this
    // EFFECTS: remove patient
    private void doRemovePatient() {
        patientQueue.removePatient();
        System.out.print("\nPatient first in queue has seen a doctor.\n");

    }

    private void printQueue(String patientName) {
        System.out.printf("\nNext in queue: %s\n", patientName);
    }

    private void printNumber(int num) {
        System.out.printf("\nIn total there are %s number of patients in queue.\n", num);
    }
}
