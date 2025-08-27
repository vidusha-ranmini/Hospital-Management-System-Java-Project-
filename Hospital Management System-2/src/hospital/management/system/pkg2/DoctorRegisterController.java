/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.management.system.pkg2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Vidusha Ranmini
 */
public class DoctorRegisterController  {
    
    @FXML
    private TextField fullNameField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private ComboBox<String> specializationComboBox;

    @FXML
    private TextField mobileNumberField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private Button registerbtn;
    
    @FXML
    private void initialize(){
        
       genderComboBox.getItems().addAll("Male", "Female", "Other");
        
       specializationComboBox.getItems().addAll("Cardiology", "Neurology", "Pediatrics", "Orthopedics", "Dermatology", "General Practice");
    }
    
    private final AlertMessage alert = new AlertMessage(); 
    
    //doctor registration
    @FXML
    private void registerDoctor(){
        
        String fullName = fullNameField.getText();
        String gender = genderComboBox.getValue();
        String specialization = specializationComboBox.getValue();
        String mobileNumber = mobileNumberField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        
       if (fullName.isEmpty() || gender == null || specialization == null || mobileNumber.isEmpty()
            || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            alert.showAlert( AlertType.ERROR, "Form Error!","Please complete all fields");
            return;
        }
       
       if(!mobileNumber.matches("\\d{10}")){
          alert.showAlert( AlertType.ERROR,"Form Error!","Please enter a valid 10-digit mobile number"); 
          return;
       }
       
       DatabaseConnection dbConnection = new DatabaseConnection();
       Connection connection = dbConnection.connect();
       
       if (connection != null) {
            String insertSQL = "INSERT INTO doctors (full_name, gender, specialized, mobile_number, doctor_id, password, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                statement.setString(1, fullName);
                statement.setString(2, gender);
                statement.setString(3, specialization);
                statement.setString(4, mobileNumber);
                statement.setString(5, username);
                statement.setString(6, password);  
                statement.setString(7, email);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    alert.successMessage("Registration Successful");
                    clearForm();
                    showLoginPage();
                }
            } catch (SQLException e) {
                alert.errorMessage("Error occurred while registering the doctor.");
            } 
            
        }
       
    }
    
    //clear all fields
    private void clearForm() {
        fullNameField.clear();
        genderComboBox.getSelectionModel().clearSelection();
        specializationComboBox.getSelectionModel().clearSelection();
        mobileNumberField.clear();
        usernameField.clear();
        passwordField.clear();
        emailField.clear();
    }
    
    //show doctor login page
    private void showLoginPage(){
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorLogin.fxml"));
            Parent loginPageRoot = loader.load();

            Stage stage = (Stage) fullNameField.getScene().getWindow();
            stage.setScene(new Scene(loginPageRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            alert.showAlert(AlertType.ERROR, "Error", "Unable to load the login page.");
        }
    }
}
