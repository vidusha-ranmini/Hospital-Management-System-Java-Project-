// BillController.java
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class BillController {

    @FXML private TableView<Bill> billTable;
    @FXML private TextField appointmentIdField;
    @FXML private TextField patientIdField;
    @FXML private TextField doctorIdField;
    @FXML private TextField amountField;
    @FXML private TextField statusField;

    private BillDAO billDAO;

    public BillController(BillDAO billDAO) {
        this.billDAO = billDAO;
    }

    @FXML
    public void initialize() {
        loadBills();
    }

    // Load all bills into the table
    private void loadBills() {
        try {
            billTable.getItems().setAll(billDAO.getAllBills());
        } catch (SQLException e) {
            showAlert("Error", "Unable to load bills.");
        }
    }

    // Create a new bill
    @FXML
    public void createBill() {
        Bill bill = new Bill();
        bill.setAppointmentId(Integer.parseInt(appointmentIdField.getText()));
        bill.setPatientId(Integer.parseInt(patientIdField.getText()));
        bill.setDoctorId(Integer.parseInt(doctorIdField.getText()));
        bill.setAmount(Double.parseDouble(amountField.getText()));
        bill.setStatus(statusField.getText());
        try {
            billDAO.createBill(bill);
            loadBills();
        } catch (SQLException e) {
            showAlert("Error", "Unable to create bill.");
        }
    }

    // Update the selected bill
    @FXML
    public void updateBill() {
        Bill bill = billTable.getSelectionModel().getSelectedItem();
        if (bill != null) {
            bill.setAmount(Double.parseDouble(amountField.getText()));
            bill.setStatus(statusField.getText());
            try {
                billDAO.updateBill(bill);
                loadBills();
            } catch (SQLException e) {
                showAlert("Error", "Unable to update bill.");
            }
        }
    }

    // Delete the selected bill
    @FXML
    public void deleteBill() {
        Bill bill = billTable.getSelectionModel().getSelectedItem();
        if (bill != null) {
            try {
                billDAO.deleteBill(bill.getBillId());
                loadBills();
            } catch (SQLException e) {
                showAlert("Error", "Unable to delete bill.");
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
