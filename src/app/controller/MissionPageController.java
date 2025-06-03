package app.controller;

import app.helper.DatabaseHelper;
import app.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MissionPageController implements Initializable {
    
    @FXML private Label rankLabel;
    @FXML private Label goldLabel;
    @FXML private Label gemLabel;
    @FXML private Button dailyMissionBtn;
    @FXML private Button longTermMissionBtn;
    @FXML private ScrollPane missionScrollPane;
    @FXML private VBox missionContainer;
    @FXML private Button backBtn;
    
    private DatabaseHelper dbHelper;
    private Player currentPlayer;
    private boolean showingDailyMissions = true;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbHelper = DatabaseHelper.getInstance();
        
        // Set initial player (assuming first player for demo)
        ObservableList<Player> players = dbHelper.getAllPlayers();
        if (!players.isEmpty()) {
            currentPlayer = players.get(0);
            updatePlayerInfo();
            
            // Generate daily missions if needed
            dbHelper.generateDailyMissions(currentPlayer.getIdPlayer());
            
            // Initialize long-term missions if needed
            if (dbHelper.getPlayerLongTermMissions(currentPlayer.getIdPlayer()).isEmpty()) {
                dbHelper.initializeLongTermMissions(currentPlayer.getIdPlayer());
            }
            
            // Show daily missions by default
            showDailyMissions();
        }
        
        // Style scroll pane
        missionScrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        missionScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        missionScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }
    
    private void updatePlayerInfo() {
        if (currentPlayer != null) {
            // Refresh player data
            currentPlayer = dbHelper.getPlayerById(currentPlayer.getIdPlayer());
            
            rankLabel.setText("Rank " + currentPlayer.getLevel());
            goldLabel.setText(String.valueOf(currentPlayer.getGold()));
            gemLabel.setText("0"); // Gems not implemented yet
        }
    }
    
    @FXML
    private void showDailyMissions() {
        showingDailyMissions = true;
        updateButtonStyles();
        loadDailyMissions();
    }
    
    @FXML
    private void showLongTermMissions() {
        showingDailyMissions = false;
        updateButtonStyles();
        loadLongTermMissions();
    }
    
    private void updateButtonStyles() {
        if (showingDailyMissions) {
            dailyMissionBtn.setStyle("-fx-background-color: #8B4513; -fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 10 20; -fx-font-weight: bold;");
            longTermMissionBtn.setStyle("-fx-background-color: #CD853F; -fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 10 20;");
        } else {
            dailyMissionBtn.setStyle("-fx-background-color: #CD853F; -fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 10 20;");
            longTermMissionBtn.setStyle("-fx-background-color: #8B4513; -fx-text-fill: white; -fx-background-radius: 15; -fx-padding: 10 20; -fx-font-weight: bold;");
        }
    }
    
    private void loadDailyMissions() {
        missionContainer.getChildren().clear();
        
        ObservableList<DailyAssignment> dailyAssignments = dbHelper.getPlayerDailyMissions(currentPlayer.getIdPlayer());
        ObservableList<Mission> allMissions = dbHelper.getAllMissions();
        
        for (DailyAssignment assignment : dailyAssignments) {
            Mission mission = allMissions.stream()
                .filter(m -> m.getIdMission() == assignment.getIdMission())
                .findFirst()
                .orElse(null);
                
            if (mission != null) {
                VBox missionCard = createMissionCard(mission, assignment.isCompleted(), assignment.isClaimed(), 
                    () -> handleDailyMissionAction(assignment, mission));
                missionContainer.getChildren().add(missionCard);
            }
        }
        
        if (dailyAssignments.isEmpty()) {
            Label noMissionsLabel = new Label("No daily missions available today!");
            noMissionsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #8B4513; -fx-font-weight: bold;");
            missionContainer.getChildren().add(noMissionsLabel);
        }
    }
    
    private void loadLongTermMissions() {
        missionContainer.getChildren().clear();
        
        ObservableList<PlayerMission> playerMissions = dbHelper.getPlayerLongTermMissions(currentPlayer.getIdPlayer());
        ObservableList<Mission> allMissions = dbHelper.getAllMissions();
        
        for (PlayerMission playerMission : playerMissions) {
            Mission mission = allMissions.stream()
                .filter(m -> m.getIdMission() == playerMission.getIdMission())
                .findFirst()
                .orElse(null);
                
            if (mission != null) {
                VBox missionCard = createMissionCard(mission, playerMission.isCompleted(), playerMission.isClaimed(),
                    () -> handleLongTermMissionAction(playerMission, mission));
                missionContainer.getChildren().add(missionCard);
            }
        }
        
        if (playerMissions.isEmpty()) {
            Label noMissionsLabel = new Label("No long-term missions available!");
            noMissionsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #8B4513; -fx-font-weight: bold;");
            missionContainer.getChildren().add(noMissionsLabel);
        }
    }
    
    private VBox createMissionCard(Mission mission, boolean completed, boolean claimed, Runnable actionHandler) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #FFF8DC; -fx-background-radius: 15; -fx-border-color: #8B4513; -fx-border-radius: 15; -fx-border-width: 2;");
        
        // Status indicator
        HBox statusBox = new HBox();
        statusBox.setAlignment(Pos.CENTER_LEFT);
        statusBox.setSpacing(10);
        
        if (completed && claimed) {
            Label statusLabel = new Label("CLAIMED");
            statusLabel.setStyle("-fx-background-color: #32CD32; -fx-text-fill: white; -fx-padding: 5 10; -fx-background-radius: 10; -fx-font-weight: bold; -fx-font-size: 10px;");
            statusBox.getChildren().add(statusLabel);
        } else if (completed) {
            Label statusLabel = new Label("COMPLETED");
            statusLabel.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #8B4513; -fx-padding: 5 10; -fx-background-radius: 10; -fx-font-weight: bold; -fx-font-size: 10px;");
            statusBox.getChildren().add(statusLabel);
        }
        
        // Mission content
        HBox contentBox = new HBox();
        contentBox.setSpacing(15);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        
        // Mission icon
        ImageView missionIcon = new ImageView();
        missionIcon.setFitHeight(50);
        missionIcon.setFitWidth(50);
        try {
            missionIcon.setImage(new Image(getClass().getResourceAsStream("/app/images/mission_icon.png")));
        } catch (Exception e) {
            // Use placeholder if image not found
            missionIcon.setStyle("-fx-background-color: #8B4513; -fx-background-radius: 25;");
        }
        
        // Mission info
        VBox infoBox = new VBox();
        infoBox.setSpacing(5);
        
        Label titleLabel = new Label(mission.getTitle());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #8B4513;");
        
        Label descLabel = new Label(mission.getDescription());
        descLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #8B4513;");
        descLabel.setWrapText(true);
        
        // Stars (difficulty indicator)
        HBox starsBox = new HBox();
        starsBox.setSpacing(2);
        int starCount = Math.min(5, Math.max(1, mission.getGoldReward() / 20)); // Stars based on reward
        for (int i = 0; i < starCount; i++) {
            Label star = new Label("★");
            star.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 14px;");
            starsBox.getChildren().add(star);
        }
        
        infoBox.getChildren().addAll(titleLabel, descLabel, starsBox);
        
        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Action button
        Button actionBtn = new Button();
        actionBtn.setPrefHeight(40);
        actionBtn.setPrefWidth(80);
        actionBtn.setStyle("-fx-background-radius: 10; -fx-font-weight: bold; -fx-font-size: 12px;");
        
        if (completed && claimed) {
            actionBtn.setText("DONE");
            actionBtn.setStyle(actionBtn.getStyle() + "-fx-background-color: #90EE90; -fx-text-fill: #228B22;");
            actionBtn.setDisable(true);
        } else if (completed && !claimed) {
            VBox rewardBox = new VBox();
            rewardBox.setAlignment(Pos.CENTER);
            rewardBox.setSpacing(2);
            
            Label getLabel = new Label("GET");
            getLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold;");
            
            HBox rewardInfo = new HBox();
            rewardInfo.setAlignment(Pos.CENTER);
            rewardInfo.setSpacing(5);
            
            Label goldReward = new Label(String.valueOf(mission.getGoldReward()));
            goldReward.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold;");
            
            Label goldIcon = new Label("🪙");
            goldIcon.setStyle("-fx-font-size: 12px;");
            
            rewardInfo.getChildren().addAll(goldReward, goldIcon);
            rewardBox.getChildren().addAll(getLabel, rewardInfo);
            
            actionBtn.setGraphic(rewardBox);
            actionBtn.setStyle(actionBtn.getStyle() + "-fx-background-color: #20B2AA; -fx-text-fill: white;");
            actionBtn.setOnAction(e -> {
                actionHandler.run();
                updatePlayerInfo();
                if (showingDailyMissions) {
                    loadDailyMissions();
                } else {
                    loadLongTermMissions();
                }
            });
        } else {
            actionBtn.setText("TODO");
            actionBtn.setStyle(actionBtn.getStyle() + "-fx-background-color: #CD853F; -fx-text-fill: white;");
            actionBtn.setOnAction(e -> {
                // Mark as completed (for demo purposes)
                if (showingDailyMissions) {
                    // For daily missions, you might want to implement actual completion logic
                    showAlert("Mission Progress", "Complete the required task to finish this mission!");
                } else {
                    // For long-term missions, you might want to implement actual completion logic
                    showAlert("Mission Progress", "Complete the required task to finish this mission!");
                }
            });
        }
        
        contentBox.getChildren().addAll(missionIcon, infoBox, spacer, actionBtn);
        
        if (!statusBox.getChildren().isEmpty()) {
            card.getChildren().addAll(statusBox, contentBox);
        } else {
            card.getChildren().add(contentBox);
        }
        
        return card;
    }
    
    private void handleDailyMissionAction(DailyAssignment assignment, Mission mission) {
        if (assignment.isCompleted() && !assignment.isClaimed()) {
            boolean success = dbHelper.claimDailyMissionReward(
                assignment.getIdAssignment(), 
                currentPlayer.getIdPlayer(), 
                mission.getGoldReward(), 
                mission.getExpReward()
            );
            
            if (success) {
                showAlert("Reward Claimed!", 
                    String.format("You received %d gold and %d exp!", 
                        mission.getGoldReward(), mission.getExpReward()));
            } else {
                showAlert("Error", "Failed to claim reward. Please try again.");
            }
        }
    }
    
    private void handleLongTermMissionAction(PlayerMission playerMission, Mission mission) {
        if (playerMission.isCompleted() && !playerMission.isClaimed()) {
            boolean success = dbHelper.claimLongTermMissionReward(
                playerMission.getIdPlayerMission(), 
                currentPlayer.getIdPlayer(), 
                mission.getGoldReward(), 
                mission.getExpReward()
            );
            
            if (success) {
                showAlert("Reward Claimed!", 
                    String.format("You received %d gold and %d exp!", 
                        mission.getGoldReward(), mission.getExpReward()));
            } else {
                showAlert("Error", "Failed to claim reward. Please try again.");
            }
        }
    }
    
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/view/MainMenu.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Monster Mates - Main Menu");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to return to main menu.");
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Method to set current player (can be called from other controllers)
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        updatePlayerInfo();
        
        // Generate daily missions if needed
        dbHelper.generateDailyMissions(currentPlayer.getIdPlayer());
        
        // Initialize long-term missions if needed
        if (dbHelper.getPlayerLongTermMissions(currentPlayer.getIdPlayer()).isEmpty()) {
            dbHelper.initializeLongTermMissions(currentPlayer.getIdPlayer());
        }
        
        // Show daily missions by default
        showDailyMissions();
    }
}