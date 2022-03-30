package controller;

import database.DatabaseConnectionHandler;
import delegates.LoginWindowDelegate;
import delegates.TerminalTransactionsDelegate;
import delegates.UITransactionsDelegate;
import model.ClassSession;
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
        new GUI(dbHandler);

        //loginWindow = new LoginWindow();
        //loginWindow.showFrame(this);

    }

    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            loginWindow.dispose();

//            //TODO: stop here and use gui when ready
            new GUI(dbHandler);
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
    public void insertClassSession(ClassSession model) {
        dbHandler.insertClassSession(model);
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
    public void showClassSessionTerminal() {
        //TODO
        ClassSession[] models = dbHandler.getGymInfo();
    }

    /**
     * Displays information about various class sessions on the UI
     */
    public void showClassSessionUI() {
        //TODO
        ClassSession[] models = dbHandler.getGymInfo();
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
