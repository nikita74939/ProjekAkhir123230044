package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/view/LoginPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/app/view/loginpage.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Monster Mates");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
