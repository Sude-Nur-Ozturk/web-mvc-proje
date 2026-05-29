package com.ecommerce.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:8081/eticaret_db";
    private static final String USER = "root";
    private static final String PASSWORD = "sude1234mysql"; 

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("CONN: " + conn);
        return conn;
    }
}
