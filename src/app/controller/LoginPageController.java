package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.effect.DropShadow;

import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class LoginPageController {

    @FXML
    private AnchorPane rootPane;

    private List<String> akunTerdaftar = List.of("Nikita", "Niki", "Nikita 2"); 

    public void initialize() {
        generateButtons();
    }

    private void generateButtons() {
        Label welcomeLabel = new Label("WELCOME");
        welcomeLabel.setFont(Font.font("Comic Sans MS", javafx.scene.text.FontWeight.BOLD, 36));
        welcomeLabel.setTextFill(Color.WHITE);
        welcomeLabel.setLayoutX(100); // sesuaikan biar di tengah, tergantung panjang teks
        welcomeLabel.setLayoutY(80);
        welcomeLabel.setTextAlignment(TextAlignment.CENTER);
        welcomeLabel.setEffect(new DropShadow());

        rootPane.getChildren().add(welcomeLabel);
        int jumlahAkun = akunTerdaftar.size();
        int jumlahButton = Math.min(4, jumlahAkun == 4 ? 4 : jumlahAkun + 1);

        double startY = 180;
        double spacing = 80;

        for (int i = 0; i < jumlahButton; i++) {
            Button btn = new Button();
            btn.setPrefWidth(250);
            btn.setPrefHeight(60);
            btn.setLayoutX(75);
            btn.setLayoutY(startY + i * spacing);

            // font joy & bold
            btn.setFont(Font.font("Comic Sans MS", javafx.scene.text.FontWeight.BOLD, 20));

            // style: item transparan + teks putih + rounded corner
            btn.setStyle(
                "-fx-background-color: rgba(0,0,0,0.2); " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-radius: 12px;"
            );

            // biar ada efek bayangan biar makin pop
            btn.setEffect(new DropShadow());
            
            btn.setOnMouseEntered(e -> {
            btn.setScaleX(1.05);
            btn.setScaleY(1.05);
            btn.setStyle(
                "-fx-background-color: rgba(255,255,255,0.2); " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-radius: 12px;"
            );
        });

        btn.setOnMouseExited(e -> {
            btn.setScaleX(1.0);
            btn.setScaleY(1.0);
            btn.setStyle(
                "-fx-background-color: rgba(0,0,0,0.2); " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-radius: 12px;"
            );
        });



            if (i < jumlahAkun) {
                String akun = akunTerdaftar.get(i);
                btn.setText(akun);
                int finalI = i;
                btn.setOnAction(e -> masukKeGame(akunTerdaftar.get(finalI)));
            } else {
                btn.setText("New Player");
                btn.setOnAction(e -> buatAkunBaru());
            }

            rootPane.getChildren().add(btn);
        }
    }

    private void masukKeGame(String akun) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/view/HomePage.fxml"));
        // ganti scene atau panggil logic game
    }

    private void buatAkunBaru() {
        System.out.println("Menuju halaman pendaftaran akun...");
        // ganti scene ke form registrasi
    }
}
