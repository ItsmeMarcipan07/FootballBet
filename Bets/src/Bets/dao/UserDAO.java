package Bets.dao;

import Bets.Database;
import Bets.models.User;

import java.sql.*;

public class UserDAO {
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