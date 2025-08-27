package hospital.management.system.pkg2;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private ImageView image;

    
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("/hospital.jpg");  
        Image img = new Image(file.toURI().toString());  
        image.setImage(img); 
    }

    @FXML
    private Button patientbtn;

    @FXML
    private Button doctorbtn;

    @FXML
    private Button adminbtn;

    @FXML
    private void handlePatientLogin() {
        openLoginPage("Patient.fxml", "Patient Login");
        
    }

    @FXML
    private void handleDoctorLogin() {
        openLoginPage("DoctorLogin.fxml", "Doctor Login");
    }

    @FXML
    private void handleAdminLogin() {
        openLoginPage("AdminLogin.fxml", "Admin Login");
    }

   
    private void openLoginPage(String fxmlFile, String title) {
        try {
            
            Stage currentStage = (Stage) patientbtn.getScene().getWindow();
            currentStage.close();
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
    }
         catch (Exception e) {
            e.printStackTrace();
        }
    }
}

