package ui;

import model.Patient;
import model.PatientQueue;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class QueueApp {

    private Patient patient1;
    private Patient patient2;
    private Patient patient3;

    private Patient nextPatient;
    private PatientQueue patientQueue;
    private ArrayList<Patient> patientList;

    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/patientQueue.json";

    // EFFECTS: runs the Queue application
    public QueueApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        patientQueue = new PatientQueue("Patient Queue");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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
                savePatientQueue();
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
        } else if (command.equals("s")) {
            savePatientQueue();
        } else if (command.equals("l")) {
            loadPatientQueue();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes queue
    private void init() {
        this.patient1 = new Patient("Emma", 27, "Mild", 30);
        this.patient2 = new Patient("Ben", 72, "Moderate", 30);
        this.patient3 = new Patient("Jenny", 27, "Severe", 30);

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
        System.out.println("\ts -> save patient queue");
        System.out.println("\tl -> load patient queue");
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

        doViewQueue();
    }

    // MODIFIES: this
    // EFFECTS: view patients in queue
    private void doViewQueue() {
        patientList = patientQueue.viewQueue();
        if (patientQueue.getTotalNumberOfPatients() == 0) {
            System.out.print("\nThere is no patient in the queue\n");
        } else {
            nextPatient = patientList.get(0);

            for (int i = 0; i < patientQueue.getTotalNumberOfPatients(); i++) {
                System.out.println(patientList.get(i).getPatientName());
            }
            printQueue(nextPatient.getPatientName());
            printNumber(patientList.size());
        }
    }

    // MODIFIES: this
    // EFFECTS: remove patient
    private void doRemovePatient() {
        if (patientQueue.getTotalNumberOfPatients() != 0) {
            patientQueue.removePatient();
            System.out.print("\nThis is the updated queue:\n");
        }

        doViewQueue();

    }

    // EFFECTS: saves the patient queue to file
    private void savePatientQueue() {
        try {
            jsonWriter.open();
            jsonWriter.write(patientQueue);
            jsonWriter.close();
            System.out.println("Saved " + patientQueue.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads patient queue from the file
    private void loadPatientQueue() {
        try {
            patientQueue = jsonReader.read();
            System.out.println("Loaded " + patientQueue.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void printQueue(String patientName) {
        System.out.printf("\nNext in queue: %s\n", patientName);
    }

    private void printNumber(int num) {
        System.out.printf("\nIn total there are %s number of patients in queue.\n", num);
    }
}
