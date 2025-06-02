package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class HomePageController {

    @FXML
    private Label labelNama;

    @FXML
    private Label labelLevel;

    @FXML
    private Label labelGold;

    @FXML
    private Button btnToko, btnMisi, btnFood, btnPlay, btnLogout, btnPrevPet, btnNextPet;

    @FXML
    private ImageView imgPet;

    private List<Image> daftarPet;
    private int indeksPet = 0;

    private String namaPemain = "Niki";
    private int level = 5;
    private int gold = 1234;

    public void initialize() {
        // set nama, level, dan gold
        labelNama.setText(namaPemain);
        labelLevel.setText("Level " + level);
        labelGold.setText("Gold: " + gold);

        // load gambar pet
        daftarPet = List.of(
            new Image(getClass().getResourceAsStream("/assets/images/monsters/BigEgg.png")),
            new Image(getClass().getResourceAsStream("/assets/images/monsters/BigPear.png")),
            new Image(getClass().getResourceAsStream("/assets/images/monsters/LittleReaper.png"))
        );

        tampilkanPet(indeksPet);

        // tombol navigasi pet
        btnPrevPet.setOnAction(e -> gantiPet(-1));
        btnNextPet.setOnAction(e -> gantiPet(1));

        // tombol aksi
        btnFood.setOnAction(e -> kasihMakan());
        btnPlay.setOnAction(e -> ajakMain());
        btnToko.setOnAction(e -> bukaToko());
        btnMisi.setOnAction(e -> bukaMisi());
        btnLogout.setOnAction(e -> logout());
    }

    private void tampilkanPet(int index) {
        if (index >= 0 && index < daftarPet.size()) {
            imgPet.setImage(daftarPet.get(index));
        }
    }

    private void gantiPet(int arah) {
        indeksPet += arah;
        if (indeksPet < 0) {
            indeksPet = daftarPet.size() - 1;
        } else if (indeksPet >= daftarPet.size()) {
            indeksPet = 0;
        }
        tampilkanPet(indeksPet);
    }

    private void kasihMakan() {
        System.out.println("Memberi makan pet...");
        // tambahkan logic kasih makan
    }

    private void ajakMain() {
        System.out.println("Mengajak pet bermain...");
        // tambahkan logic main
    }

    private void bukaToko() {
        System.out.println("Membuka toko...");
        // ganti scene ke toko
    }

    private void bukaMisi() {
        System.out.println("Membuka misi...");
        // ganti scene ke misi
    }

    private void logout() {
        System.out.println("Logout...");
        // kembali ke halaman login
    }
}
