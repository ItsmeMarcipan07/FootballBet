package Bets.dao;

import Bets.Database;
import Bets.models.Match;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatchDAO {
    public static List<Match> getAllMatches() {
        List<Match> matches = new ArrayList<>();
        String sql = "SELECT * FROM Matches";
        try (Connection conn = Database.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                matches.add(new Match(
                        rs.getInt("id"),
                        rs.getString("team1"),
                        rs.getString("team2"),
                        rs.getDouble("coef_team1"),
                        rs.getDouble("coef_draw"),
                        rs.getDouble("coef_team2"),
                        rs.getString("result"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matches;
    }
    public static String simulateMatch(Match match) {
        Random random = new Random();
        int team1Goals = random.nextInt(5);
        int team2Goals = random.nextInt(5);
        String result = team1Goals > team2Goals ? "Team1" : team1Goals == team2Goals ? "Draw" : "Team2";

        String sql = "UPDATE Matches SET result = ? WHERE id = ?";
        try (Connection conn = Database.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, result);
            stmt.setInt(2, match.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    public static void updateMatch(int matchId) {
        String sql = "UPDATE Matches SET status = ? WHERE id = ?";
        try (Connection conn = Database.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Concluded");
            stmt.setInt(2, matchId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
