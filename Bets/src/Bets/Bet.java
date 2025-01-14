package Bets;

public class Bet {
    private int id;
    private int userId;
    private int matchId;
    private double betAmount;
    private String betType;
    private boolean isWon;
    private double odds; // Добавяме поле за коефициент


    public Bet(int id, int userId, int matchId, double betAmount, String betType, boolean isWon, double odds) {
        this.id = id;
        this.userId = userId;
        this.matchId = matchId;
        this.betAmount = betAmount;
        this.betType = betType;
        this.isWon = isWon;
        this.odds = odds; // Инициализиране на коефициента

    }

    // Getters и Setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getMatchId() { return matchId; }
    public double getBetAmount() { return betAmount; }
    public String getBetType() { return betType; }
    public boolean isWon() { return isWon; }
    public double getOdds() { return odds; } // Гетър за коефициент
    public void setOdds(double odds) { this.odds = odds; } // Сетър за коефициент

    public void setWon(boolean won) {
        isWon = won;
    }
}
