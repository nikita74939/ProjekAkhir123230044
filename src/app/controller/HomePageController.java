package app.controller;

import app.helper.DatabaseHelper;
import app.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;
import javafx.application.Platform;

import java.util.List;
import java.util.ArrayList;

public class HomePageController {

    @FXML
    private Label labelNama;

    @FXML
    private Label labelLevel;

    @FXML
    private Label labelGold;

    @FXML
    private Label labelPetName;

    @FXML
    private Label labelPetLevel;

    @FXML
    private Label labelPetStats;

    @FXML
    private Button btnToko, btnMisi, btnFood, btnPlay, btnLogout, btnPrevPet, btnNextPet;

    @FXML
    private ImageView imgPet;

    private DatabaseHelper dbHelper;
    private Player currentPlayer;
    private ObservableList<Owner> playerFriends;
    private List<Monster> availableMonsters;
    private int currentFriendIndex = 0;

    // Default pet images
    private List<String> petImagePaths = List.of(
        "/assets/images/monsters/BigEgg.png",
        "/assets/images/monsters/BigPear.png",
        "/assets/images/monsters/LittleReaper.png",
        "/assets/images/monsters/Dragon.png",
        "/assets/images/monsters/Phoenix.png"
    );

    public void initialize() {
        dbHelper = DatabaseHelper.getInstance();
        
        // Setup button actions
        setupButtonActions();
        
        // Check database connection
        if (!dbHelper.isConnected()) {
            showAlert("Database Error", "Cannot connect to database!", Alert.AlertType.ERROR);
            return;
        }
        
        // Load available monsters
        loadAvailableMonsters();
    }

    private void setupButtonActions() {
        // Navigation buttons
        btnPrevPet.setOnAction(e -> previousPet());
        btnNextPet.setOnAction(e -> nextPet());
        
        // Action buttons
        btnFood.setOnAction(e -> feedPet());
        btnPlay.setOnAction(e -> playWithPet());
        btnToko.setOnAction(e -> openShop());
        btnMisi.setOnAction(e -> openMissions());
        btnLogout.setOnAction(e -> logout());
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        if (player != null) {
            updatePlayerInfo();
            loadPlayerFriends();
            generateDailyMissionsIfNeeded();
        }
    }

    private void updatePlayerInfo() {
        if (currentPlayer != null) {
            labelNama.setText(currentPlayer.getName());
            labelLevel.setText("Level " + currentPlayer.getLevel());
            labelGold.setText("Gold: " + currentPlayer.getGold());
        }
    }

    private void loadAvailableMonsters() {
        try {
            ObservableList<Monster> monsters = dbHelper.getAllMonsters();
            availableMonsters = new ArrayList<>(monsters);
        } catch (Exception e) {
            availableMonsters = new ArrayList<>();
            System.err.println("Error loading monsters: " + e.getMessage());
        }
    }

    private void loadPlayerFriends() {
        if (currentPlayer == null) return;
        
        try {
            playerFriends = dbHelper.getPlayerFriends(currentPlayer.getIdPlayer());
            
            if (playerFriends.isEmpty()) {
                // Player belum punya pet, tampilkan default atau pilih pet pertama
                showNoPetMessage();
            } else {
                currentFriendIndex = 0;
                displayCurrentPet();
            }
        } catch (Exception e) {
            System.err.println("Error loading player friends: " + e.getMessage());
            showAlert("Error", "Failed to load your pets!", Alert.AlertType.ERROR);
        }
    }

    private void showNoPetMessage() {
        labelPetName.setText("No Pet");
        labelPetLevel.setText("Get your first pet!");
        labelPetStats.setText("Visit shop to adopt a monster");
        
        // Load default image
        try {
            Image defaultImage = new Image(getClass().getResourceAsStream(petImagePaths.get(0)));
            imgPet.setImage(defaultImage);
        } catch (Exception e) {
            System.err.println("Error loading default pet image: " + e.getMessage());
        }
        
        // Disable pet interaction buttons
        btnFood.setDisable(true);
        btnPlay.setDisable(true);
        btnPrevPet.setDisable(true);
        btnNextPet.setDisable(true);
    }

    private void displayCurrentPet() {
        if (playerFriends == null || playerFriends.isEmpty()) {
            showNoPetMessage();
            return;
        }

        if (currentFriendIndex >= 0 && currentFriendIndex < playerFriends.size()) {
            Owner currentOwner = playerFriends.get(currentFriendIndex);
            Monster currentMonster = getMonsterById(currentOwner.getIdMonster());
            
            if (currentMonster != null) {
                labelPetName.setText(currentMonster.getName());
                labelPetLevel.setText("Level " + currentOwner.getLevel());
                labelPetStats.setText(String.format("Food: %d | Happy: %d | EXP: %d", 
                    currentOwner.getPointFood(), 
                    currentOwner.getPointHappy(), 
                    currentOwner.getPointLevelup()));
                
                // Load pet image
                loadPetImage(currentMonster.getIdMonster());
                
                // Enable interaction buttons
                btnFood.setDisable(false);
                btnPlay.setDisable(false);
                btnPrevPet.setDisable(playerFriends.size() <= 1);
                btnNextPet.setDisable(playerFriends.size() <= 1);
            }
        }
    }

