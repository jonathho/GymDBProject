package ui;

import controller.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class GUI extends JFrame implements ActionListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;
    private String[] classDurations = {"all", "15", "30", "45", "60", "60+"};
    private String[] classCategories = {"all", "Yoga", "Cycling", "Private", "Zumba"};
    private String[] classSizes = {"all", "1", "10", "30", "30+"};
    private String[] periods = {"all", "1 day", "3 days", "1 week", "1 month"};

    private JComboBox<String> durBox;
    private JComboBox<String> catBox;
    private JComboBox<String> sizeBox;
    private JComboBox<String> timeBox;
    private JComboBox<String> startBox;
    private JTextField selectedDate;
    private JFrame dateFrame;
    private JPanel sumTab;
    private JPanel modTab;
    private JPanel basicPanel;
    private JPanel buttonPanel;
    private JPanel listPanel;
    private JTabbedPane tabs;


    public GUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // initialization of panels
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

    private void initList() {
        listPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel outputTitle = new JLabel("RESULTS:", JLabel.LEFT);
        listPanel.add(outputTitle);
        // setup all tables and classes

        add(listPanel);
        listPanel.setBounds(WIDTH / 2 + 10, 20, WIDTH / 2 - 100, HEIGHT - 100);
        listPanel.setVisible(true);
    }

    private void initInteractive() {
        sumTab = new JPanel();
        modTab = new JPanel();
        tabs = new JTabbedPane();
        tabs.setBounds(40, 20, WIDTH / 2 - 100, HEIGHT - 100);
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
        timeBox = new JComboBox<>(periods);

        JButton filter = new JButton("FILTER CLASSES");
        JTextField customerIDfield = new JTextField(15);
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
        buttonPanel.add(new JLabel("Time Period:"));
        buttonPanel.add(timeBox);
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
        JTextField classCode = new JTextField(15);
        JTextField size = new JTextField(15);

        JButton setTime = new JButton("SET TIME");
        JButton setDate = new JButton("SET DATE");
        setDate.setActionCommand("date");
        setDate.addActionListener(this);

        selectedDate = new JTextField("Select Date", 12);
        selectedDate.setEditable(false);
        basicPanel.add(selectedDate);
        basicPanel.add(setDate);


        modTab.add(basicPanel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("filter")) {
            String durFilter = (String) durBox.getSelectedItem();
            String catFilter = (String) catBox.getSelectedItem();
            String sizeFilter = (String) sizeBox.getSelectedItem();
            System.out.println("FILTER PRESSED " + durFilter + " " + catFilter + " " + sizeFilter);
        } else if (e.getActionCommand().equals("taken")) {
            System.out.println("TAKEN PRESSED");
        } else if (e.getActionCommand().equals("cusFreq")) {
            System.out.println("CUSTOMER FREQUENCY PRESSED");
        } else if (e.getActionCommand().equals("locFreq")) {
            System.out.println("LOCATION FREQUENCY PRESSED");
        } else if (e.getActionCommand().equals("locCov")) {
            System.out.println("LOCATION COVERAGE PRESSED");
        } else if (e.getActionCommand().equals("classes")) {
            System.out.println("ALL CLASSES PRESSED");
        }  else if (e.getActionCommand().equals("date")) {
            dateFrame();
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
        String[] monthStrings = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
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
        for (int i = 0; i < 60; i++){
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
            String selYear = (String) year.getSelectedItem();
            String selHour = (String) hour.getSelectedItem();
            String selMinute = (String) minute.getSelectedItem();
            selectedDate.setText(String.format("%s %s, %s, %s:%s", selMonth, selDay, selYear, selHour, selMinute));
            dateFrame.dispose();
        });
        dateFrame.add(finish);
        dateFrame.setVisible(true);
    }

}
