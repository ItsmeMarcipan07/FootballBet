package Bets;

public class BetCalculator {
    public static double calculatePayout(double betAmount, double odds, boolean isWon) {
        return isWon ? betAmount * odds : 0;
    }
}

