package Bets.screens;

import Bets.BetDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Bets.Bet;
import Bets.User;

import java.util.List;

public class ShowBetHistoryScreen {

    private VBox layout;
    private User currentUser;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/betting_app";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private Bet bet;

    public ShowBetHistoryScreen(Stage primaryStage, User currentUser) {
        List<Bet> bets = BetDAO.getBetsByUser(currentUser.getId());
        layout = new VBox(10);

        for (Bet bet : bets) {
            // Показваме информация за залагането, включително коефициента и дали е спечелено
            Label betLabel = new Label("Bet on Match: " + bet.getMatchId() +
                    ", Amount: " + bet.getBetAmount() +
                    ", Type: " + bet.getBetType() +
                    ", Odds: " + bet.getOdds() +   // Добавяме коефициент
                    ", Won: " + (bet.isWon() ? "Yes" : "No"));
            betLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            layout.getChildren().add(betLabel);
        }


        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            HomeScreen homeScreen = new HomeScreen(primaryStage, currentUser);
            primaryStage.setScene(new Scene(homeScreen.getLayout(), 400, 300));
        });
        layout.getChildren().add(backButton);
        // Създаваме ScrollPane и добавяме VBox в него
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true); // Позволява layout съдържанието да се разширява по ширина

    }

    public VBox getLayout() {
        return layout;
    }
}