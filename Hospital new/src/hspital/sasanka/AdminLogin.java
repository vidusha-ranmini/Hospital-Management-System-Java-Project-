package hspital.sasanka;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminLogin extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLogin.fxml"));
        primaryStage.setTitle("Admin Login - Hospital Management System");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
