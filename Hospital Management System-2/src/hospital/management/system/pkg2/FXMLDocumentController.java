/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.management.system.pkg2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Vidusha Ranmini
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;

    @FXML
    private TextField doctorusername;

    @FXML
    private PasswordField doctorpassword;

    @FXML
    private Button loginbtn;

    @FXML
     private Hyperlink registerhere;
    
    private final AlertMessage alert = new AlertMessage();
    
    //doctor login
    public void doctorlogin(ActionEvent event) throws SQLException{
        String username = doctorusername.getText();
        String password = doctorpassword.getText();
        
        if(validateLogin(username,password)){
            alert.successMessage("Login Successful");
            showPatientnotes();
        }else{
            alert.errorMessage("Invalid username or password");
        }
    }
    
    private DatabaseConnection dbConnection = new DatabaseConnection();
    private String doctorName;
    
    private boolean validateLogin(String username, String password) throws SQLException{
        String query = "SELECT * FROM doctors WHERE doctor_id = ? AND  password = ?";
        Connection connect = dbConnection.connect();
        
        try(PreparedStatement pstmt = connect.prepareStatement(query)){
            pstmt.setString(1,username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
                doctorName = rs.getString("full_name");
                if (doctorName == null || doctorName.trim().isEmpty()) {
                doctorName = "Doctor"; 
            }
                return true;
            }
            
        }catch(SQLException e){
            e.printStackTrace();
       } 
        return false;
    }
    
    
    //show doctor registration page
    @FXML
    private void showRegistrationPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorRegister.fxml"));
            Parent registrationPageRoot = loader.load();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registrationPageRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the registration page.");
        }
    }
    
    //show patient notes page
    private void showPatientnotes(){
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientNotes.fxml"));
            Parent loginPageRoot = loader.load();
            
            PatientNotesController patientNotesController = loader.getController();
            patientNotesController.setDoctorName(doctorName); 
            Stage stage = (Stage) doctorusername.getScene().getWindow();
            stage.setScene(new Scene(loginPageRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            alert.showAlert(Alert.AlertType.ERROR, "Error", "Unable to load the Patient Notes page.");
        }
    }

   @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (registerhere == null) {
            System.out.println("registerhere is not connected.");
        }
    }

}
