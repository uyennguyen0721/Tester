package com.ou_software_testing.ou_software_testing.services;

import java.sql.*;


public class JdbcServices {
    //Change the comment to your password and leave the first line
    private static String[] info = {"root", "123456"};

    
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/saledb", info[0], info[1]);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
}
