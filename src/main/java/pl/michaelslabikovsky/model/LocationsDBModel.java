package pl.michaelslabikovsky.model;

import pl.michaelslabikovsky.utils.DialogUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationsDBModel {

    public static final String DB_URL = "jdbc:sqlite:LocationsDB.sqlite";

    public boolean insertIntoTable(String value) {
        try (Connection c = DriverManager.getConnection(DB_URL); Statement stmt = c.createStatement()) {
            c.setAutoCommit(true);
            String sql = "INSERT INTO locations" + " VALUES (null , '" + value + "');";
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public List<String> selectAllFromDB() {
        try (Connection c = DriverManager.getConnection(DB_URL); Statement stmt = c.createStatement()) {
            c.setAutoCommit(false);

            ResultSet rs = stmt.executeQuery("SELECT * FROM locations;");

            List<String> locations = new ArrayList<>();

            while (rs.next()) {
                locations.add(rs.getString("name"));
            }

            return locations;
        } catch (Exception e) {
            DialogUtils.errorDialog(e.getMessage());
            return null;
        }
    }

    public boolean deleteFromTable(String value) {
        try (Connection c = DriverManager.getConnection(DB_URL); Statement stmt = c.createStatement()) {
            c.setAutoCommit(true);

            String sql = "DELETE FROM locations WHERE name = '" + value + "';";
            stmt.executeUpdate(sql);

            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
}

