package com.flappybird.connectionDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/flappybird?useUnicode=true";    
    private static String driverName = "com.mysql.cj.jdbc.Driver";   
    private static String username = "root";   
    private static String password = "";
    private static Connection con;
    
    public static final String errorConnection = "Failed to create the database connection.";
    public static final String errorDriver = "Driver not found.";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.out.println(errorConnection + ": " + ex.getMessage()); 
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(errorDriver + ": " + ex.getMessage());
        }
        return con;
    }
}