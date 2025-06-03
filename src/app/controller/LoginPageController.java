package app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

import app.helper.DatabaseHelper;
import app.model.Player;

public class LoginPageController {

    @FXML
    private AnchorPane rootPane;

    private DatabaseHelper dbHelper;
    private ObservableList<Player> playerList;

    public void initialize() {
        dbHelper = DatabaseHelper.getInstance();
        loadPlayersAndGenerateButtons();
    }

    private void loadPlayersAndGenerateButtons() {
        rootPane.getChildren().clear();
        playerList = dbHelper.getAllPlayers();

        // Label "WELCOME" - menggunakan CSS class
        Label welcomeLabel = new Label("WELCOME");
        welcomeLabel.getStyleClass().add("label-welcome"); // Menggunakan CSS class
        welcomeLabel.setLayoutX(100);
        welcomeLabel.setLayoutY(80);
        welcomeLabel.setTextAlignment(TextAlignment.CENTER);
        rootPane.getChildren().add(welcomeLabel);

        // Hitung posisi tombol
        int jumlahPlayer = playerList.size();
        int jumlahButton = Math.min(4, jumlahPlayer == 4 ? 4 : jumlahPlayer + 1);

        double startY = 180;
        double spacing = 80;

        for (int i = 0; i < jumlahPlayer && i < 4; i++) {
            Player player = playerList.get(i);
            Button btn = createPlayerButton(player, startY + i * spacing);
            rootPane.getChildren().add(btn);
        }

        if (jumlahPlayer < 4) {
            Button newPlayerBtn = createNewPlayerButton(startY + jumlahPlayer * spacing);
            rootPane.getChildren().add(newPlayerBtn);
        }
    }

    private Button createPlayerButton(Player player, double y) {
        Button btn = new Button(player.getName());
        btn.getStyleClass().add("button-player"); // DIPERBAIKI: sesuaikan dengan CSS
        btn.setLayoutX(75);
        btn.setLayoutY(y);
        btn.setOnAction(e -> masukKeGame(player));
        return btn;
    }

    private Button createNewPlayerButton(double y) {
        Button btn = new Button("New Player");
        btn.getStyleClass().add("button-player"); // DIPERBAIKI: sesuaikan dengan CSS
        btn.setLayoutX(75);
        btn.setLayoutY(y);
        btn.setOnAction(e -> buatAkunBaru());
        return btn;
    }

    private void masukKeGame(Player player) {
        try {
            dbHelper.generateDailyMissions(player.getIdPlayer());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/view/HomePage.fxml"));
            Parent root = loader.load();

            HomePageController homeController = loader.getController();
            if (homeController != null) {
                homeController.setCurrentPlayer(player);
            }

            Scene scene = new Scene(root);
            // DIPERBAIKI: gunakan path yang sama dengan FXML
            scene.getStylesheets().add(getClass().getResource(".../assets/styles/loginpage.css").toExternalForm());

            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buatAkunBaru() {
    try {
        // Load the CreateAccountPage FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/view/CreateAccountPage.fxml"));
        Parent root = loader.load();

        // Create a new scene with the loaded FXML
        Scene scene = new Scene(root);

        // Get the current stage (login window)
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        
        // Close the current stage
        if (currentStage != null) {
            currentStage.close();
        }

        // Create a new stage for the CreateAccountPage
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Create New Account"); // Optional: Set the title of the new window
        newStage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


}