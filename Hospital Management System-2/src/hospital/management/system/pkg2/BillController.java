package hospital.management.system.pkg2;



import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BillController {

    @FXML
    private DatePicker datepicker;

    @FXML
    private TextField patientid;

    @FXML
    private TextField patientname;

    @FXML
    private TextField doctorname;

    @FXML
    private TextArea servicetext;

    @FXML
    private TextField amount;
    
    @FXML
    private TableView<Bill> billtable;

    @FXML
    private TableColumn<Bill, Integer> billidcolumn;

    @FXML
    private TableColumn<Bill, Integer> patientidcolumn;

    @FXML
    private TableColumn<Bill, String> patientnamecolumn;

    @FXML
    private TableColumn<Bill, String> doctornamecolumn;

    @FXML
    private TableColumn<Bill, String> servicecolumn;

    @FXML
    private TableColumn<Bill, Double> amountcolumn;

    @FXML
    private TableColumn<Bill, String> statuscolumn;
    
    @FXML
    private TableColumn<Bill, Timestamp> datecolumn;

    private Connection connection;

    private final AlertMessage alert = new AlertMessage();
    
    public BillController() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        initializeTable();
        loadBills();
        
        // Set up listener for table row selection
        billtable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }

    private void initializeTable() {
        billidcolumn.setCellValueFactory(new PropertyValueFactory<>("billId"));
        patientidcolumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        patientnamecolumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        doctornamecolumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        servicecolumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        amountcolumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        statuscolumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("billDate"));
    }

    //insert bill
    @FXML
    private void insertBill() {
        try {
            String sql = "INSERT INTO hospital_bills (patient_id, patient_name, doctor_name, service_description, total_amount, status, bill_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(patientid.getText()));
            pstmt.setString(2, patientname.getText());
            pstmt.setString(3, doctorname.getText());
            pstmt.setString(4, servicetext.getText());
            pstmt.setDouble(5, Double.parseDouble(amount.getText()));
            pstmt.setString(6, "Generated");
            pstmt.setTimestamp(7, Timestamp.valueOf(datepicker.getValue().atStartOfDay()));
            pstmt.executeUpdate();
            loadBills();
            alert.successMessage("Bill has been added successfully.");
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update bill
    @FXML
    private void updateBill() {
        Bill selectedBill = billtable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            try {
                String sql = "UPDATE hospital_bills SET patient_id = ?, patient_name = ?, doctor_name = ?, service_description = ?, total_amount = ?, status = ?, bill_date = ? WHERE bill_id = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, Integer.parseInt(patientid.getText()));
                pstmt.setString(2, patientname.getText());
                pstmt.setString(3, doctorname.getText());
                pstmt.setString(4, servicetext.getText());
                pstmt.setDouble(5, Double.parseDouble(amount.getText()));
                pstmt.setString(6, "Updated");
                pstmt.setTimestamp(7, Timestamp.valueOf(datepicker.getValue().atStartOfDay()));
                pstmt.setInt(8, selectedBill.getBillId());
                pstmt.executeUpdate();
                loadBills();
                alert.successMessage("Bill has been updated.");
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //delete bill
    @FXML
    private void deleteBill() {
        Bill selectedBill = billtable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            try {
                String sql = "DELETE FROM hospital_bills WHERE bill_id = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, selectedBill.getBillId());
                pstmt.executeUpdate();
                loadBills();
                alert.successMessage("Bill has been deleted.");
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //clear all fields
    @FXML
    private void clearFields() {
        datepicker.setValue(null);
        patientid.clear();
        patientname.clear();
        doctorname.clear();
        servicetext.clear();
        amount.clear();
    }

    //load bill into table
    private void loadBills() {
        List<Bill> bills = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM hospital_bills");
            while (rs.next()) {
                bills.add(new Bill(
                        rs.getInt("bill_id"),
                        rs.getInt("patient_id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_name"),
                        rs.getString("service_description"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        rs.getTimestamp("bill_date").toLocalDateTime().toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        billtable.getItems().setAll(bills);
    }

    private void populateFields(Bill bill) {
        patientid.setText(String.valueOf(bill.getPatientId()));
        patientname.setText(bill.getPatientName());
        doctorname.setText(bill.getDoctorName());
        servicetext.setText(bill.getService());
        amount.setText(String.valueOf(bill.getAmount()));
        datepicker.setValue(bill.getBillDate());
    }

    //goto admin dashboard
    @FXML
    private void goToAdminDashboard() {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
       
        Stage stage = (Stage) billtable.getScene().getWindow();
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
    } catch (IOException e) {
        e.printStackTrace();
        showAlert("Error", "Could not navigate to the Admin Dashboard.");
    }
}


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
