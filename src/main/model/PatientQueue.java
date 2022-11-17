package model;

//Represents a patient queue

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

public class PatientQueue implements Writable {
    private PriorityQueue<Patient> patientQueue;
    private ArrayList<Patient> patientList;
    private String name;
    private Patient patient;


    //EFFECTS: Constructs patient queue with a name and empty queue of patients
    public PatientQueue(String name) {
        this.patientQueue = new PriorityQueue<>(new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                //Comparing patients
                int losCompare = p1.getLevelOfSeverity().compareTo(p2.getLevelOfSeverity());
                int waitTimeCompare = (p2.getWaitTime() - p1.getWaitTime());

                //2nd level comparison
                if (losCompare == 0) {
                    return waitTimeCompare;
                } else {
                    return losCompare;
                }
            }
        });
        this.name = name;
    }

    //EFFECTS: Get name of the queue
    public String getName() {
        return name;
    }

    //MODIFIES: this
    //EFFECTS: Adds a new patient in the queue
    public void addPatient(Patient patient) {
        patientQueue.add(patient);
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

    public ArrayList<Patient> viewQueue() {
        patientList = new ArrayList<>();
        while (!patientQueue.isEmpty()) {
            patient = patientQueue.poll();
            patientList.add(patient);
        }
        int i;
        for (i = 0; i < patientList.size(); i++) {
            patientQueue.add(patientList.get(i));
        }

        return patientList;
    }

    public String getPatientName(int index) {

        patientList = new ArrayList<>();
        while (!patientQueue.isEmpty()) {
            patient = patientQueue.poll();
            patientList.add(patient);
        }
        int i;
        for (i = 0; i < patientList.size(); i++) {
            patientQueue.add(patientList.get(i));
        }

        return patientList.get(index).getPatientName();
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
