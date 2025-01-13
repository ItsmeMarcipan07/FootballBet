package Bets;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/betting_app";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }
}
