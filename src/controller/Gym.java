package controller;

import database.DatabaseConnectionHandler;
import delegates.LoginWindowDelegate;
import delegates.TerminalTransactionsDelegate;
import delegates.UITransactionsDelegate;
import model.*;
import ui.GUI;
import ui.LoginWindow;
//import ui.TerminalTransactions;

import java.sql.Timestamp;

/**
 * Controller class that starts everything, based off CPSC304 Bank example
 */
public class Gym implements LoginWindowDelegate, TerminalTransactionsDelegate, UITransactionsDelegate {
    private DatabaseConnectionHandler dbHandler;
    private LoginWindow loginWindow = null;

    public Gym() {
        dbHandler = new DatabaseConnectionHandler();
    }

    private void start() {

        // temporary
        //new GUI(dbHandler);

        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);

    }

    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            loginWindow.dispose();




//            //TODO: stop here and use gui when ready
            new GUI(dbHandler);
            this.printResults();
            this.printProjections();
            this.printAggregates(20);
//            TerminalTransactions transactions = new TerminalTransactions();
//            transactions.setupDatabase(this);
//            transactions.showMainMenu(this);
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have reached your maximum number of login attempts.");
                System.exit(-1);
            }
        }
    }

    /**
     * Insert a class session with given info
     */
    public void insertClassSession(ClassSession classSession) {
        dbHandler.insertClassSession(classSession);
    }

    /**
     * Delete a class session with given class code
     */
    public void deleteClassSession(int class_code) {
        dbHandler.deleteClassSession(class_code);
    }

    /**
     * Update the class session time for given class code
     */
    public void updateClassSession(int class_code, Timestamp start_time) {
        dbHandler.updateClassSession(class_code, start_time);
    }

    /**
     * Displays information about various class sessions on the terminal
     */
    public void printResults() {
        //TODO
//        insertClassSession(new ClassSession(420, "2891 Laurel St", 862245315,
//                new Timestamp(System.currentTimeMillis()), "Yoga", 45, 10));
        // deleteClassSession(420);
        // updateClassSession(441, new Timestamp(System.currentTimeMillis()));
        ClassSession[] classSessions = dbHandler.selectClassSession("60", "Cycling", "25");


        System.out.printf("%-15.15s", "class code");
        System.out.printf("%-35.35s", "address");
        System.out.printf("%-15.15s", "SIN");
        System.out.printf("%-25.25s", "start time");
        System.out.printf("%-15.15s", "category");
        System.out.printf("%-15.15s", "duration");
        System.out.printf("%-15.15s", "capacity");
        System.out.println();

        for (int i = 0; i < classSessions.length; i++) {
            ClassSession classSession = classSessions[i];


            // simplified output formatting; truncation may occur
            System.out.printf("%-15.15s", classSession.getClass_code());
            if (classSession.getAddress() == null) {
                System.out.printf("%-35.35s", " ");
            } else {
                System.out.printf("%-35.35s", classSession.getAddress());
            }
            System.out.printf("%-15.15s", classSession.getSIN());
            if (classSession.getStart_time().toString() == "") {
                System.out.printf("%-25.25s", " ");
            } else {
                System.out.printf("%-25.25s", classSession.getStart_time().toString());
            }
            System.out.printf("%-15.15s", classSession.getCategory());
            System.out.printf("%-15.15s", classSession.getDuration());
            System.out.printf("%-15.15s", classSession.getCapacity());
            System.out.println();
        }
    }

    public void printProjections() {
        ProjectionClass[] ps = dbHandler.projectAllClassSessions();

        System.out.printf("%-35.35s", "address");
        System.out.printf("%-25.25s", "start time");
        System.out.printf("%-15.15s", "category");
        System.out.printf("%-15.15s", "duration");
        System.out.printf("%-15.15s", "capacity");
        System.out.println();

        for (int i = 0; i < ps.length; i++) {
            ProjectionClass cs = ps[i];


            // simplified output formatting; truncation may occur
            if (cs.getAddress() == null) {
                System.out.printf("%-35.35s", " ");
            } else {
                System.out.printf("%-35.35s", cs.getAddress());
            }
            if (cs.getStart_time().toString() == "") {
                System.out.printf("%-25.25s", " ");
            } else {
                System.out.printf("%-25.25s", cs.getStart_time().toString());
            }
            System.out.printf("%-15.15s", cs.getCategory());
            System.out.printf("%-15.15s", cs.getDuration());
            System.out.printf("%-15.15s", cs.getCapacity());
            System.out.println();
        }
    }

    public void printAggregates(int cid) {
        TotalExerciseTime[] as = dbHandler.aggregSignsUps(cid);

        System.out.printf("%-15.15s", "cid");
        System.out.printf("%-15.15s", "num_classes");
        System.out.println();

        for (int i = 0; i < as.length; i++) {
            TotalExerciseTime a = as[i];

            System.out.printf("%-15.15s", a.getCid());
            System.out.printf("%-15.15s", a.getNumClasses());
            System.out.println();
        }
    }
    /**
     * Displays information about various class sessions on the UI
     */
    public void showClassSessionUI() {
        //TODO
        ClassSession[] classSessions = dbHandler.getGymInfo();
    }

    @Override
    public void transactionsFinishedTerminal() {
        transactionsFinished();
    }



    @Override
    public void transactionsFinishedUI() {
        transactionsFinished();
    }

    /**
     * Clean up transaction connection.
     *
     * May be able to use for both terminal and ui???
     */
    private void transactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }

    /**
     * Drops any existing tables corresponding to ClassSession and creates a new one for this project
     *
     * Do we need one for each of termminal/ui???
     */
    public void databaseSetup() {
        dbHandler.databaseSetup();
    }

    /**
     * Main method at launch
     */
    public static void main(String[] args) {
        Gym gym = new Gym();
        gym.start();
    }
}
