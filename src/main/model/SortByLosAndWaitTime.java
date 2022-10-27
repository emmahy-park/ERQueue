package model;

import java.util.Comparator;

public class SortByLosAndWaitTime implements Comparator<Patient> {
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
}

