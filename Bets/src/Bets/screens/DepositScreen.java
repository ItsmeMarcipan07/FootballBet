package Bets.screens;

import Bets.User;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Bets.UserDAO;

public class DepositScreen {

    private VBox layout;

    public DepositScreen(Stage primaryStage, User currentUser) {
        Label titleLabel = new Label("Deposit Funds");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount to deposit");

        Button depositButton = new Button("Deposit");
        Button backButton = new Button("Back");

        depositButton.setOnAction(e -> {
            int amount = Integer.parseInt(amountField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert Box");
            alert.setHeaderText(null);
            alert.setContentText("Successfully deposited!");
            alert.showAndWait();
            currentUser.setBalance(currentUser.getBalance() + amount);
            UserDAO.updateUserBalance(currentUser);
            HomeScreen homeScreen = new HomeScreen(primaryStage, currentUser);
            primaryStage.setScene(new Scene(homeScreen.getLayout(), 400, 300));
            System.out.println("Deposit: " + amountField.getText());
        });

        backButton.setOnAction(e -> {
            HomeScreen homeScreen = new HomeScreen(primaryStage, currentUser);
            primaryStage.setScene(new Scene(homeScreen.getLayout(), 400, 300));
        });

        layout = new VBox(10, titleLabel, amountField, depositButton, backButton);
    }

    public VBox getLayout() {
        return layout;
    }
}
