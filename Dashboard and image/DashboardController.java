import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private void handlePatientLogin() {
        openLoginPage("PatientLogin.fxml", "Patient Login");
    }

    @FXML
    private void handleDoctorLogin() {
        openLoginPage("DoctorLogin.fxml", "Doctor Login");
    }

    @FXML
    private void handleAdminLogin() {
        openLoginPage("AdminLogin.fxml", "Admin Login");
    }

    // Helper method to load the login page in a new window
    private void openLoginPage(String fxmlFile, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
