package persistence;

import model.Patient;
import model.PatientQueue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import static java.lang.Integer.valueOf;

// Represents a reader that reads patient queue from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads patient queue from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PatientQueue read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePatientQueue(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses patient queue from JSON object and returns it
    private PatientQueue parsePatientQueue(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        PatientQueue patientQueue = new PatientQueue(name);
        addPatients(patientQueue, jsonObject);
        return patientQueue;
    }

    // MODIFIES: patientQueue
    // EFFECTS: parses patients from JSON object and adds them to patient queue
    private void addPatients(PatientQueue patientQueue, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("patient");
        for (Object json : jsonArray) {
            JSONObject nextPatient = (JSONObject) json;
            addPatient(patientQueue, nextPatient);
        }
    }

    // MODIFIES: patient queue
    // EFFECTS: parses patient from JSON object and adds it to patient queue
    private void addPatient(PatientQueue patientQueue, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int age = jsonObject.getInt("age");
        String los = jsonObject.getString("los");
        int waitTime = jsonObject.getInt("waitTime");
        Patient patient = new Patient(name, age, los, waitTime);
        patientQueue.addPatient(patient);
    }
}
