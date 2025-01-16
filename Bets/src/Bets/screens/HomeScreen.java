package Bets.screens;

import Bets.models.Bet;
import Bets.models.User;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeScreen {

    private VBox layout;
    public HomeScreen(Stage primaryStage, User currentUser) {
        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label balanceLabel = new Label("Balance: " + currentUser.getBalance() + " BGN");
        balanceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Button logoutButton = new Button("Logout");
        Button withdrawButton = new Button("Withdraw");
        Button depositButton = new Button("Deposit");
        Button showMatchesButton = new Button("Show Matches");
        Button betHistoryButton = new Button("Bet History");
        Button placeBetButton = new Button("Place Bet");

        logoutButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert Box");
            alert.setHeaderText(null);
            alert.setContentText("Successfully logged out!");
            alert.showAndWait();
            LoginScreen loginScreen = new LoginScreen(primaryStage);
            primaryStage.setScene(new Scene(loginScreen.getLayout(), 300, 200));
        });

        withdrawButton.setOnAction(e -> {
            WithdrawScreen withdrawScreen = new WithdrawScreen(primaryStage, currentUser);
            primaryStage.setScene(new Scene(withdrawScreen.getLayout(), 300, 200));
        });

        depositButton.setOnAction(e -> {
            DepositScreen depositScreen = new DepositScreen(primaryStage, currentUser);
            primaryStage.setScene(new Scene(depositScreen.getLayout(), 300, 200));
        });

        showMatchesButton.setOnAction(e -> {
            ShowMatchesScreen showMatchesScreen = new ShowMatchesScreen(primaryStage, currentUser);
            primaryStage.setScene(new Scene(showMatchesScreen.getLayout(), 400, 300));
        });

        betHistoryButton.setOnAction(e -> {
            ShowBetHistoryScreen betHistoryScreen = new ShowBetHistoryScreen(primaryStage, currentUser);
            primaryStage.setScene(new Scene(betHistoryScreen.getLayout(), 400, 300));
        });


        layout = new VBox(10, welcomeLabel, balanceLabel,withdrawButton, depositButton, showMatchesButton, betHistoryButton,
                logoutButton);
    }

    public VBox getLayout() {
        return layout;
    }
}
