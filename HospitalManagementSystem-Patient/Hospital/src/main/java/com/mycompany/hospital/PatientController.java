package com.mycompany.hospital;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

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
        } else if (nic.equals("testNIC") && password.equals("testPass")) {  // Sample credentials
            loginMessage.setText("Login successful!");
            // Proceed with login logic
        } else {
            loginMessage.setText("Invalid NIC or Password.");
        }
    }

    @FXML
    private void handleRegister() {
        String fullName = fullNameField.getText();
        String age = ageField.getText();
        String gender = genderComboBox.getValue();
        String nic = nicField.getText();
        String password = regPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (fullName.isEmpty() || age.isEmpty() || gender == null || nic.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            registerMessage.setText("Please fill in all fields.");
        } else if (!password.equals(confirmPassword)) {
            registerMessage.setText("Passwords do not match.");
        } else {
            registerMessage.setText("Registration successful!");
            // Further logic for storing user details, etc.
        }
    }
}
