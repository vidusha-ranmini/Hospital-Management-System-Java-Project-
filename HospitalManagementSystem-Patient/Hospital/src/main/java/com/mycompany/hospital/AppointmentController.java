package com.mycompany.hospital;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;
import java.time.LocalDate;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppointmentController implements Initializable {
    
    @FXML private Label patientNameLabel;
    @FXML private DatePicker appointmentDate;
    @FXML private ComboBox<String> appointmentTime;
    @FXML private ComboBox<String> doctorSelector;
    @FXML private TextArea reasonText;
    
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, LocalDate> dateColumn;
    @FXML private TableColumn<Appointment, String> timeColumn;
    @FXML private TableColumn<Appointment, String> doctorColumn;
    @FXML private TableColumn<Appointment, String> reasonColumn;
    @FXML private TableColumn<Appointment, String> statusColumn;

    private String patientNIC;
    private String patientName;
    private ObservableList<Appointment> appointments;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointments = FXCollections.observableArrayList();
        
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        appointmentTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    populateForm(newValue);
                }
            }
        );

        appointmentDate.setValue(LocalDate.now());

        loadAppointments();
    }

    public void setPatientInfo(String nic, String name) {
        this.patientNIC = nic;
        this.patientName = name;
        patientNameLabel.setText("Welcome, " + name);
        loadAppointments();
    }

    @FXML
    private void handleAddAppointment() {
        if (validateInput()) {
            try {
                Appointment newAppointment = new Appointment(
                    appointmentDate.getValue(),
                    appointmentTime.getValue(),
                    doctorSelector.getValue(),
                    reasonText.getText(),
                    "Pending",
                    patientNIC
                );
                
                if (newAppointment.saveToDatabase()) {
                    loadAppointments();
                    clearForm();
                    showAlert(AlertType.INFORMATION, "Success", "Appointment added successfully!");
                }
            } catch (Exception e) {
                showAlert(AlertType.ERROR, "Error", "Failed to add appointment: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleUpdateAppointment() {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(AlertType.WARNING, "No Selection", "Please select an appointment to update.");
            return;
        }

        if (validateInput()) {
            try {
                selected.setDate(appointmentDate.getValue());
                selected.setTime(appointmentTime.getValue());
                selected.setDoctor(doctorSelector.getValue());
                selected.setReason(reasonText.getText());

                if (selected.updateInDatabase()) {
                    loadAppointments();
                    clearForm();
                    showAlert(AlertType.INFORMATION, "Success", "Appointment updated successfully!");
                }
            } catch (Exception e) {
                showAlert(AlertType.ERROR, "Error", "Failed to update appointment: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleDeleteAppointment() {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(AlertType.WARNING, "No Selection", "Please select an appointment to delete.");
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete this appointment?");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            if (selected.deleteFromDatabase()) {
                loadAppointments();
                clearForm();
                showAlert(AlertType.INFORMATION, "Success", "Appointment deleted successfully!");
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to delete appointment.");
            }
        }
    }

    @FXML
    private void handleClearForm() {
        clearForm();
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Patient.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) patientNameLabel.getScene().getWindow();
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Error", "Could not load login page.");
        }
    }

    private void loadAppointments() {
        appointments.clear();
        appointments.addAll(Appointment.loadAppointmentsForPatient(patientNIC));
        appointmentTable.setItems(appointments);
    }

    private boolean validateInput() {
        if (appointmentDate.getValue() == null) {
            showAlert(AlertType.WARNING, "Validation Error", "Please select a date.");
            return false;
        }
        if (appointmentTime.getValue() == null) {
            showAlert(AlertType.WARNING, "Validation Error", "Please select a time.");
            return false;
        }
        if (doctorSelector.getValue() == null) {
            showAlert(AlertType.WARNING, "Validation Error", "Please select a doctor.");
            return false;
        }
        if (reasonText.getText().trim().isEmpty()) {
            showAlert(AlertType.WARNING, "Validation Error", "Please enter a reason.");
            return false;
        }
        
        // Check if date is not in the past
        if (appointmentDate.getValue().isBefore(LocalDate.now())) {
            showAlert(AlertType.WARNING, "Validation Error", "Please select a future date.");
            return false;
        }
        
        return true;
    }

    private void clearForm() {
        appointmentDate.setValue(LocalDate.now());
        appointmentTime.setValue(null);
        doctorSelector.setValue(null);
        reasonText.clear();
        appointmentTable.getSelectionModel().clearSelection();
    }

    private void populateForm(Appointment appointment) {
        appointmentDate.setValue(appointment.getDate());
        appointmentTime.setValue(appointment.getTime());
        doctorSelector.setValue(appointment.getDoctor());
        reasonText.setText(appointment.getReason());
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}