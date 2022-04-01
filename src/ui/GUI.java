package ui;


//import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import database.DatabaseConnectionHandler;
import model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;

public class GUI extends JFrame implements ActionListener {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 700;
    private String[] classDurations = {"all", "15", "30", "45", "60", "60+"};
    private String[] classCategories = {"all", "Yoga", "Cycling", "Personal", "Zumba"};
    private String[] classSizes = {"all", "1", "10", "30", "30+"};
    private String[] periods = {"all", "1 day", "3 days", "1 week", "1 month"};
    private String[] monthStrings = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    HashMap hm = new HashMap();



    private JComboBox<String> durBox;
    private JComboBox<String> catBox;
    private JComboBox<String> sizeBox;
    //private JComboBox<String> timeBox;
    private JTextField selectedDate;
    private JTextField classSize;
    private JTextField classDuration;
    private JTextField customerIDfield;
    private JPanel sumTab;
    private JPanel modTab;
    private JPanel basicPanel;
    private JPanel buttonPanel;
    private JPanel listPanel;
    private JTabbedPane tabs;
    private JTextField classCode;
    private JTable joinedClassPanel;
    private JFrame msg;

    private DatabaseConnectionHandler dbHandler;
    private JComboBox<String> updateCatBox;
    private JTextField classAddress;
    private JTextField empSIN;


