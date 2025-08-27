import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AppointmentController {

    @FXML
    private TextField patientNameField, doctorNameField, dateField, timeField;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> idColumn;

    @FXML
    private TableColumn<Appointment, String> patientNameColumn, doctorNameColumn, dateColumn, timeColumn;

    private ObservableList<Appointment> appointments;

    @FXML
    private void initialize() {
        appointments = FXCollections.observableArrayList(AppointmentDB.getAllAppointments());

        // Setup table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        appointmentTable.setItems(appointments);
    }

    @FXML
    private void handleAddAppointment() {
        Appointment newAppointment = new Appointment(0, patientNameField.getText(), doctorNameField.getText(), dateField.getText(), timeField.getText());
        AppointmentDB.addAppointment(newAppointment);
        refreshTable();
    }

    @FXML
    private void handleUpdateAppointment() {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            selectedAppointment.setPatientName(patientNameField.getText());
            selectedAppointment.setDoctorName(doctorNameField.getText());
            selectedAppointment.setDate(dateField.getText());
            selectedAppointment.setTime(timeField.getText());
            AppointmentDB.updateAppointment(selectedAppointment);
            refreshTable();
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
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an appointment to delete.");
        }
    }

    private void refreshTable() {
        appointments.setAll(AppointmentDB.getAllAppointments());
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
