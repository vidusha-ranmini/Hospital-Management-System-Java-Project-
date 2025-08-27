package hospital.management.system.pkg2;

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
        
    }
    
    //admin login
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        //admin user name and password
        String adminUsername = "admin";
        String adminPassword = "password123";

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome to the Admin Dashboard!");
            
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
            Parent appointmentRoot = loader.load();
            Scene appointmentScene = new Scene(appointmentRoot);

            
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.setTitle("Admin Dashboard");
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
