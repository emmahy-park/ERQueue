# CPSC 210 Personal Project

## Emergency Room Queue Management 

ER Queue Management is a Java desktop application that has a graphical user interface
that triages the patients in the Emergency Room. In Metro Vancouver, we often face unexpectedly long waits in the Emergency Room.
To minimize the wait time and the workforce prioritizing patients, this application prioritizes and outputs the estimated wait time and patient queue.

It lets the user (triage nurse) to enter information about the patient (Name, Age, Level of Severity, Time of Arrival, Temperature, Pulse, Blood Pressure).
Possible features include estimated wait time; order in queue; emergency updates (symptoms, X-ray results, lab results); Nurse/Doctor's treatment start time.

The triage nurse will enter and update the information.
The patients and guardians waiting in the Emergency Room can see the outputs on the screen.
Also, the patients searching for an Emergency Room can see the estimated wait time on a website.

## User Stories
- As a user, I want to be able to view the list of patients in the queue.
- As a user, I want to be able to add a patient to the queue.
- As a user, I want to be able to remove a patient from the queue.
- As a user, I want to be able to see the number of patients that are in the queue.
- As a user, I want to be able to see the next patient (first patient) in the queue.
- As a user, I want to be able to save my patient queue to file.
- As a user, I want to be able to load my patient queue from file.

## Instructions for Grader
- You can generate the first required event related to adding new patient to the patient queue by clicking "Add Patient".
- You can generate the second required event related to removing new patient from the patient queue by clicking "Remove Patient".
- You can generate the third required event related to displaying the next patient in line and total patient number by clicking "Next Patient".
- You can locate my visual component (button icon for Add button) by clicking 'Add Patient'.
- You can save the state of my application by clicking 'Save Queue' in the Menu.
- You can reload the state of my application by clicking 'Load Queue' in the Menu.
