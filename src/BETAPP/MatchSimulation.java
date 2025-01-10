package BETAPP;

import java.util.Random;

public class MatchSimulation {
    public static String simulateMatch(String team1, String team2) {
        Random random = new Random();
        int team1Goals = random.nextInt(5); // 0 до 4 гола
        int team2Goals = random.nextInt(5);

        return team1 + " " + team1Goals + " - " + team2Goals + " " + team2;
    }
}
