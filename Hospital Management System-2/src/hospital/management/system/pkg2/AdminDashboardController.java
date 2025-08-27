/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.management.system.pkg2;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Vidusha Ranmini
 */
public class AdminDashboardController {
    
    @FXML
    private Button appointmentbtn;

    @FXML
    private Button billsbtn;
    
    @FXML
    private Button backbtn;
    
    @FXML
    private AnchorPane imageview;
    
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("/hospital2.jpg");  
        Image img = new Image(file.toURI().toString());  
         
    }
    
    
    @FXML
    private void openBills() {
        try {
            
            Stage currentStage = (Stage) billsbtn.getScene().getWindow();
            currentStage.close();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Bill.fxml"));
            Parent root = loader.load();

            
            Stage stage = new Stage();
            stage.setTitle("Bill Management");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void openAppointments() {
        try {
            
            Stage currentStage = (Stage) appointmentbtn.getScene().getWindow();
            currentStage.close();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentAdmin.fxml"));
            Parent root = loader.load();

            
            Stage stage = new Stage();
            stage.setTitle("Appointment Management");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void gotoDashboard() {
        try {
            
            Stage currentStage = (Stage) backbtn.getScene().getWindow();
            currentStage.close();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();

            
            Stage stage = new Stage();
            stage.setTitle("Hospital management System");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
