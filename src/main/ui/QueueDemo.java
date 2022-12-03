package ui;

import model.Event;
import model.EventLog;
import model.Patient;
import model.PatientQueue;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class QueueDemo extends JFrame implements ListSelectionListener, ActionListener {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private PatientQueue pq;
    private JInternalFrame controlPanel;
    private JList list;
    private DefaultListModel  listModel = new DefaultListModel();
    private JButton button;
    private JButton newButton;
    private JButton removeButton;
    private JButton nextButton;

    //comments
    public QueueDemo() {
        pq = new PatientQueue("Patient Queue");
        Patient patient1 = new Patient("Emma", 27, "Mild", 30);
        Patient patient2 = new Patient("Ben", 72, "Moderate", 30);
        Patient patient3 = new Patient("Jenny", 27, "Severe", 30);
        pq.addPatient(patient1);
        pq.addPatient(patient2);
        pq.addPatient(patient3);

        controlPanel = new JInternalFrame("Patient Queue", false, false, false, false);
        controlPanel.setLayout(new BorderLayout());

        setTitle("Patient Queue Simulator");
        setSize(WIDTH, HEIGHT);

        viewPatientQueue();
        addButtons();
        addMenu();

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(list);
        add(listScrollPane);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);

        printLog();
    }

    private void printLog() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.getDate());
                    System.out.println(event.getDescription());
                }
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: resets listModel, then add patients' names in listModel.
    private void viewPatientQueue() {
        listModel.removeAllElements();

        for (int x = 0; x < pq.getTotalNumberOfPatients(); x++) {
            listModel.addElement(pq.getPatientName(x));
        }
    }

    // EFFECTS: Adds menu bar
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu queueMenu = new JMenu("Menu");
        addMenuItem(queueMenu, new LoadQueueAction(), null);
        addMenuItem(queueMenu, new SaveQueueAction(), null);
        addMenuItem(queueMenu, new ClearQueueAction(), null);
        menuBar.add(queueMenu);

        setJMenuBar(menuBar);
    }

    // EFFECTS: Adds on item with given handler to the given menu
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    // EFFECTS: Add buttons
    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));

        newButton = new JButton(new AddPatientAction());
        buttonPanel.add(newButton);
        newButton.setToolTipText("Click this button to add new patient.");

        removeButton = new JButton(new RemovePatientAction());
        buttonPanel.add(removeButton);
        removeButton.setToolTipText("Click this button to remove patient.");

        nextButton = new JButton(new NextPatientAction());
        buttonPanel.add(nextButton);
        nextButton.setToolTipText("Click this button to view next patient in queue.");

        add(buttonPanel, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: Disables the remove button when a patient is not selected
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable remove button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the buttons.
                newButton.setEnabled(true);
                removeButton.setEnabled(true);
                nextButton.setEnabled(true);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //ignore
    }

    // REQUIRES: Valid image path
    // EFFECTS: Returns an ImageIcon, or null if the path is invalid
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = QueueDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    //comments
    private class LoadQueueAction extends AbstractAction {
        JsonReader jsonReader;
        PatientQueue newPatientQueue;
        private static final String JSON_STORE = "./data/patientQueue.json";

        LoadQueueAction() {
            super("Load Queue");
        }

        // EFFECTS: Reads patient queue
        @Override
        public void actionPerformed(ActionEvent e) {
            jsonReader = new JsonReader(JSON_STORE);
            try {
                newPatientQueue = jsonReader.read();
                pq = newPatientQueue;
                viewPatientQueue();
            } catch (IOException exception) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    //comments
    private class SaveQueueAction extends AbstractAction {
        JsonWriter jsonWriter;
        private static final String JSON_STORE = "./data/patientQueue.json";

        SaveQueueAction() {
            super("Save Queue");
        }

        // EFFECTS: Write patient queue
        @Override
        public void actionPerformed(ActionEvent e) {
            jsonWriter = new JsonWriter(JSON_STORE);
            try {
                jsonWriter.open();
                jsonWriter.write(pq);
                jsonWriter.close();
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    //comments
    private class ClearQueueAction extends AbstractAction {
        ClearQueueAction() {
            super("Clear Queue");
        }

        // MODIFIES: listMode, pq
        // EFFECTS: Removes all elements in listModel and patient queue
        @Override
        public void actionPerformed(ActionEvent e) {
            listModel.removeAllElements();

            EventLog.getInstance().logEvent(new Event("Cleared Queue"));

            pq = new PatientQueue("Patient Queue");
        }
    }

    //comments
    private class AddPatientAction extends AbstractAction {

        public AddPatientAction() {
            super("Add Patient");
        }

        // EFFECTS: Create and set up the window
        //          Add text field contents and display
        @Override
        public void actionPerformed(ActionEvent e) {
            //Create and set up the window.
            JFrame frame = new JFrame("Add Patient");

            //Add contents to the window.
            frame.add(new TextInput());

            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }
    }

    //comments
    private class RemovePatientAction extends AbstractAction {

        public RemovePatientAction() {
            super("Remove Patient");
        }

        // MODIFIES: listModel, pq
        // EFFECTS: Remove selected patient from listModel and patent queue
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
            pq.removePatient(index);
        }
    }

    //comments
    private class NextPatientAction extends AbstractAction {

        NextPatientAction() {
            super("Next Patient");
        }

        // EFFECTS: Create and set up the window
        //          Add contents and display
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Next Patient");

            //Add contents to the window.
            frame.add(new NextPatient().createUI());

            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }
    }

    //comments
    private class NextPatient extends JPanel {

        // REQUIRES: at least one patient in patient queue
        // EFFECTS: Creates a panel that displays the next patient's name and total number of patients in queue
        public JPanel createUI() {
            JPanel panel = new JPanel();
            JLabel introPatient = new JLabel("Next Patient:");
            JLabel introNum = new JLabel("Number of patients in queue: ");
            JLabel name = new JLabel(listModel.getElementAt(0).toString());
            JLabel num = new JLabel(Integer.toString(listModel.size()));
            introPatient.setLabelFor(name);
            introNum.setLabelFor(num);

            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            introPatient.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            name.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            introNum.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            name.setAlignmentX(JComponent.CENTER_ALIGNMENT);

            panel.add(introPatient);
            panel.add(Box.createVerticalStrut(5));
            panel.add(name);
            panel.add(Box.createVerticalStrut(10));
            panel.add(introNum);
            panel.add(Box.createVerticalStrut(5));
            panel.add(num);

            return panel;
        }
    }

    //comments
    private class TextInput extends JPanel implements ActionListener, FocusListener {

        JTextField patientName;
        JTextField patientAge;
        JSpinner los;
        JTextField waitTime;
        boolean addPatient = false;

        public TextInput() {
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

            JPanel entryPanel = new JPanel() {
                public Dimension getMaximumSize() {
                    Dimension pref = getPreferredSize();
                    return new Dimension(Integer.MAX_VALUE, pref.height);
                }
            };
            entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.PAGE_AXIS));
            entryPanel.add(createEntryField());
            entryPanel.add(createButtons());
            add(entryPanel);
        }

        // EFFECTS: displays a button icon next to "Add"
        protected JComponent createButtons() {
            ImageIcon leftButtonIcon = createImageIcon("images/right.gif");
            JPanel buttonPanel = new JPanel(new FlowLayout((FlowLayout.TRAILING)));
            button = new JButton("Add", leftButtonIcon);
            button.addActionListener(this);
            buttonPanel.add(button);

            buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));

            return buttonPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if ("clear".equals(e.getActionCommand())) {
                addPatient = false;
                patientName.setText("");
                patientAge.setText("");
                waitTime.setText("");
            } else {
                addPatient = true;
            }
            updateDisplays();
        }

        // EFFECTS: updates patient queue when patient is added to the queue and resets the text field
        protected void updateDisplays() {
            if (addPatient) {
                Patient newPatient = new Patient(patientName.getText(), Integer.parseInt(patientAge.getText()),
                        (String)los.getValue(), Integer.parseInt(waitTime.getText()));
                pq.addPatient(newPatient);


                viewPatientQueue();

                //Reset the text field
                patientName.setText("");
                patientAge.setText("");
                waitTime.setText("");
            }
        }

        // EFFECTS: create entry field to add patients
        protected JComponent createEntryField() {
            JPanel panel = new JPanel(new SpringLayout());
            String[] labelStrings = {"Patient Name: ", "Patient Age: ", "Level of Severity (Mild/Moderate/Severe): ",
                    "Wait Time (minutes): "};

            JComponent[] fields = new JComponent[labelStrings.length];
            int fieldNum = 0;

            patientName = new JTextField(20);
            fields[fieldNum++] = patientName;

            patientAge = new JTextField(20);
            fields[fieldNum++] = patientAge;

            String[] losStrings = getLosStrings();
            los = new JSpinner(new SpinnerListModel(losStrings));
            fields[fieldNum++] = los;

            waitTime = new JTextField(20);
            fields[fieldNum++] = waitTime;

            JLabel[] labels = new JLabel[labelStrings.length];

            entryFieldForLoop(labelStrings, labels, panel, fields);

            SpringUtilities.makeCompactGrid(panel, labelStrings.length, 2, 10, 10, 10, 5);
            return panel;
        }

        //comments
        private void entryFieldForLoop(String[] labelStrings, JLabel[] labels, JPanel panel, JComponent[] fields) {
            for (int i = 0; i < labelStrings.length; i++) {
                labels[i] = new JLabel(labelStrings[i], JLabel.TRAILING);
                labels[i].setLabelFor(fields[i]);
                panel.add(labels[i]);
                panel.add(fields[i]);

                JTextField tf;
                if (fields[i] instanceof JSpinner) {
                    tf = getTextField((JSpinner) fields[i]);
                } else {
                    tf = (JTextField) fields[i];
                }
                tf.addActionListener(this);
                tf.addFocusListener(this);
            }
        }

        //comments
        public String[] getLosStrings() {
            String[] los = {
                    "Mild",
                    "Moderate",
                    "Severe",
            };
            return los;
        }

        //comments
        public JFormattedTextField getTextField(JSpinner spinner) {
            JComponent editor = spinner.getEditor();
            if (editor instanceof JSpinner.DefaultEditor) {
                return ((JSpinner.DefaultEditor) editor).getTextField();
            } else {
                System.err.println("Unexpected editor type: " + spinner.getEditor().getClass()
                        + " isn't a descendant of DefaultEditor");
                return null;
            }
        }

        @Override
        public void focusGained(FocusEvent e) {
            Component c = e.getComponent();
            if (c instanceof  JFormattedTextField) {
                selectItLater(c);
            } else if (c instanceof JTextField) {
                ((JTextField)c).selectAll();
            }
        }

        protected void selectItLater(Component c) {
            if (c instanceof JFormattedTextField) {
                final JFormattedTextField ftf = (JFormattedTextField)c;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ftf.selectAll();
                    }
                });
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            //ignore
        }
    }

    // starts the application
    public static void main(String[] args) {
        new QueueDemo();
    }

}
