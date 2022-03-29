package ui;

import delegates.TerminalTransactionsDelegate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Handles terminal transactions, for testing until GUI is ready
 *
 * Heavily based off of CPSC304 Bank demo
 */
public class TerminalTransactions {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;

    private BufferedReader bufferedReader = null;
    private TerminalTransactionsDelegate delegate = null;

    public TerminalTransactions() {}

    /**
     * TODO:
     * Sets up database with ClassSession table with ___ tuples for manipulation
     * Don't care if we already have tables, just drop and reconstruct???
     */
    public void setupDatabase(TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while(choice != 1 && choice != 2) {
            System.out.println("If you have a table called classSession in your database (capitialization of the name does not matter), it will be dropped and a new ClassSession table will be created.\nIf you want to proceed, enter 1; if you want to quit, enter 2.");

            choice = readInteger(false);

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        // this goes to Gym's databaseSetup method, which then calls the DBConnectionHandler's method
                        delegate.databaseSetup();
                        break;
                    case 2:
                        handleQuitOption();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.\n");
                        break;
                }
            }
        }
    }

    /**
     * Displays simple text interface
     */
    public void showMainMenu(TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println();
            System.out.println("1. Insert classSession");
            System.out.println("2. Delete classSession");
            System.out.println("3. Update classSession start time");
            System.out.println("4. Show class session");
            System.out.println("5. Quit");
            System.out.print("Please choose one of the above 5 options: ");

            choice = readInteger(false);

            System.out.println(" ");

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleInsertOption();
                        break;
                    case 2:
                        handleDeleteOption();
                        break;
                    case 3:
                        handleUpdateOption();
                        break;
                    case 4:
                        delegate.showClassSessionTerminal();
                        break;
                    case 5:
                        handleQuitOption();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        break;
                }
            }
        }
    }

    private void handleDeleteOption() {
        int class_code = INVALID_INPUT;
        while (class_code == INVALID_INPUT) {
            System.out.print("Please enter the classSession class code you wish to delete: ");
            class_code = readInteger(false);
            if (class_code != INVALID_INPUT) {
                delegate.deleteClassSession(class_code);
            }
        }
    }
}