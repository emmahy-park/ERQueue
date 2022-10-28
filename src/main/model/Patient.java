package model;

// Represents a patient

import org.json.JSONObject;
import persistence.Writable;

public class Patient implements Writable {
    private String patientName;
    private int patientAge;
    private String levelOfSeverity;
    private int waitTime;

    //EFFECTS: Construct a patient with associated information such as
    //         name, age, level of severity, wait time.
    public Patient(String name, int age, String los, int waitTime) {
        this.patientName = name;
        this.patientAge = age;
        this.waitTime = waitTime;

        if ((los.equals("Severe") || los.equals("A")) && (age <= 5 || age >= 80)) {
            this.levelOfSeverity = "A";
        } else if (los.equals("Severe") || los.equals("B")) {
            this.levelOfSeverity = "B";
        } else if ((los.equals("Moderate") || los.equals("C")) && (age <= 5 || age >= 80)) {
            this.levelOfSeverity = "C";
        } else if (los.equals("Moderate") || los.equals("D")) {
            this.levelOfSeverity = "D";
        } else if ((los.equals("Mild") || los.equals("E")) && (age <= 5 || age >= 80)) {
            this.levelOfSeverity = "E";
        } else if (los.equals("Mild") || los.equals("F")) {
            this.levelOfSeverity = "F";
        }
    }

    //EFFECTS: Get patient name
    public String getPatientName() {
        return patientName;
    }

    //EFFECTS: Get patient age
    public int getPatientAge() {
        return patientAge;
    }

    //EFFECTS: Get level of severity
    public String getLevelOfSeverity() {
        return levelOfSeverity;
    }

    //EFFECTS: Get patient wait time
    public int getWaitTime() {
        return waitTime;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", patientName);
        json.put("age", patientAge);
        json.put("los", levelOfSeverity);
        json.put("waitTime", waitTime);
        return json;
    }
}
