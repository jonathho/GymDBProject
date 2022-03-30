package database;

import model.ClassSession;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void insertClassSession(ClassSession model) {
        //TODO
    }

    public void deleteClassSession(int class_code) {
        //TODO
    }

    public void updateClassSession(int class_code, Timestamp start_time) {
        //TODO
    }

    public ClassSession[] getGymInfo() {
        System.out.println("executing select *");
        ArrayList<ClassSession> result = new ArrayList<ClassSession>();
        //TODO

        try {
            String query = "SELECT * FROM classSession";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                ClassSession classSession = new ClassSession(
                        rs.getInt("class_code"),
                        rs.getString("address"),
                        rs.getInt("SIN"),
                        rs.getTimestamp("start_time"),
                        rs.getString("category"),
                        rs.getInt("duration"),
                        rs.getInt("capacity")
                );
                result.add(classSession);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new ClassSession[result.size()]);
    }

    public ClassSession[] getJoinInfo(int cid) {
        System.out.println("executing join");
        ArrayList<ClassSession> result = new ArrayList<ClassSession>();
        //TODO

        try {
            String query = "SELECT * FROM SIGNSUP S, CLASSSESSION C WHERE " +
                    "C.CLASS_CODE = S.CLASS_CODE and S.CID = " + cid;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                ClassSession classSession = new ClassSession(
                        rs.getInt("class_code"),
                        rs.getString("address"),
                        rs.getInt("SIN"),
                        rs.getTimestamp("start_time"),
                        rs.getString("category"),
                        rs.getInt("duration"),
                        rs.getInt("capacity")
                );
                result.add(classSession);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new ClassSession[result.size()]);
    }


    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void databaseSetup() {
        //dropClassSessionTableIfExists();

        //TODO
    }

    private void dropClassSessionTableIfExists() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");

            //TODO: check naming on these later
            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals("classSession")) {
                    stmt.execute("DROP TABLE classSession");
                    break;
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
