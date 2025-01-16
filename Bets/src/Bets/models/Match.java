package Bets.models;

public class Match {
    private int id;
    private String team1;
    private String team2;
    private double oddTeam1;
    private double oddDraw;
    private double oddTeam2;
    private String result;
    private String status;

    public Match(int id, String team1, String team2, double oddTeam1, double oddDraw, double oddTeam2, String result, String status) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.oddTeam1 = oddTeam1;
        this.oddDraw = oddDraw;
        this.oddTeam2 = oddTeam2;
        this.result = result;
        this.status = status;
    }

    // Getters и Setters
    public int getId() { return id; }
    public String getTeam1() { return team1; }
    public String getTeam2() { return team2; }
    public double getOddTeam1() { return oddTeam1; }
    public double getOddDraw() { return oddDraw; }
    public double getOddTeam2() { return oddTeam2; }
    public String getResult() { return result; }

    public void setResult(String result) {
        this.result = result;
    }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

