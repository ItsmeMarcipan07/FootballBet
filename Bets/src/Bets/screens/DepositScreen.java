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
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0) {
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
            }
            else {
                new Alert(Alert.AlertType.ERROR, "Invalid amount!").showAndWait();
            }
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
