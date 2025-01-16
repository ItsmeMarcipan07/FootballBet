package Bets.screens;

import Bets.models.User;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Bets.models.Match;
import Bets.dao.MatchDAO;
import java.util.List;

public class ShowMatchesScreen {

    private VBox layout;

    public ShowMatchesScreen(Stage primaryStage, User currentUser) {
        List<Match> matches = MatchDAO.getAllMatches();
        VBox activeMatchesLayout = new VBox(10);
        VBox concludedMatchesLayout = new VBox(10);

        Label activeLabel = new Label("Активни мачове:");
        activeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        activeMatchesLayout.getChildren().add(activeLabel);

        Label concludedLabel = new Label("Приключили мачове:");
        concludedLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        concludedMatchesLayout.getChildren().add(concludedLabel);

        for (Match match : matches) {
            System.out.println("Match Status: " + match.getStatus());

            Button matchButton = new Button(match.getTeam1() + " vs " + match.getTeam2());
            String status = match.getStatus().trim(); // Премахване на излишни празни символи
            if ("Active".equals(status)) {
                matchButton.setOnAction(e -> {
                    PlaceBetScreen placeBetScreen = new PlaceBetScreen(primaryStage, currentUser, match);
                    primaryStage.setScene(new Scene(placeBetScreen.getLayout(), 400, 300));});
                activeMatchesLayout.getChildren().add(matchButton);
            } else if ("Concluded".equals(status)) {
                matchButton.setDisable(true);
                concludedMatchesLayout.getChildren().add(matchButton);
            }
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            HomeScreen homeScreen = new HomeScreen(primaryStage, currentUser);
            primaryStage.setScene(new Scene(homeScreen.getLayout(), 400, 300));
        });

        layout = new VBox(20, activeMatchesLayout, concludedMatchesLayout, backButton);

        layout.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
    }

    public VBox getLayout() {
        return layout;
    }
}
