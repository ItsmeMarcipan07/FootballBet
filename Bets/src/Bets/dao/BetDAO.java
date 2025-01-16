package Bets.dao;

import Bets.models.Bet;
import Bets.Database;
import Bets.models.Match;
import Bets.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BetDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/betting_app";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    public static void placeBet(int userId, int matchId, double betAmount, String betType, double odds) {
        String query = "INSERT INTO bets (user_id, match_id, bet_amount, bet_type,odds) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, matchId);
            stmt.setDouble(3, betAmount);
            stmt.setString(4, betType);
            //stmt.setDouble(5, MatchDAO.simulateMatch(match));
            stmt.setDouble(5, odds); // Добавяме коефициента при запис в базата

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
    public static void updateWon(String result, Match match, String betType, User currentUser, double betAmount) {
        String sql = "UPDATE Bets SET is_won = ? WHERE match_id = ?";
        try (Connection conn = Database.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if(result.equals(betType)) {
                stmt.setBoolean(1, true);
                stmt.setInt(2, match.getId());
                stmt.executeUpdate();
                updateBalanceFromOdds(betAmount, currentUser, match, result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateBalanceFromOdds(double betAmount, User currentUser, Match match, String result) {
        String sql = "UPDATE Users SET balance = ? WHERE id = ?";
        try (Connection conn = Database.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if(result.equals("Team1")) {
                stmt.setDouble(1, currentUser.getBalance() + (betAmount * match.getCoefTeam1()));
                stmt.setInt(2, currentUser.getId());
                stmt.executeUpdate();
            }
            else if(result.equals("Team2")) {
                stmt.setDouble(1, currentUser.getBalance() + (betAmount * match.getCoefTeam1()));
                stmt.setInt(2, currentUser.getId());
                stmt.executeUpdate();
            }
            else {
                stmt.setDouble(1, currentUser.getBalance() + (betAmount * match.getCoefTeam1()));
                stmt.setInt(2, currentUser.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
