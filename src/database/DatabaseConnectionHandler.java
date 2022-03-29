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
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO classSession VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1, model.getClass_code());
            ps.setString(2, model.getAddress());
            ps.setInt(3, model.getSIN());
            ps.setTimestamp(4, model.getStart_time());
            ps.setString(5, model.getCategory());
            ps.setInt(6, model.getDuration());
            ps.setInt(7, model.getSize());
            //TODO: need to check for nulls?

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteClassSession(int class_code) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM classSession WHERE class_code = ?");
            ps.setInt(1, class_code);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Class code " + class_code + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateClassSession(int class_code, Timestamp start_time) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE classSession SET start_time = ? WHERE class_code = ?");
            ps.setTimestamp(1, start_time);
            ps.setInt(2, class_code);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Class code " + class_code + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public ClassSession[] getGymInfo() {
        ArrayList<ClassSession> result = new ArrayList<ClassSession>();

        //TODO
        return null;
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
        dropClassSessionTableIfExists();

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
