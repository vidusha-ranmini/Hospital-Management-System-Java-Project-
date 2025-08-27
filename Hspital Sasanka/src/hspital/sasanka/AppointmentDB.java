package hspital.sasanka;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDB {

    private static final String URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Retrieve all appointments from the database
    public static List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointment";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Appointment appointment = new Appointment(
                    rs.getInt("id"),
                    rs.getString("patient_name"),
                    rs.getString("doctor"),
                    rs.getString("date"),
                    rs.getString("time"),
                    rs.getString("reason")
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    // Add a new appointment to the database
    public static void addAppointment(Appointment appointment) {
        String query = "INSERT INTO appointment (patient_name, doctor, date, time,reason) VALUES (?, ?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, appointment.getPatientName());
            pstmt.setString(2, appointment.getDoctorName());
            pstmt.setString(3, appointment.getDate());
            pstmt.setString(4, appointment.getTime());
            pstmt.setString(5, appointment.getReason());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing appointment in the database
    public static void updateAppointment(Appointment appointment) {
        String query = "UPDATE appointment SET patient_name = ?, doctor = ?, date = ?, time = ? ,reason = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, appointment.getPatientName());
            pstmt.setString(2, appointment.getDoctorName());
            pstmt.setString(3, appointment.getDate());
            pstmt.setString(4, appointment.getTime());
            pstmt.setString(5, appointment.getReason());
            pstmt.setInt(6, appointment.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an appointment from the database
    public static void deleteAppointment(int id) {
        String query = "DELETE FROM appointment WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
