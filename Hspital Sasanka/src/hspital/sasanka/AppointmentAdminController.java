package hspital.sasanka;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AppointmentController {

    @FXML
    private TextField patientNameField, doctorNameField, dateField, timeField,reasonField;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> idColumn;

    @FXML
    private TableColumn<Appointment, String> patientNameColumn, doctorNameColumn, dateColumn, timeColumn,reasonColumn;

    private ObservableList<Appointment> appointments;

    @FXML
    private void initialize() {
        // Initialize the appointments list with data from the database
        appointments = FXCollections.observableArrayList(AppointmentDB.getAllAppointments());

        // Set up table columns to match Appointment properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        // Load data into the table
        appointmentTable.setItems(appointments);

        // Populate text fields when a row is selected
        appointmentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
            }
        });
    }

    @FXML
    private void handleAddAppointment() {
        Appointment newAppointment = new Appointment(0, patientNameField.getText(), doctorNameField.getText(), dateField.getText(), timeField.getText(),reasonField.getText());
        AppointmentDB.addAppointment(newAppointment);
        refreshTable();
        handleClearFields();
    }

    @FXML
    private void handleUpdateAppointment() {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            selectedAppointment.setPatientName(patientNameField.getText());
            selectedAppointment.setDoctorName(doctorNameField.getText());
            selectedAppointment.setDate(dateField.getText());
            selectedAppointment.setTime(timeField.getText());
            selectedAppointment.setReason(reasonField.getText());
            AppointmentDB.updateAppointment(selectedAppointment);
            refreshTable();
            handleClearFields();
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an appointment to update.");
        }
    }

    @FXML
    private void handleDeleteAppointment() {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            AppointmentDB.deleteAppointment(selectedAppointment.getId());
            refreshTable();
            handleClearFields();
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an appointment to delete.");
        }
    }

    @FXML
    private void handleClearFields() {
        patientNameField.clear();
        doctorNameField.clear();
        dateField.clear();
        timeField.clear();
        reasonField.clear();
    }

    private void refreshTable() {
        appointments.setAll(AppointmentDB.getAllAppointments());
    }

    private void populateFields(Appointment appointment) {
        patientNameField.setText(appointment.getPatientName());
        doctorNameField.setText(appointment.getDoctorName());
        dateField.setText(appointment.getDate());
        timeField.setText(appointment.getTime());
        reasonField.setText(appointment.getTime());
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
