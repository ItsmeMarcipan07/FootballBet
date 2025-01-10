package BETAPP;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BetDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/betting_app";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    public static void placeBet(int userId, int matchId, double betAmount, String betType, double odds) {
        String query = "INSERT INTO bets (user_id, match_id, bet_amount, bet_type,is_won ,odds) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, matchId);
            stmt.setDouble(3, betAmount);
            stmt.setString(4, betType);
            //stmt.setDouble(5, is_won);
            stmt.setDouble(6, odds); // Добавяме коефициента при запис в базата

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static List<Bet> getBetsByUser(int userId) {
        List<Bet> bets = new ArrayList<>();
        String query = "SELECT * FROM bets WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int matchId = rs.getInt("match_id");
                double betAmount = rs.getDouble("bet_amount");
                String betType = rs.getString("bet_type");
                boolean isWon = rs.getBoolean("is_won");
                double odds = rs.getDouble("odds"); // Извличаме коефициента

                bets.add(new Bet(id, userId, matchId, betAmount, betType, isWon, odds)); // Връщаме залагането с коефициент
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bets;
    }
}