    private Monster getMonsterById(int monsterId) {
        if (availableMonsters != null) {
            return availableMonsters.stream()
                .filter(m -> m.getIdMonster() == monsterId)
                .findFirst()
                .orElse(null);
        }
        return null;
    }

    private void loadPetImage(int monsterId) {
        try {
            // Use monster ID to determine image index
            int imageIndex = (monsterId - 1) % petImagePaths.size();
            String imagePath = petImagePaths.get(imageIndex);
            
            Image petImage = new Image(getClass().getResourceAsStream(imagePath));
            imgPet.setImage(petImage);
        } catch (Exception e) {
            System.err.println("Error loading pet image: " + e.getMessage());
            // Load default image as fallback
            try {
                Image defaultImage = new Image(getClass().getResourceAsStream(petImagePaths.get(0)));
                imgPet.setImage(defaultImage);
            } catch (Exception ex) {
                System.err.println("Error loading default image: " + ex.getMessage());
            }
        }
    }

    private void previousPet() {
        if (playerFriends != null && !playerFriends.isEmpty()) {
            currentFriendIndex = (currentFriendIndex - 1 + playerFriends.size()) % playerFriends.size();
            displayCurrentPet();
        }
    }

    private void nextPet() {
        if (playerFriends != null && !playerFriends.isEmpty()) {
            currentFriendIndex = (currentFriendIndex + 1) % playerFriends.size();
            displayCurrentPet();
        }
    }

