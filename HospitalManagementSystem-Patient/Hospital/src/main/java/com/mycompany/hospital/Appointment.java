package com.mycompany.hospital;

import java.time.LocalDate;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointment {
    private int id;
    private LocalDate date;
    private String time;
    private String doctor;
    private String reason;
    private String status;
    private String patientNIC;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital"; // Change to your database name
    private static final String DB_USER = "root"; // Change to your database username
    private static final String DB_PASSWORD = ""; // Change to your database password

    public Appointment(LocalDate date, String time, String doctor, String reason, String status, String patientNIC) {
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.reason = reason;
        this.status = status;
        this.patientNIC = patientNIC;
    }

    public Appointment(int id, LocalDate date, String time, String doctor, String reason, String status, String patientNIC) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.reason = reason;
        this.status = status;
        this.patientNIC = patientNIC;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPatientNIC() { return patientNIC; }
    public void setPatientNIC(String patientNIC) { this.patientNIC = patientNIC; }

    public boolean saveToDatabase() {
        String sql = "INSERT INTO appointment (date, time, doctor, reason, status, patient_nic) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(date));
            statement.setString(2, time);
            statement.setString(3, doctor);
            statement.setString(4, reason);
            statement.setString(5, status);
            statement.setString(6, patientNIC);

            return statement.executeUpdate() > 0; // Returns true if an appointment was added
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateInDatabase() {
        String sql = "UPDATE appointment SET date = ?, time = ?, doctor = ?, reason = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(date));
            statement.setString(2, time);
            statement.setString(3, doctor);
            statement.setString(4, reason);
            statement.setInt(5, id);

            return statement.executeUpdate() > 0; // Returns true if the appointment was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFromDatabase() {
        String sql = "DELETE FROM appointment WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0; // Returns true if the appointment was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Appointment> loadAppointmentsForPatient(String patientNIC) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT id, date, time, doctor, reason, status FROM appointment WHERE patient_nic = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, patientNIC);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String time = resultSet.getString("time");
                String doctor = resultSet.getString("doctor");
                String reason = resultSet.getString("reason");
                String status = resultSet.getString("status");

                appointments.add(new Appointment(id, date, time, doctor, reason, status, patientNIC));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }
}
