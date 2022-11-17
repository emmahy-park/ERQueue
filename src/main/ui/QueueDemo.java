package ui;

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

    public QueueDemo() {
        pq = new PatientQueue("Patient Queue");
        Patient patient1 = new Patient("Emma", 27, "Mild", 30);
        Patient patient2 = new Patient("Ben", 72, "Moderate", 30);
        Patient patient3 = new Patient("Jenny", 27, "Severe", 30);
        pq.addPatient(patient1);
        pq.addPatient(patient2);
        pq.addPatient(patient3);

        //where is this title shown???
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

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void viewPatientQueue() {
        listModel.removeAllElements();

        for (int x = 0; x < pq.getTotalNumberOfPatients(); x++) {
            listModel.addElement(pq.getPatientName(x));
        }
    }

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu queueMenu = new JMenu("Menu");
        addMenuItem(queueMenu, new LoadQueueAction(), null);
        addMenuItem(queueMenu, new SaveQueueAction(), null);
        addMenuItem(queueMenu, new ClearQueueAction(), null);
        menuBar.add(queueMenu);

        setJMenuBar(menuBar);
    }

    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

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

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
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

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = QueueDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private class LoadQueueAction extends AbstractAction {
        JsonReader jsonReader;
        PatientQueue newPatientQueue;
        private static final String JSON_STORE = "./data/patientQueue.json";

        LoadQueueAction() {
            super("Load Queue");
        }

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

    private class SaveQueueAction extends AbstractAction {
        JsonWriter jsonWriter;
        private static final String JSON_STORE = "./data/patientQueue.json";

        SaveQueueAction() {
            super("Save Queue");
        }

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

    private class ClearQueueAction extends AbstractAction {
        ClearQueueAction() {
            super("Clear Queue");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            listModel.removeAllElements();
        }
    }

    private class AddPatientAction extends AbstractAction {

        public AddPatientAction() {
            super("Add Patient");
        }

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

    private class RemovePatientAction extends AbstractAction {

        public RemovePatientAction() {
            super("Remove Patient");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
        }
    }

    private class NextPatientAction extends AbstractAction {

        NextPatientAction() {
            super("Next Patient");
        }

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

    private class NextPatient extends JPanel {
        public JPanel createUI() {
            JPanel panel = new JPanel();
            JLabel intro = new JLabel("Next Patient:");
            JLabel name = new JLabel(listModel.getElementAt(0).toString());
            intro.setLabelFor(name);

            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
            intro.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            name.setAlignmentX(JComponent.CENTER_ALIGNMENT);

            panel.add(intro);
            panel.add(Box.createVerticalStrut(5));
            panel.add(name);

            return panel;
        }
    }

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
            SpringUtilities.makeCompactGrid(panel, labelStrings.length, 2, 10, 10, 10, 5);
            return panel;
        }

        public String[] getLosStrings() {
            String[] los = {
                    "Mild",
                    "Moderate",
                    "Severe",
            };
            return los;
        }

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

    public static void main(String[] args) {
        new QueueDemo();
    }

}
