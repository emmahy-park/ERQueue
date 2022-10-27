package model;

//Represents a patient queue

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class PatientQueue implements Writable {
    private PriorityQueue<Patient> patientQueue;
    private ArrayList<String> patientList;
    private String patient;
    private String name;
    private List<Patient> patients;

    //EFFECTS: Constructs patient queue with a name and empty queue of patients
    public PatientQueue(String name) {
        this.patientQueue = new PriorityQueue<>(new SortByLosAndWaitTime());
        this.name = name;
        patients = new ArrayList<>();
    }

    //EFFECTS: Get name of the queue
    public String getName() {
        return name;
    }

    //MODIFIES: this
    //EFFECTS: Adds a new patient in the queue
    public void addPatient(Patient patient) {
        patientQueue.add(patient);
        patients.add(patient);
    }

    //EFFECTS: Get total number of patients in the queue
    public int getTotalNumberOfPatients() {
        return patientQueue.size();
    }

    //REQUIRES: the queue should not be empty
    //MODIFIES: this
    //EFFECTS: Remove a patient first in the queue
    public void removePatient() {
        patientQueue.remove();
    }

    public ArrayList<String> viewQueue() {
        patientList = new ArrayList<>();
        while (!patientQueue.isEmpty()) {
            patient = patientQueue.poll().getPatientName();
            patientList.add(patient);
        }
        return patientList;
    }

    //EFFECTS: returns an unmodifiable list of patients in this workroom
    public List<Patient> getPatients() {
        return Collections.unmodifiableList(patients);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("patients", patientsToJson());
        return json;
    }

    // EFFECTS: returns patients in this patient queue as a JSON array
    private JSONArray patientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Patient p : patientQueue) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
