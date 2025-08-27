package hspital.sasanka;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        // Optional: Initialize any components if necessary
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Sample credentials for demonstration
        String adminUsername = "admin";
        String adminPassword = "password123";

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome to the Admin Dashboard!");
            
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Appointment.fxml"));
            Parent appointmentRoot = loader.load();
            Scene appointmentScene = new Scene(appointmentRoot);

            // Get the current stage (the window) and set the new scene
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.setScene(appointmentScene);
            currentStage.show();

        }catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the Appointment page.");
        }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
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
