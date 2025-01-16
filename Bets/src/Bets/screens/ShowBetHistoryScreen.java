package Bets.screens;

import Bets.dao.BetDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Bets.models.Bet;
import Bets.models.User;

import java.util.List;

public class ShowBetHistoryScreen {

    private VBox layout;

    public ShowBetHistoryScreen(Stage primaryStage, User currentUser) {
        List<Bet> bets = BetDAO.getBetsByUser(currentUser.getId());
        layout = new VBox(10);

        for (Bet bet : bets) {
            Label betLabel = new Label("Bet on Match: " + bet.getMatchId() +
                    ", Amount: " + bet.getBetAmount() +
                    ", Type: " + bet.getBetType() +
                    ", Odds: " + bet.getOdds() +
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
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);

    }

    public VBox getLayout() {
        return layout;
    }
}