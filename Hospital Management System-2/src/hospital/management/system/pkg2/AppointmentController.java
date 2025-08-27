package hospital.management.system.pkg2;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class AppointmentController {

    @FXML private Label patientNameLabel;
    @FXML private DatePicker appointmentDate;
    @FXML private ComboBox<String> appointmentTime;
    @FXML private ComboBox<String> doctorSelector;
    @FXML private TextArea reasonText;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> dateColumn;
    @FXML private TableColumn<Appointment, String> timeColumn;
    @FXML private TableColumn<Appointment, String> doctorColumn;
    @FXML private TableColumn<Appointment, String> reasonColumn;
    @FXML private TableColumn<Appointment, String> statusColumn;

    private final ObservableList<Appointment> appointmentData = FXCollections.observableArrayList();
    private String nic;
   


    // Database connection variables
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public void initialize() {
        
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        
        loadAppointments();
        
        appointmentTable.setOnMouseClicked(event -> {
        if (event.getClickCount() == 1) { 
            Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                populateFields(selected); 
            }
        }
    });
    }
    private void populateFields(Appointment appointment) {
    appointmentDate.setValue(appointment.getDate());
    appointmentTime.setValue(appointment.getTime());
    doctorSelector.setValue(appointment.getDoctor());
    reasonText.setText(appointment.getReason());
}

    public void setnic(String nic) {
        this.nic = nic;
        loadAppointments(); 
    }

    private void loadAppointments() {
        appointmentData.clear();
        String sql = "SELECT * FROM appointment WHERE nic = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nic);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment(
                    rs.getInt("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("time"),
                    rs.getString("doctor"),
                    rs.getString("reason"),
                    rs.getString("status"),
                    rs.getString("nic")
                );
                appointmentData.add(appointment);
            }
            appointmentTable.setItems(appointmentData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddAppointment() {
        
        String sql = "INSERT INTO appointment (date, time, doctor, reason, status,nic) VALUES (?, ?, ?, ?, ?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(appointmentDate.getValue()));
            stmt.setString(2, appointmentTime.getValue());
            stmt.setString(3, doctorSelector.getValue());
            stmt.setString(4, reasonText.getText());
            stmt.setString(5, "Scheduled"); 
            stmt.setString(6, nic );
            stmt.executeUpdate();

            loadAppointments(); 
            handleClearForm();
            showAlert(AlertType.INFORMATION, "Appointment Added", "The appointment has been added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
             showAlert(AlertType.ERROR, "Database Error", "Failed to add the appointment: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateAppointment() {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        String sql = "UPDATE appointment SET date = ?, time = ?, doctor = ?, reason = ?, status = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(appointmentDate.getValue()));
            stmt.setString(2, appointmentTime.getValue());
            stmt.setString(3, doctorSelector.getValue());
            stmt.setString(4, reasonText.getText());
            stmt.setString(5, "Updated"); 
            stmt.setInt(6, selected.getId());
            stmt.executeUpdate();

            loadAppointments(); 
            handleClearForm();
            showAlert(AlertType.INFORMATION, "Appointment Updated", "The appointment has been updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "Failed to update the appointment: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteAppointment() {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        String sql = "DELETE FROM appointment WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, selected.getId());
            stmt.executeUpdate();

            loadAppointments(); 
             showAlert(AlertType.INFORMATION, "Appointment Deleted", "The appointment has been deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "Failed to update the appointment: " + e.getMessage());
        }
    }

    @FXML
    private void handleClearForm() {
        appointmentDate.setValue(null);
        appointmentTime.setValue(null);
        doctorSelector.setValue(null);
        reasonText.clear();
    }

    @FXML
private void handleLogout() {
    try {
        // Close the current appointment stage
        Stage currentStage = (Stage) appointmentDate.getScene().getWindow();
        currentStage.close();

        // Load the Dashboard.fxml file 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent dashboardRoot = loader.load();

        // Create a new stage for the dashboard
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.show();
    } catch (IOException e) {
        showAlert(AlertType.ERROR, "Error", "Failed to load the Dashboard page: " + e.getMessage());
        e.printStackTrace();
    }
}


    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
