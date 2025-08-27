package hospital.s;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PatientController implements Initializable {
    // Login Form Fields
    @FXML private VBox loginForm;
    @FXML private TextField loginNICField;
    @FXML private PasswordField loginPasswordField;
    @FXML private Label loginMessage;

    // Registration Form Fields
    @FXML private VBox registrationForm;  // Add the VBox for the registration form
    @FXML private TextField fullNameField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField nicField;
    @FXML private PasswordField regPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label registerMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (genderComboBox != null && genderComboBox.getItems().isEmpty()) {
            genderComboBox.getItems().addAll("Male", "Female", "Other");
        }
    }


    @FXML
    private void showRegistrationForm() {
        loginForm.setVisible(false);
        loginForm.setManaged(false);
        registrationForm.setVisible(true);
        registrationForm.setManaged(true);
    }

    @FXML
    private void showLoginForm() {
        registrationForm.setVisible(false);
        registrationForm.setManaged(false);
        loginForm.setVisible(true);
        loginForm.setManaged(true);
    }

    @FXML
    private void handleLogin() {
        String nic = loginNICField.getText();
        String password = loginPasswordField.getText();

        if (nic.isEmpty() || password.isEmpty()) {
            loginMessage.setText("Please enter both NIC and Password.");
        } if (Patient.login(nic, password)) {
            loginMessage.setText("Login successful!");
            try {
            // Load the Appointment.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Appointment.fxml"));
            Parent appointmentRoot = loader.load();
            
            AppointmentController appointmentController = loader.getController();
              appointmentController.setnic(nic);
            // Create a new scene and set it in the current stage
            Scene appointmentScene = new Scene(appointmentRoot);
            Stage currentStage = (Stage) loginForm.getScene().getWindow();
            currentStage.setScene(appointmentScene);
            currentStage.show();
        } catch (IOException e) {
            loginMessage.setText("Error loading Appointment page: " + e.getMessage());
        }
        } else {
            loginMessage.setText("Invalid NIC or Password.");
        }
        
        
    }

    @FXML
private void handleRegister() {
    String fullName = fullNameField.getText();
    String ageText = ageField.getText();
    String gender = genderComboBox.getValue();
    String nic = nicField.getText();
    String password = regPasswordField.getText();
    String confirmPassword = confirmPasswordField.getText();

    if (fullName.isEmpty() || ageText.isEmpty() || gender == null || nic.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
        registerMessage.setText("Please fill in all fields.");
    } else if (!password.equals(confirmPassword)) {
        registerMessage.setText("Passwords do not match.");
    } else {
        try {
            int age = Integer.parseInt(ageText); // Convert age to an integer
            Patient newPatient = new Patient(fullName, age, gender, nic, password);
            if (newPatient.register()) {
                registerMessage.setText("Registration successful!");
            } else {
                registerMessage.setText("Registration failed. Please try again.");
            }
        } catch (NumberFormatException e) {
            registerMessage.setText("Please enter a valid age.");
        }
    }
}

}
