package Bets.screens;

import Bets.models.User;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Bets.dao.UserDAO;

public class WithdrawScreen {

    private VBox layout;

    public WithdrawScreen(Stage primaryStage, User currentUser) {
        Label titleLabel = new Label("Withdraw section");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label balanceLabel = new Label("Balance: " + currentUser.getBalance() + " BGN");
        balanceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount to withdraw");

        Button withdrawButton = new Button("Withdraw");
        Button backButton = new Button("Back");

        withdrawButton.setOnAction(e -> {
            int amount = Integer.parseInt(amountField.getText());
            if (amount <= currentUser.getBalance()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert Box");
            alert.setHeaderText(null);
            alert.setContentText("Successfully withdrawn!");
            alert.showAndWait();
            currentUser.setBalance(currentUser.getBalance() - amount);
            UserDAO.updateUserBalance(currentUser);
            HomeScreen homeScreen = new HomeScreen(primaryStage, currentUser);
            primaryStage.setScene(new Scene(homeScreen.getLayout(), 400, 300));
            }
            else {
                new Alert(Alert.AlertType.ERROR, "Insufficient balance!").showAndWait();
            }
            System.out.println("Withdraw: " + amountField.getText());
        });

        backButton.setOnAction(e -> {
            HomeScreen homeScreen = new HomeScreen(primaryStage, currentUser);
            primaryStage.setScene(new Scene(homeScreen.getLayout(), 400, 300));
        });

        layout = new VBox(10, titleLabel, balanceLabel, amountField, withdrawButton, backButton);
    }

    public VBox getLayout() {
        return layout;
    }
}