    public GUI(DatabaseConnectionHandler dbHandler) {
        super("Gym API");
        this.dbHandler = dbHandler;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // initialization of panels
        monthMap();
        initInteractive();
        initList();
        initButtons();
        initModTab();
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void monthMap() {
        hm.put("January", 1);
        hm.put("February", 2);
        hm.put("March", 3);
        hm.put("April", 4);
        hm.put("May", 5);
        hm.put("June", 6);
        hm.put("July", 7);
        hm.put("August", 8);
        hm.put("September", 9);
        hm.put("October", 10);
        hm.put("November", 11);
        hm.put("December", 12);
    }

    private void initList() {
        listPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel outputTitle = new JLabel("RESULTS:", JLabel.LEFT);
        listPanel.add(outputTitle);
        // setup all tables and classes
        //add(listPanel);
        listPanel.setBounds(WIDTH / 2 + 10, 20, WIDTH / 2 - 100, HEIGHT - 100);
        listPanel.setVisible(true);
    }

    private void initInteractive() {
        sumTab = new JPanel();
        modTab = new JPanel();
        tabs = new JTabbedPane();
        tabs.setBounds(10, 20, WIDTH-40, HEIGHT - 100);
        //Make panel for each tab
        tabs.add("SUMMARY", sumTab);
        tabs.add("MODIFY", modTab);
        //Add tabs to the frame
        add(tabs);

    }

    private void initButtons() {
        buttonPanel = new JPanel(new GridLayout(0, 1, 1, 5));
        JButton allClasses = new JButton("SHOW ALL CLASSES");
        durBox = new JComboBox<>(classDurations);
        catBox = new JComboBox<>(classCategories);
        sizeBox = new JComboBox<>(classSizes);
        //timeBox = new JComboBox<>(periods);

        JButton filter = new JButton("FILTER CLASSES");
        customerIDfield = new JTextField(15);
        customerIDfield.setText("Customer ID to search");
        customerIDfield.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                JTextField source = (JTextField) e.getComponent();
                source.setText("");
                source.removeFocusListener(this);
            }
        });
        JButton classesTaken = new JButton("CLASSES TAKEN");
        JButton cusFreq = new JButton("CUSTOMER FREQUENCY");
        JButton locFreq = new JButton("LOCATION FREQUENCY");
        JButton locCov = new JButton("LOCATION COVERAGE");

        allClasses.setActionCommand("classes");
        allClasses.addActionListener(this);
        filter.setActionCommand("filter");
        filter.addActionListener(this);
        classesTaken.setActionCommand("taken");
        classesTaken.addActionListener(this);
        cusFreq.setActionCommand("cusFreq");
        cusFreq.addActionListener(this);
        locFreq.setActionCommand("locFreq");
        locFreq.addActionListener(this);
        locCov.setActionCommand("locCov");
        locCov.addActionListener(this);


        buttonPanel.add(new JLabel("SEARCH:"));
        //buttonPanel.add(new JLabel("Time Period:"));
        //buttonPanel.add(timeBox);
        buttonPanel.add(new JLabel("Duration:"));
        buttonPanel.add(durBox);
        buttonPanel.add(new JLabel("Category:"));
        buttonPanel.add(catBox);
        buttonPanel.add(new JLabel("Class Size:"));
        buttonPanel.add(sizeBox);
        buttonPanel.add(filter);
        buttonPanel.add(customerIDfield);
        buttonPanel.add(classesTaken);
        buttonPanel.add(new JLabel("SUMMARY INFORMATION:"));
        buttonPanel.add(allClasses);

        buttonPanel.add(cusFreq);
        buttonPanel.add(locFreq);
        buttonPanel.add(locCov);

        sumTab.add(buttonPanel);

    }

    private void initModTab(){
        basicPanel = new JPanel(new GridLayout(0, 2, 1, 5));
        classCode = new JTextField(12);
        basicPanel.add(new JLabel("Class Code: "));
        basicPanel.add(classCode);

        classAddress = new JTextField(12);
        basicPanel.add(new JLabel("Address: "));
        basicPanel.add(classAddress);

        empSIN = new JTextField(12);
        basicPanel.add(new JLabel("Employee SIN: "));
        basicPanel.add(empSIN);
        
        JButton setDate = new JButton("SET DATE");
        setDate.setActionCommand("date");
        setDate.addActionListener(this);
        selectedDate = new JTextField("Set Class Start Time", 12);
        selectedDate.setEditable(false);
        basicPanel.add(setDate);
        basicPanel.add(selectedDate);


        classSize = new JTextField(12);
        basicPanel.add(new JLabel("Class Size: "));
        basicPanel.add(classSize);

        classDuration = new JTextField(12);
        basicPanel.add(new JLabel("Class Duration: "));
        basicPanel.add(classDuration);
        updateCatBox = new JComboBox<>(Arrays.copyOfRange(classCategories, 1, classCategories.length));
        basicPanel.add(new JLabel("Category"));
        basicPanel.add(updateCatBox);
        JButton modify = new JButton("MODIFY");
        JButton delete = new JButton("DELETE");

        basicPanel.add(modify);
        basicPanel.add(delete);
        modify.setActionCommand("modify");
        modify.addActionListener(this);
        delete.setActionCommand("delete");
        delete.addActionListener(this);

        modTab.add(basicPanel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("filter")) {
            String durFilter = (String) durBox.getSelectedItem();
            String catFilter = (String) catBox.getSelectedItem();
            String sizeFilter = (String) sizeBox.getSelectedItem();
            ClassSession[] classes = dbHandler.selectClassSession(durFilter, catFilter, sizeFilter);
            displayClasses(classes);

        } else if (e.getActionCommand().equals("taken")) {
            if (customerIDfield.getText().equals("") || customerIDfield.getText().equals("Customer ID to search")) {
                msg = new JFrame();
                JOptionPane.showMessageDialog(msg, "ID not found");
            } else {
                ClassSession[] classes = dbHandler.getJoinInfo(Integer.parseInt(customerIDfield.getText()));
                displayClasses(classes);
            }
        } else if (e.getActionCommand().equals("cusFreq")) {
            TotalExerciseTime[] classes = dbHandler.aggregSignsUps(Integer.parseInt(customerIDfield.getText()));
            displayAggregation(classes);
        } else if (e.getActionCommand().equals("locFreq")) {
            ClassesPerLocation[] classesLocations = dbHandler.findNumClassesAllLocations();
            displayClassLocations(classesLocations);
        } else if (e.getActionCommand().equals("locCov")) {
            LocationAddress[] locationAddresses = dbHandler.findLocationsWithAllClassCategories();
            findLocationCoverage(locationAddresses);
        } else if (e.getActionCommand().equals("classes")) {
            ProjectionClass[] classes = dbHandler.projectAllClassSessions();
            displayProjection(classes);
        }  else if (e.getActionCommand().equals("date")) {
            dateFrame();
        }   else if (e.getActionCommand().equals("modify")) {
            ClassSession classSession = buildClassSession();
            if (dbHandler.classCodeExists(classSession)) {
                dbHandler.updateClassSession(classSession.getClass_code(), classSession.getStart_time());
                msg = new JFrame();
                JOptionPane.showMessageDialog(msg, "Class updated");
            } else {
                dbHandler.insertClassSession(classSession);
                msg = new JFrame();
                JOptionPane.showMessageDialog(msg, "Class inserted");
            }
        } else if (e.getActionCommand().equals("delete")) {
            ClassSession classSession = buildClassSession();
            if (dbHandler.classCodeExists(classSession)) {
                dbHandler.deleteClassSession(classSession.getClass_code());
                msg = new JFrame();
                JOptionPane.showMessageDialog(msg, "Class Deleted");
            } else {
                msg = new JFrame();
                JOptionPane.showMessageDialog(msg, "Class does not exist");
            }
        }
    }

    private ClassSession buildClassSession() {
        int class_code = Integer.parseInt(classCode.getText());
        String address = classAddress.getText();
        System.out.println("EMP SIN " + empSIN.getText());
        int SIN = (empSIN.getText().equals("") ? 0 : Integer.parseInt(empSIN.getText()));
        Timestamp time = (selectedDate.getText().equals("Set Class Start Time") ? new Timestamp(System.currentTimeMillis()): Timestamp.valueOf(selectedDate.getText()));
        String category = (String) updateCatBox.getSelectedItem();
        int duration = (classDuration.getText().equals("") ? 0 : Integer.parseInt(classDuration.getText()));
        int capacity = (classSize.getText().equals("") ? 0 : Integer.parseInt(classSize.getText()));
        ClassSession model = new ClassSession(class_code, address, SIN, time, category, duration, capacity);
        return model;
    }

    private void findLocationCoverage(LocationAddress[] classes) {
        if (classes.length == 0){
            msg = new JFrame();
            JOptionPane.showMessageDialog(msg, "No locations cover all class categories");
        } else {
            String[] columnNames = {"Addresses of gyms providing all class categories"};

            JFrame classesFrame = new JFrame("Found Locations");
            Object[][] data = new Object[classes.length][columnNames.length];
            for (int i = 0; i < classes.length; i++) {
                data[i][0] = classes[i].getAddress();
            }
            joinedClassPanel = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(joinedClassPanel);
            classesFrame.add(scrollPane);
            classesFrame.setSize(300, 400);
            classesFrame.setVisible(true);
        }
    }

    private void displayClassLocations(ClassesPerLocation[] classes) {
        if (classes.length == 0){
            msg = new JFrame();
            JOptionPane.showMessageDialog(msg, "No classes");
        } else {
            String[] columnNames = {"Gym", "Address", "Number of Classes"};

            JFrame classesFrame = new JFrame("Found Classes");
            Object[][] data = new Object[classes.length][columnNames.length];
            for (int i = 0; i < classes.length; i++) {
                data[i][0] = classes[i].getName();
                data[i][1] = classes[i].getAddress();
                data[i][2] = classes[i].getNumClasses();
            }
            joinedClassPanel = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(joinedClassPanel);
            classesFrame.add(scrollPane);
            classesFrame.setSize(500, 400);
            classesFrame.setVisible(true);
        }
    }

    private void displayAggregation(TotalExerciseTime[] classes) {
        System.out.println(classes[0].getCid());
        /*if (classes.length == 0){
            msg = new JFrame();
            JOptionPane.showMessageDialog(msg, "No classes");
        } else {
            String[] columnNames = {"Address", "Start Time", "Category", "Duration", "Size"};

            JFrame classesFrame = new JFrame("Found Classes");
            Object[][] data = new Object[classes.length][columnNames.length];
            for (int i = 0; i < classes.length; i++) {
                data[i][0] = classes[i].getAddress();
                data[i][1] = classes[i].getStart_time();
                data[i][2] = classes[i].getCategory();
                data[i][3] = classes[i].getDuration();
                data[i][4] = classes[i].getCapacity();
            }
            joinedClassPanel = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(joinedClassPanel);
            classesFrame.add(scrollPane);
            classesFrame.setSize(900, 400);
            classesFrame.setVisible(true);
        }*/
    }

    private void displayProjection(ProjectionClass[] projectionClasses) {
        if (projectionClasses.length == 0){
            msg = new JFrame();
            JOptionPane.showMessageDialog(msg, "No classes");
        } else {
            String[] columnNames = {"Address", "Start Time", "Category", "Duration", "Size"};

            JFrame classesFrame = new JFrame("Found Classes");
            Object[][] data = new Object[projectionClasses.length][columnNames.length];
            for (int i = 0; i < projectionClasses.length; i++) {
                data[i][0] = projectionClasses[i].getAddress();
                data[i][1] = projectionClasses[i].getStart_time();
                data[i][2] = projectionClasses[i].getCategory();
                data[i][3] = projectionClasses[i].getDuration();
                data[i][4] = projectionClasses[i].getCapacity();
            }
            joinedClassPanel = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(joinedClassPanel);
            classesFrame.add(scrollPane);
            classesFrame.setSize(900, 400);
            classesFrame.setVisible(true);
        }
    }

    private void displayClasses(ClassSession[] classSessions) {
        // put classes into new frame
        if (classSessions.length == 0){
            msg = new JFrame();
            JOptionPane.showMessageDialog(msg, "ID not found / No classes");
            //customerIDfield.setText("ID not found / No classes");
        } else {
            String[] columnNames = {"Class Code","Address", "SIN", "Start Time", "Category", "Duration", "Size"};

            JFrame classesFrame = new JFrame("Found Classes");
            Object[][] data = new Object[classSessions.length][columnNames.length];
            for (int i = 0; i < classSessions.length; i++) {
                data[i][0] = classSessions[i].getClass_code();
                data[i][1] = classSessions[i].getAddress();
                data[i][2] = classSessions[i].getSIN();
                data[i][3] = classSessions[i].getStart_time();
                data[i][4] = classSessions[i].getCategory();
                data[i][5] = classSessions[i].getDuration();
                data[i][6] = classSessions[i].getCapacity();
            }
            joinedClassPanel = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(joinedClassPanel);
            classesFrame.add(scrollPane);
            classesFrame.setSize(900, 400);
            classesFrame.setVisible(true);
        }
    }

    private void dateFrame() {
        JFrame dateFrame = new JFrame("Date Selection");
        dateFrame.setLayout(new GridLayout(0, 2, 1, 5));
        dateFrame.setSize(300, 300);
        String[] daysList = new String[31];
        for (int i = 1; i < 32; i++){
            daysList[i-1] = String.valueOf(i);
        }

        String[] years = new String[102];
        for (int i = 2000; i < 2101; i++){
            years[i-2000] = String.valueOf(i);
        }
        JComboBox<String> day = new JComboBox<String>(daysList);
        dateFrame.add(new JLabel("Day"));
        dateFrame.add(day);
       JComboBox<String> months = new JComboBox<>(monthStrings);
        dateFrame.add(new JLabel("Month"));
        dateFrame.add(months);
        JComboBox<String> year = new JComboBox<String>(years);
        dateFrame.add(new JLabel("Year"));
        dateFrame.add(year);


        String[] hours = new String[24];
        for (int i = 0; i < 24; i++){
            hours[i] = String.valueOf(i);
        }
        JComboBox<String> hour = new JComboBox<String>(hours);
        dateFrame.add(new JLabel("Hour"));
        dateFrame.add(hour);

        String[] minutes = new String[60];
        for (int i = 0; i < 10; i++){
            minutes[i] = "0" + String.valueOf(i);
        }
        for (int i = 10; i < 60; i++){
            minutes[i] = String.valueOf(i);
        }
        JComboBox<String> minute = new JComboBox<String>(minutes);
        dateFrame.add(new JLabel("Minute"));
        dateFrame.add(minute);
        JButton finish = new JButton("OK");
        finish.addActionListener(e -> {
            // set current date in boxes to the output of the other text field
            String selDay = (String) day.getSelectedItem();
            String selMonth = (String) months.getSelectedItem();
            int monthNum = (int) hm.get(selMonth);
            String selYear = (String) year.getSelectedItem();
            String selHour = (String) hour.getSelectedItem();
            String selMinute = (String) minute.getSelectedItem();
            selectedDate.setText(String.format("%s-%s-%s %s:%s:00", selYear, monthNum, selDay, selHour, selMinute));
            dateFrame.dispose();
        });
        dateFrame.add(finish);
        dateFrame.setVisible(true);
    }
}
