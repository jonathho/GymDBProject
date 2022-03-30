package delegates;

import model.ClassSession;

import java.sql.Timestamp;

public interface TerminalTransactionsDelegate {
    void databaseSetup();

    void insertClassSession(ClassSession model);
    void deleteClassSession(int class_code);
    void updateClassSession(int class_code, Timestamp start_time);
    void printResults();

    void transactionsFinishedTerminal();
}