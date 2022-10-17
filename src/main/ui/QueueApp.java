package ui;

import model.Patient;
import model.PatientQueue;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QueueApp {

    private Patient patient1;
    private Patient patient2;
    private Patient patient3;

    private Patient nextPatient;
    private PatientQueue newPatients;
    private Scanner input;

    // EFFECTS: runs the Queue application
    public QueueApp() {
        runQueue();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runQueue() {
        boolean kepGoing = true;
        String command = null;

        init();

        while (kepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                kepGoing = false;
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

        this.newPatients = new PatientQueue();
        newPatients.addPatient(patient1);
        newPatients.addPatient(patient2);
        newPatients.addPatient(patient3);


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
        PatientQueue newPatients = storePatient();
        System.out.print("Enter patient information: ");
        System.out.print("Patient name: ");
        String name = input.next();

        System.out.print("Patient age: ");
        int age = input.nextInt();

        System.out.print("Level of Severity (Mild/Moderate/Severe): ");
        String los = input.next();

        System.out.print("Wait Time (minutes): ");
        int time = input.nextInt();

        newPatients.addPatient(new Patient(name, age, los, time));

        System.out.println("Patient has been added successfully!");

        doViewQueue();

    }

    // MODIFIES: this
    // EFFECTS: view patients in queue
    private void doViewQueue() {
        for (int i = 0; i < newPatients.getTotalNumberOfPatients(); i++) {
            System.out.println(newPatients.viewQueue().get(i).getName());
        }
        nextPatient = newPatients.viewQueue().get(0);
        printQueue(nextPatient);
    }

    // MODIFIES: this
    // EFFECTS: remove patient
    private void doRemovePatient() {
        newPatients.removePatient();
        newPatients.viewQueue();
        System.out.print("Patient first in queue has seen a doctor.\n");
        doViewQueue();
    }

    // EFFECTS: prompts user to keep on adding patients and returns it
    private PatientQueue storePatient() {
        String store = ""; // force entry into loop

        while (store.equals("a")) {
            System.out.println("a for add new patient");
            store = input.next();
            store = store.toLowerCase();
        }
        return newPatients;
    }


    private void printQueue(Patient patient) {
        System.out.printf("Next in queue: %s\n", patient.getName());
    }
}
