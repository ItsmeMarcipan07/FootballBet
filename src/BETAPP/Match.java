package BETAPP;

public class Match {
    private int id;
    private String team1;
    private String team2;
    private double coefTeam1;
    private double coefDraw;
    private double coefTeam2;
    private String result;

    public Match(int id, String team1, String team2, double coefTeam1, double coefDraw, double coefTeam2, String result) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.coefTeam1 = coefTeam1;
        this.coefDraw = coefDraw;
        this.coefTeam2 = coefTeam2;
        this.result = result;
    }

    // Getters и Setters
    public int getId() { return id; }
    public String getTeam1() { return team1; }
    public String getTeam2() { return team2; }
    public double getCoefTeam1() { return coefTeam1; }
    public double getCoefDraw() { return coefDraw; }
    public double getCoefTeam2() { return coefTeam2; }
    public String getResult() { return result; }

    public void setResult(String result) {
        this.result = result;
    }
}

