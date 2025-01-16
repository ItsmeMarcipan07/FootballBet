package Bets.dao;

import Bets.Database;
import Bets.models.User;

import java.sql.*;

public class UserDAO {
    public static User login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (Connection conn = Database.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean register(String username, String password) {
        String sql = "INSERT INTO Users (username, password, balance) VALUES (?, ?, 100.0)";
        try (Connection conn = Database.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void updateUserBalance(User user) {
        String sql = "UPDATE Users SET balance = ? WHERE id = ?";
        try (Connection conn = Database.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, user.getBalance());
            stmt.setInt(2, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
//import java.sql.*;
//
//public class UserDAO {
//    public static User login(String username, String password) {
//        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
//        try (Connection conn = Database.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return new User(
//                    rs.getInt("id"),
//                    rs.getString("username"),
//                    rs.getString("password"),
//                    rs.getDouble("balance")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static boolean register(String username, String password) {
//        String sql = "INSERT INTO Users (username, password, balance) VALUES (?, ?, 100.0)";
//        try (Connection conn = Database.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            stmt.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}