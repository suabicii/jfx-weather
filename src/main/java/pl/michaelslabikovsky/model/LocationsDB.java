package pl.michaelslabikovsky.model;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationsDB {

    public static final String SUCCESS_OPENING_DB_MSG = "Opened database successfully";
    public static final String OPERATION_SUCCESS_MSG = "Operation done successfully";

    public void openDB() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:LocationsDB.sqlite");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println(SUCCESS_OPENING_DB_MSG);
    }

    public boolean insertIntoTable(String value) throws SQLException {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:LocationsDB.sqlite");
            c.setAutoCommit(false);
            System.out.println(SUCCESS_OPENING_DB_MSG);

            stmt = c.createStatement();
            String sql = "INSERT INTO locations" + " VALUES (null , '" + value + "');";
            stmt.executeUpdate(sql);

            closeConnection(c, stmt);
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            closeConnection(c, stmt);
            return false;
        }
    }

    public List<String> selectAllFromDB() throws SQLException {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:LocationsDB.sqlite");
            c.setAutoCommit(false);
            System.out.println(SUCCESS_OPENING_DB_MSG);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM locations;");

            List<String> locations = new ArrayList<>();

            while (rs.next()) {
                locations.add(rs.getString("name"));
            }

            rs.close();
            closeConnection(c, stmt);
            System.out.println(OPERATION_SUCCESS_MSG);
            return locations;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            closeConnection(c, stmt);
            return null;
        }
    }

    private void closeConnection(Connection c, Statement stmt) throws SQLException {
        stmt.close();
        c.commit();
        c.close();
    }
}

