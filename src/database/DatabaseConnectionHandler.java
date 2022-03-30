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
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ClassSession VALUES (?,?,?,?,?,?,?)");
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
            PreparedStatement ps = connection.prepareStatement("DELETE FROM ClassSession WHERE class_code = ?");
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
            PreparedStatement ps = connection.prepareStatement("UPDATE ClassSession SET start_time = ? WHERE class_code = ?");
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

    public ClassSession[] selectClassSession(String cat, Timestamp s_time) {
        //TODO: select classes with specific categories and times
        System.out.println("Executing Select");
        ArrayList<ClassSession> result = new ArrayList<ClassSession>();

        try {
            String query = "SELECT * FROM ClassSession WHERE category = "+ cat +" AND start_time = "+ s_time;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
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
            //TODO: correct thing to do? Don't need to rollback b/c no modification?
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new ClassSession[result.size()]);
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
        dropAllGymTablesIfExists();

        //TODO: might not need this, just run sql script to populate

    }

    private void dropAllGymTablesIfExists() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");

            //TODO: loop through to drop all tables
            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals("ClassSession")) {
                    stmt.execute("DROP TABLE ClassSession");
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
