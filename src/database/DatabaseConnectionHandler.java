package database;

import model.*;

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
            PreparedStatement ps = connection.prepareStatement("INSERT INTO CLASSSESSION VALUES (?,?,?,?,?,?,?)");
            //TODO: need to check for nulls?
            ps.setInt(1, model.getClass_code());
            ps.setString(2, model.getAddress());
            ps.setInt(3, model.getSIN());
            ps.setTimestamp(4, model.getStart_time());
            ps.setString(5, model.getCategory());
            ps.setInt(6, model.getDuration());
            ps.setInt(7, model.getCapacity());

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
            PreparedStatement ps = connection.prepareStatement("DELETE FROM CLASSSESSION WHERE class_code = ?");
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
            PreparedStatement ps = connection.prepareStatement("UPDATE CLASSSESSION SET start_time = ? WHERE class_code = ?");
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

    public ClassSession[] selectClassSession(String duration, String cat, String class_size) {
        //TODO: select classes with specific categories and times
        System.out.println("Executing Select");
        ArrayList<ClassSession> result = new ArrayList<ClassSession>();

        int oneClause = 0;
        String categoryQ = "";
        if (!cat.equals("all")){
            categoryQ = " category = " + "\'" + cat + "\'";
            oneClause = 1;
        }
        String durationQ = "";
        if (duration.equals("60+")){
            durationQ = " duration > " + 60;
            oneClause = 1;
        } else if (!duration.equals("all")){
            durationQ = " duration = " + duration;
            oneClause = 1;
        }

        /*String timeQ = "";
        if (!timePeriod.equals("all")){
            String days = "1";
            if (timePeriod.equals("3 days")) {
                days = "3";
            } else if (timePeriod.equals("1 week")){
                days = "7";
            } else if (timePeriod.equals("1 month")) {
                days = "31";
            } else {
                days = "1";
            }
            timeQ = " WHERE start_time < " + days;
        }*/

        String sizeQ = "";
        if (class_size.equals("30+")){
            sizeQ = " capacity > " + 30;
            oneClause = 1;
        } else if (!class_size.equals("all")){
            sizeQ = " capacity = " + class_size;
            oneClause = 1;
        }
        String where = (oneClause == 1) ? " WHERE" : "";
        String delim1 = (categoryQ.equals("") || (durationQ.equals("") && sizeQ.equals("")) ? "" : " AND ");
        String delim2 = (durationQ.equals("") ||(sizeQ.equals("")) ? "" : " AND ");

        try {
            String query = "SELECT * FROM ClassSession " + where + categoryQ + delim1+  durationQ + delim2 + sizeQ;
            System.out.println(query);
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

            //TODO: might not need to close resultsets, autoclose
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            //TODO: correct thing to do? Don't need to rollback b/c no modification?
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new ClassSession[result.size()]);
    }

    public ProjectionClass[] projectAllClassSessions() {
        System.out.println("executing projection");
        ArrayList<ProjectionClass> result = new ArrayList<ProjectionClass>();

        try {
            String query = "SELECT address, start_time, category, duration, capacity FROM CLASSSESSION";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ProjectionClass projectionClass = new ProjectionClass(
                        rs.getString("address"),
                        rs.getTimestamp("start_time"),
                        rs.getString("category"),
                        rs.getInt("duration"),
                        rs.getInt("capacity")
                );
                result.add(projectionClass);
            }

            //TODO: might not need to close resultsets, autoclose
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new ProjectionClass[result.size()]);
    }

    public ClassSession[] getGymInfo() {
        System.out.println("executing select *");
        ArrayList<ClassSession> result = new ArrayList<ClassSession>();
        //TODO

        try {
            String query = "SELECT * FROM CLASSSESSION";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                ClassSession CLASSSESSION = new ClassSession(
                        rs.getInt("class_code"),
                        rs.getString("address"),
                        rs.getInt("SIN"),
                        rs.getTimestamp("start_time"),
                        rs.getString("category"),
                        rs.getInt("duration"),
                        rs.getInt("capacity")
                );
                result.add(CLASSSESSION);
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

    public TotalExerciseTime[] aggregSignsUps(int cid) {
        System.out.println("executing aggregation");
        ArrayList<TotalExerciseTime> result = new ArrayList<TotalExerciseTime>();

        try {
            String query = "SELECT SUM(duration) over () as totalExerciseTime " +
                    "FROM CUSTOMER C, SIGNSUP S, CLASSSESSION CS " +
                    "WHERE C.CID = S.CID AND S.CLASS_CODE = CS.CLASS_CODE AND CID = " + cid;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                TotalExerciseTime totalExerciseTime = new TotalExerciseTime(
                        cid,
                        rs.getInt("totalExerciseTime")
                );
                result.add(totalExerciseTime);
            }

            rs.close();
            stmt.close();
        } catch(SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new TotalExerciseTime[result.size()]);
    }

    /**
     * Finds number of classes for each gym location
     */
    public ClassesPerLocation[] findNumClassesAllLocations() {
        System.out.println("executing nested aggregation");
        ArrayList<ClassesPerLocation> result = new ArrayList<ClassesPerLocation>();

        try {
            String query = "SELECT L.ADDRESS, COUNT(DISTINCT CLASS_CODE) as num_classes " +
                    "FROM LOCATION L, CLASSSESSION C " +
                    "WHERE L.ADDRESS = C.ADDRESS GROUP BY L.ADDRESS";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                ClassesPerLocation classesPerLocation = new ClassesPerLocation(
                        rs.getString("address"),
                        rs.getInt("numClasses")
                );
                result.add(classesPerLocation);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new ClassesPerLocation[result.size()]);
    }

    /**
     * Division Query: Find locations offering classes in all categories
     */
    public LocationAddress[] findLocationsWithAllClassCategories() {
        System.out.println("executing division");
        ArrayList<LocationAddress> result = new ArrayList<LocationAddress>();

        try {
            String query = "SELECT L.ADDRESS FROM LOCATION L WHERE NOT EXISTS(" +
                    "SELECT C1.CATEGORY FROM CLASSSESSION C1 MINUS (" +
                    "SELECT C2.CATEGORY FROM CLASSSESSION C2 WHERE L.ADDRESS = C2.ADDRESS))";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                LocationAddress locationAddress = new LocationAddress(
                  rs.getString("address")
                );
                result.add(locationAddress);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new LocationAddress[result.size()]);
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
                if(rs.getString(1).toLowerCase().equals("classsession")) {
                    stmt.execute("DROP TABLE CLASSSESSION");
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
