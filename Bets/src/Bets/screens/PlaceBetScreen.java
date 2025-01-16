package Bets.screens;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Bets.models.Bet;
import Bets.models.User;
import Bets.models.Match;
import Bets.dao.BetDAO;
import Bets.dao.MatchDAO;
import Bets.dao.UserDAO;

public class PlaceBetScreen {

    private VBox layout;
    private User currentUser;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/betting_app";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private Bet bet;

    public PlaceBetScreen(Stage primaryStage, User currentUser, Match match) {
        TextField amountField = new TextField();
        amountField.setPromptText("Bet Amount");
        Label balanceLabel = new Label("Balance: " + currentUser.getBalance() + " BGN");
        balanceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        ComboBox<String> betTypeBox = new ComboBox<>();
        betTypeBox.getItems().addAll("Team1", "Draw", "Team2");
        Label infoLabel = new Label("Place Bet on:  " + match.getTeam1() + " vs " + match.getTeam2());
        infoLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Добавяне на информация за коефициентите
        double oddsTeam1 = match.getCoefTeam1();
        double oddsDraw = match.getCoefDraw();
        double oddsTeam2 = match.getCoefTeam2();

        Label oddsLabel = new Label("Odds: " + "Team1: " + oddsTeam1 + ", Draw: " + oddsDraw + ", Team2: " + oddsTeam2);
        oddsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Button confirmButton = new Button("Place Bet");
        confirmButton.setOnAction(e -> {
            double betAmount = Double.parseDouble(amountField.getText());
            String betType = betTypeBox.getValue();
            double selectedOdds = 0.0;

            // Избиране на коефициент според типа на залога
            if (betType.equals("Team1")) {
                selectedOdds = oddsTeam1;
            } else if (betType.equals("Draw")) {
                selectedOdds = oddsDraw;
            } else if (betType.equals("Team2")) {
                selectedOdds = oddsTeam2;
            }

            if (betAmount <= currentUser.getBalance()) {
                BetDAO.placeBet(currentUser.getId(), match.getId(), betAmount, betType, selectedOdds);
                currentUser.setBalance(currentUser.getBalance() - betAmount);
                UserDAO.updateUserBalance(currentUser);
                BetDAO.updateWon(MatchDAO.simulateMatch(match), match, betType, currentUser, betAmount);
                MatchDAO.updateMatch(match.getId());

                HomeScreen homeScreen = new HomeScreen(primaryStage, currentUser);
                primaryStage.setScene(new Scene(homeScreen.getLayout(), 400, 300));
            } else {
                new Alert(Alert.AlertType.ERROR, "Insufficient balance!").showAndWait();
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            HomeScreen homeScreen = new HomeScreen(primaryStage, currentUser);
            primaryStage.setScene(new Scene(homeScreen.getLayout(), 400, 300));
        });
        layout = new VBox(10, infoLabel, balanceLabel, oddsLabel, amountField, betTypeBox,
                confirmButton, backButton);
    }

    public VBox getLayout() {
        return layout;
    }
}
