package pl.michaelslabikovsky.model;

import java.sql.*;

public class LocationsDB {

    public static final String SUCCESS_OPENING_DB_MSG = "Opened database successfully";
    public static final String OPERATION_SUCCESS_MSG = "Operation done successfully";

    public void openDB() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:LocationsDB");

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
            c = DriverManager.getConnection("jdbc:sqlite:LocationsDB");
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

    private void closeConnection(Connection c, Statement stmt) throws SQLException {
        stmt.close();
        c.commit();
        c.close();
    }

    public void selectFromDB() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:LocationsDB");
            c.setAutoCommit(false);
            System.out.println(SUCCESS_OPENING_DB_MSG);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery(" SELECT * FROM COMPANY;");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println("ID: " + id);
                System.out.println("NAME: " + name);
                System.out.println("AGE: " + age);
                System.out.println("ADDRESS: " + address);
                System.out.println("SALARY: " + salary);
                System.out.println();
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println(OPERATION_SUCCESS_MSG);
    }

    public void updateIntoTable() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:LocationsDB");
            c.setAutoCommit(false);http://google.github.io/lovefield/error_lookup/src/error_lookup.html?c=201&p0=Item.pkItem&p1=100
            System.out.println(SUCCESS_OPENING_DB_MSG);

            stmt = c.createStatement();
            String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println(OPERATION_SUCCESS_MSG);
    }

    public void deleteFromTable() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:LocationsDB");
            c.setAutoCommit(false);
            System.out.println(SUCCESS_OPENING_DB_MSG);

            stmt = c.createStatement();
            String sql = "DELETE from COMPANY where ID=2;";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}