    private void feedPet() {
        if (currentPlayer == null || playerFriends == null || playerFriends.isEmpty()) {
            showAlert("No Pet", "You don't have any pets to feed!", Alert.AlertType.WARNING);
            return;
        }

        try {
            Owner currentOwner = playerFriends.get(currentFriendIndex);
            
            // Check if player has food items in inventory
            ObservableList<Inventory> foodItems = dbHelper.getPlayerInventoryByType(
                currentPlayer.getIdPlayer(), "food");
            
            if (foodItems.isEmpty()) {
                showAlert("No Food", "You don't have any food items! Visit the shop to buy some.", 
                    Alert.AlertType.WARNING);
                return;
            }

            // Use first available food item
            Inventory foodItem = foodItems.get(0);
            Property foodProperty = dbHelper.getPropertyById(foodItem.getIdProperty());
            
            if (foodProperty != null) {
                // Use the food item
                if (dbHelper.useItem(currentPlayer.getIdPlayer(), foodItem.getIdProperty())) {
                    // Update pet stats
                    int newFoodPoints = currentOwner.getPointFood() + foodProperty.getPoint();
                    int newExp = currentOwner.getPointLevelup() + foodProperty.getExp();
                    int newLevel = currentOwner.getLevel();
                    
                    // Check for level up (every 100 exp points)
                    if (newExp >= 100) {
                        newLevel++;
                        newExp = newExp % 100;
                    }
                    
                    // Update in database
                    if (dbHelper.updateFriendStats(currentOwner.getIdOwner(), 
                            newFoodPoints, currentOwner.getPointHappy(), newExp, newLevel)) {
                        
                        // Update local data
                        currentOwner.setPointFood(newFoodPoints);
                        currentOwner.setPointLevelup(newExp);
                        currentOwner.setLevel(newLevel);
                        
                        // Refresh display
                        displayCurrentPet();
                        
                        showAlert("Success", String.format("Fed your pet with %s! (+%d food, +%d exp)", 
                            foodProperty.getName(), foodProperty.getPoint(), foodProperty.getExp()), 
                            Alert.AlertType.INFORMATION);
                    } else {
                        showAlert("Error", "Failed to update pet stats!", Alert.AlertType.ERROR);
                    }
                } else {
                    showAlert("Error", "Failed to use food item!", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            System.err.println("Error feeding pet: " + e.getMessage());
            showAlert("Error", "An error occurred while feeding your pet!", Alert.AlertType.ERROR);
        }
    }

    private void playWithPet() {
        if (currentPlayer == null || playerFriends == null || playerFriends.isEmpty()) {
            showAlert("No Pet", "You don't have any pets to play with!", Alert.AlertType.WARNING);
            return;
        }

        try {
            Owner currentOwner = playerFriends.get(currentFriendIndex);
            
            // Check if player has toy items in inventory
            ObservableList<Inventory> toyItems = dbHelper.getPlayerInventoryByType(
                currentPlayer.getIdPlayer(), "toy");
            
            if (toyItems.isEmpty()) {
                showAlert("No Toys", "You don't have any toys! Visit the shop to buy some.", 
                    Alert.AlertType.WARNING);
                return;
            }

            // Use first available toy item
            Inventory toyItem = toyItems.get(0);
            Property toyProperty = dbHelper.getPropertyById(toyItem.getIdProperty());
            
            if (toyProperty != null) {
                // Use the toy item
                if (dbHelper.useItem(currentPlayer.getIdPlayer(), toyItem.getIdProperty())) {
                    // Update pet stats
                    int newHappyPoints = currentOwner.getPointHappy() + toyProperty.getPoint();
                    int newExp = currentOwner.getPointLevelup() + toyProperty.getExp();
                    int newLevel = currentOwner.getLevel();
                    
                    // Check for level up (every 100 exp points)
                    if (newExp >= 100) {
                        newLevel++;
                        newExp = newExp % 100;
                    }
                    
                    // Update in database
                    if (dbHelper.updateFriendStats(currentOwner.getIdOwner(), 
                            currentOwner.getPointFood(), newHappyPoints, newExp, newLevel)) {
                        
                        // Update local data
                        currentOwner.setPointHappy(newHappyPoints);
                        currentOwner.setPointLevelup(newExp);
                        currentOwner.setLevel(newLevel);
                        
                        // Refresh display
                        displayCurrentPet();
                        
                        showAlert("Success", String.format("Played with your pet using %s! (+%d happiness, +%d exp)", 
                            toyProperty.getName(), toyProperty.getPoint(), toyProperty.getExp()), 
                            Alert.AlertType.INFORMATION);
                    } else {
                        showAlert("Error", "Failed to update pet stats!", Alert.AlertType.ERROR);
                    }
                } else {
                    showAlert("Error", "Failed to use toy item!", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            System.err.println("Error playing with pet: " + e.getMessage());
            showAlert("Error", "An error occurred while playing with your pet!", Alert.AlertType.ERROR);
        }
    }

    private void openShop() {
        try {
            // TODO: Implement navigation to shop scene
            System.out.println("Opening shop for player: " + currentPlayer.getName());
            showAlert("Shop", "Shop feature will be implemented soon!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            System.err.println("Error opening shop: " + e.getMessage());
        }
    }

    private void openMissions() {
        try {
            // TODO: Implement navigation to missions scene
            System.out.println("Opening missions for player: " + currentPlayer.getName());
            showAlert("Missions", "Missions feature will be implemented soon!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            System.err.println("Error opening missions: " + e.getMessage());
        }
    }

    private void generateDailyMissionsIfNeeded() {
        if (currentPlayer != null) {
            try {
                dbHelper.generateDailyMissions(currentPlayer.getIdPlayer());
                System.out.println("Daily missions generated/checked for player: " + currentPlayer.getName());
            } catch (Exception e) {
                System.err.println("Error generating daily missions: " + e.getMessage());
            }
        }
    }

    private void logout() {
        try {
            // TODO: Implement navigation back to login scene
            System.out.println("Logging out player: " + (currentPlayer != null ? currentPlayer.getName() : "Unknown"));
            
            // Clear current player data
            currentPlayer = null;
            playerFriends = null;
            currentFriendIndex = 0;
            
            showAlert("Logout", "You have been logged out successfully!", Alert.AlertType.INFORMATION);
            
            // Close application for now (should navigate to login scene)
            Platform.exit();
        } catch (Exception e) {
            System.err.println("Error during logout: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    // Utility method untuk refresh data player dari database
    public void refreshPlayerData() {
        if (currentPlayer != null) {
            try {
                Player updatedPlayer = dbHelper.getPlayerById(currentPlayer.getIdPlayer());
                if (updatedPlayer != null) {
                    this.currentPlayer = updatedPlayer;
                    updatePlayerInfo();
                }
            } catch (Exception e) {
                System.err.println("Error refreshing player data: " + e.getMessage());
            }
        }
    }

    // Method untuk menambahkan pet baru (dipanggil dari scene lain)
    public void addNewPet(int monsterId) {
        if (currentPlayer != null) {
            try {
                if (dbHelper.addPlayerFriend(currentPlayer.getIdPlayer(), monsterId)) {
                    loadPlayerFriends(); // Reload pets
                    showAlert("New Pet", "You've successfully adopted a new pet!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to adopt the pet!", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                System.err.println("Error adding new pet: " + e.getMessage());
                showAlert("Error", "An error occurred while adopting the pet!", Alert.AlertType.ERROR);
            }
        }
    }

    // Getter methods for other controllers
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public DatabaseHelper getDatabaseHelper() {
        return dbHelper;
    }
}