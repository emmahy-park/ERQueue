package model;

// Represents a queue having
// a patient name, age, level of severity, time of arrival, temperature, pulse, and blood pressure

import java.util.*;

public class PatientQueue {
    private List<Patient> patientList;
    private List<Patient> severePatients;
    private List<Patient> moderatePatients;
    private List<Patient> mildPatients;
    private List<Patient> updatedHighSeverePatients;
    private List<Patient> updatedHighModeratePatients;
    private List<Patient> updatedHighMildPatients;
    private List<Patient> updatedLowSeverePatients;
    private List<Patient> updatedLowModeratePatients;
    private List<Patient> updatedLowMildPatients;
    private List<Patient> updatedList;

    // EFFECTS: constructs an empty queue of patients
    public PatientQueue() {
        this.patientList = new ArrayList<>();
        this.severePatients = new ArrayList<>();
        this.moderatePatients = new ArrayList<>();
        this.mildPatients = new ArrayList<>();
        this.updatedHighSeverePatients = new ArrayList<>();
        this.updatedHighModeratePatients = new ArrayList<>();
        this.updatedHighMildPatients = new ArrayList<>();
        this.updatedLowSeverePatients = new ArrayList<>();
        this.updatedLowModeratePatients = new ArrayList<>();
        this.updatedLowMildPatients = new ArrayList<>();
        this.updatedList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new patient in the queue
    public void addPatient(Patient patient) {
        this.patientList.add(patient);
        if (isPatientSevere(patient)) {
            this.severePatients.add(patient);
        } else if (isPatientModerate(patient)) {
            this.moderatePatients.add(patient);
        } else if (isPatientMild(patient)) {
            this.mildPatients.add(patient);
        }
    }

    // EFFECTS: returns the sorted list of all patients in the queue
    public List<Patient> viewQueue() {
        for (Patient patient : this.patientList) {
            if (isPatientSevere(patient) && (patient.getAge() < 5 || patient.getAge() > 80)) {
                updatedHighSeverePatients.add(patient);
            } else if (isPatientSevere(patient) && (patient.getAge() >= 5 || patient.getAge() <= 80)) {
                updatedLowSeverePatients.add(patient);
            } else if (isPatientModerate(patient) && (patient.getAge() < 5 || patient.getAge() > 80)) {
                updatedHighModeratePatients.add(patient);
            } else if (isPatientModerate(patient) && (patient.getAge() >= 5 || patient.getAge() <= 80)) {
                updatedLowModeratePatients.add(patient);
            } else if (isPatientMild(patient) && (patient.getAge() < 5 || patient.getAge() > 80)) {
                updatedHighMildPatients.add(patient);
            } else if (isPatientMild(patient) && (patient.getAge() >= 5 || patient.getAge() <= 80)) {
                updatedLowMildPatients.add(patient);
            }
        }
        sortQueue();
        return updatedList;
    }

    // EFFECTS: sort the list of the patients according to their waiting time and append list
    public void sortQueue() {
        Collections.sort(updatedHighSeverePatients, Comparator.comparingInt(Patient::getWaitTime).reversed());
        Collections.sort(updatedLowSeverePatients, Comparator.comparingInt(Patient::getWaitTime).reversed());
        Collections.sort(updatedHighModeratePatients, Comparator.comparingInt(Patient::getWaitTime).reversed());
        Collections.sort(updatedLowModeratePatients, Comparator.comparingInt(Patient::getWaitTime).reversed());
        Collections.sort(updatedHighMildPatients, Comparator.comparingInt(Patient::getWaitTime).reversed());
        Collections.sort(updatedLowMildPatients, Comparator.comparingInt(Patient::getWaitTime).reversed());

        updatedList.addAll(updatedHighSeverePatients);
        updatedList.addAll(updatedLowSeverePatients);
        updatedList.addAll(updatedHighModeratePatients);
        updatedList.addAll(updatedLowModeratePatients);
        updatedList.addAll(updatedHighMildPatients);
        updatedList.addAll(updatedLowMildPatients);
    }

    // EFFECTS: determines whether the patient is in severe condition
    public boolean isPatientSevere(Patient patients) {
        return (patients.getLevelOfSeverity().equals("Severe"));
    }

    // EFFECTS: determines whether the patient is in moderate condition
    public boolean isPatientModerate(Patient patients) {
        return (patients.getLevelOfSeverity().equals("Moderate"));
    }

    // EFFECTS: determines whether the patient is in mild condition
    public boolean isPatientMild(Patient patients) {
        return (patients.getLevelOfSeverity().equals("Mild"));
    }

    // EFFECTS: returns the total number of patients in the queue
    public int getTotalNumberOfPatients() {
        return this.patientList.size();
    }

    // EFFECTS: returns the total number of patients in severe condition
    public int getTotalNumberOfSeverePatients() {
        return severePatients.size();
    }

    // EFFECTS: returns the total number of patients in moderate condition
    public int getTotalNumberOfModeratePatients() {
        return moderatePatients.size();
    }

    // EFFECTS: returns the total number of patients in mild condition
    public int getTotalNumberOfMildPatients() {
        return mildPatients.size();
    }

    // MODIFIES: patients
    // EFFECTS: Remove a patient from the queue
    public void removePatient() {
        updatedList.remove(0);
    }

    // EFFECTS: View the number of patients that are remaining in the queue
    public int remainingPatient() {
        return updatedList.size();
    }
}
