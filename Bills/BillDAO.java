// BillDAO.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private Connection connection;

    public BillDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to create a new bill
    public void createBill(Bill bill) throws SQLException {
        String query = "INSERT INTO Bills (appointment_id, patient_id, doctor_id, date, amount, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bill.getAppointmentId());
            statement.setInt(2, bill.getPatientId());
            statement.setInt(3, bill.getDoctorId());
            statement.setDate(4, new java.sql.Date(bill.getDate().getTime()));
            statement.setDouble(5, bill.getAmount());
            statement.setString(6, bill.getStatus());
            statement.executeUpdate();
        }
    }

    // Method to retrieve all bills
    public List<Bill> getAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM Bills";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setBillId(resultSet.getInt("bill_id"));
                bill.setAppointmentId(resultSet.getInt("appointment_id"));
                bill.setPatientId(resultSet.getInt("patient_id"));
                bill.setDoctorId(resultSet.getInt("doctor_id"));
                bill.setDate(resultSet.getDate("date"));
                bill.setAmount(resultSet.getDouble("amount"));
                bill.setStatus(resultSet.getString("status"));
                bills.add(bill);
            }
        }
        return bills;
    }

    // Method to update a bill
    public void updateBill(Bill bill) throws SQLException {
        String query = "UPDATE Bills SET amount = ?, status = ? WHERE bill_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, bill.getAmount());
            statement.setString(2, bill.getStatus());
            statement.setInt(3, bill.getBillId());
            statement.executeUpdate();
        }
    }

    // Method to delete a bill
    public void deleteBill(int billId) throws SQLException {
        String query = "DELETE FROM Bills WHERE bill_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, billId);
            statement.executeUpdate();
        }
    }
}
