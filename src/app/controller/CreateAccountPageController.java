package app.controller;


import app.model.Player;
import app.helper.DatabaseHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountPageController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField textField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private DatabaseHelper dbHelper = new DatabaseHelper();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setOpacity(0.5);
        saveButton.setDisable(true);
        setupTextFieldValidation();
    }

    private void setupTextFieldValidation() {
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                textField.getStyleClass().remove("text-field-unfocused");
                textField.getStyleClass().add("text-field-focused");
            } else {
                textField.getStyleClass().remove("text-field-focused");
                textField.getStyleClass().add("text-field-unfocused");
            }
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isEmpty = newValue.trim().isEmpty();
            saveButton.setOpacity(isEmpty ? 0.5 : 1.0);
            saveButton.setDisable(isEmpty);
        });
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        String inputText = textField.getText().trim();

        if (inputText.isEmpty() || inputText.length() > 20) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Text must be filled and less than 20 characters.");
            textField.requestFocus();
            return;
        }

        try {
            boolean success = dbHelper.createPlayer(inputText);

            if (success) {
                ObservableList<Player> updatedList = dbHelper.getAllPlayers();
                Player newPlayer = updatedList.stream()
                        .filter(p -> p.getName().equals(inputText))
                        .findFirst()
                        .orElse(null);

                if (newPlayer != null) {
                    dbHelper.initializeLongTermMissions(newPlayer.getIdPlayer());
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Player '" + inputText + "' has been created successfully!");
                    // loadPlayersAndGenerateButtons(); // optional: implement this
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to retrieve newly created player!");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create new player. Please try again.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save data: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        performCancel();
    }

    private void performCancel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/view/LoginPage.fxml"));
            Parent root = loader.load();

            // Dapatkan stage saat ini dan tutup
            Stage currentStage = (Stage) cancelButton.getScene().getWindow();
            if (currentStage != null) {
                currentStage.close();
            }

            // Buat stage baru untuk LoginPage dan tampilkan
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/app/view/loginpage.css").toExternalForm());
            newStage.setScene(scene);
            newStage.setTitle("Login Page"); // Optional: berikan judul jendela
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
