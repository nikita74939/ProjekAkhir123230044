/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package app.controller;

import app.helper.DatabaseHelper;
import app.model.Player;
import app.model.Property;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author THINKPAD T470S
 */
public class ShopPageController implements Initializable {

    @FXML
    private Label goldLabel;
    
    @FXML
    private ScrollPane shopScrollPane;
    
    @FXML
    private GridPane shopGrid;
    
    @FXML
    private Button backButton;
    
    private DatabaseHelper dbHelper;
    private Player currentPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbHelper = DatabaseHelper.getInstance();
        setupShopGrid();
        setupEventHandlers();
    }
    
    /**
     * Setup shop grid
     */
    private void setupShopGrid() {
        shopGrid.setHgap(10);
        shopGrid.setVgap(10);
        shopGrid.setPadding(new Insets(10));
        shopScrollPane.setFitToWidth(true);
    }
    
    /**
     * Setup event handlers
     */
    private void setupEventHandlers() {
        backButton.setOnAction(e -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        });
    }
    
    /**
     * Set current player and load shop
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        updateGoldDisplay();
        loadShopItems();
    }
    
    /**
     * Update gold display
     */
    private void updateGoldDisplay() {
        if (currentPlayer != null) {
            // Refresh player data
            Player refreshedPlayer = dbHelper.getPlayerById(currentPlayer.getIdPlayer());
            if (refreshedPlayer != null) {
                currentPlayer = refreshedPlayer;
                goldLabel.setText("💰 " + currentPlayer.getGold() + " Gold");
            }
        }
    }
    
    /**
     * Load all shop items
     */
    private void loadShopItems() {
        shopGrid.getChildren().clear();
        ObservableList<Property> properties = dbHelper.getAllProperties();
        
        int col = 0;
        int row = 0;
        
        for (Property property : properties) {
            VBox itemCard = createItemCard(property);
            shopGrid.add(itemCard, col, row);
            
            col++;
            if (col >= 2) { // 2 columns
                col = 0;
                row++;
            }
        }
    }
    
    /**
     * Create item card for shop
     */
    private VBox createItemCard(Property property) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10));
        card.setPrefWidth(160);
        card.setPrefHeight(120);
        
        // Determine if player can afford this item
        boolean canAfford = currentPlayer != null && currentPlayer.getGold() >= property.getPrice();
        
        // Set card style based on affordability
        if (canAfford) {
            card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #3498db; " +
                         "-fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; " +
                         "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2); " +
                         "-fx-cursor: hand;");
        } else {
            card.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #e74c3c; " +
                         "-fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; " +
                         "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2); " +
                         "-fx-opacity: 0.7;");
        }
        
        // Item image
        ImageView imageView = createItemImage(property);
        
        // Item name
        Label nameLabel = new Label(property.getName());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(140);
        nameLabel.setAlignment(Pos.CENTER);
        
        // Price label
        Label priceLabel = new Label(property.getPrice() + " Gold");
        priceLabel.setFont(Font.font("System", FontWeight.BOLD, 11));
        
        // Set price color based on affordability
        if (canAfford) {
            priceLabel.setStyle("-fx-text-fill: #27ae60;");
        } else {
            priceLabel.setStyle("-fx-text-fill: #e74c3c;");
        }
        
        card.getChildren().addAll(imageView, nameLabel, priceLabel);
        
        // Add click handler if player can afford
        if (canAfford) {
            card.setOnMouseClicked(e -> buyItem(property));
            
            // Add hover effect
            card.setOnMouseEntered(e -> {
                card.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #2980b9; " +
                             "-fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; " +
                             "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 8, 0, 0, 3); " +
                             "-fx-cursor: hand; -fx-scale-x: 1.05; -fx-scale-y: 1.05;");
            });
            
            card.setOnMouseExited(e -> {
                card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #3498db; " +
                             "-fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; " +
                             "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2); " +
                             "-fx-cursor: hand; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
            });
        }
        
        return card;
    }
    
    /**
     * Create item image based on property type and name
     */
    private ImageView createItemImage(Property property) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        
        // Create placeholder image based on item type
        String imagePath = getItemImagePath(property);
        
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imageView.setImage(image);
        } catch (Exception e) {
            // Create text-based placeholder if image not found
            Label placeholder = new Label(getItemEmoji(property));
            placeholder.setFont(Font.font(30));
            placeholder.setPrefSize(50, 50);
            placeholder.setAlignment(Pos.CENTER);
            placeholder.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 25;");
        }
        
        return imageView;
    }
    
    /**
     * Get image path for item
     */
    private String getItemImagePath(Property property) {
        String type = property.getType().toLowerCase();
        String name = property.getName().toLowerCase().replace(" ", "_");
        return "/images/items/" + type + "/" + name + ".png";
    }
    
    /**
     * Get emoji for item type
     */
    private String getItemEmoji(Property property) {
        switch (property.getType().toLowerCase()) {
            case "food":
                return "🍖";
            case "toy":
                return "🎾";
            default:
                return "📦";
        }
    }
    
    /**
     * Buy item
     */
    private void buyItem(Property property) {
        if (currentPlayer == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "No player selected!");
            return;
        }
        
        if (currentPlayer.getGold() < property.getPrice()) {
            showAlert(Alert.AlertType.ERROR, "Insufficient Gold", 
                "You need " + property.getPrice() + " gold but only have " + currentPlayer.getGold() + " gold!");
            return;
        }
        
        // Process purchase
        boolean success = dbHelper.buyItem(currentPlayer.getIdPlayer(), property.getIdProperty(), 1);
        
        if (success) {
            // Show success message
            showSuccessMessage(property);
            
            // Update display
            updateGoldDisplay();
            loadShopItems(); // Reload to update affordability colors
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to purchase item. Please try again.");
        }
    }
    
    /**
     * Show success purchase message
     */
    private void showSuccessMessage(Property property) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Purchase Successful!");
        alert.setHeaderText("🎉 Item Purchased!");
        alert.setContentText("You bought " + property.getName() + " for " + property.getPrice() + " gold!");
        
        // Auto close after 1.5 seconds
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1.5), e -> alert.hide())
        );
        timeline.play();
        
        alert.show();
    }
    
    /**
     * Show alert dialog
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Get current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}