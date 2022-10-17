package model;

public class Patient {
    private String name;
    private int age;
    private String levelOfSeverity;
    private int waitTime;


    // EFFECTS: construct a patient with associated information such as
    // name, age, level of severity, wait time, temperature, pulse, and blood pressure and status
    public Patient(String patientName, int patientAge, String levelOfSeverity, int waitTime) {
        this.name = patientName;
        this.age = patientAge;
        this.levelOfSeverity = levelOfSeverity;
        this.waitTime = waitTime;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getLevelOfSeverity() {
        return levelOfSeverity;
    }

    public int getWaitTime() {
        return waitTime;
    }
}
