/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.management.system.pkg2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Vidusha Ranmini
 */
public class PatientNotesController {
    
     @FXML
    private Label doctorname;

    @FXML
    private DatePicker date;

    @FXML
    private TextArea symptoms;

    @FXML
    private TextArea diagnosis;

    @FXML
    private TextField patientname;

    @FXML
    private TextArea treatment;

    @FXML
    private Button insert;

    @FXML
    private Button update;

    @FXML
    private Button delete;

    @FXML
    private Button clear;

    @FXML
    private Button back;

    @FXML
    private TableView<Patientnotes> patienttable;

    @FXML
    private TableColumn<Patientnotes, String> datetb;
    @FXML
    private TableColumn<Patientnotes, String> patientnametb;
    @FXML
    private TableColumn<Patientnotes, String> symptomstb;  
    @FXML
    private TableColumn<Patientnotes, String> diagnosistb;
    @FXML
    private TableColumn<Patientnotes, String> treatmentstb;

    
    private final AlertMessage alert = new AlertMessage();
    String doctorName;
    private final DatabaseConnection dbConnection = new DatabaseConnection();
    private final ObservableList<Patientnotes> patientNotesList = FXCollections.observableArrayList();
    private Integer selectedNoteId = null;
    
    //insert patient notes
    public void insertNote() {
    try (Connection connect = dbConnection.connect()) {
        String sql = "INSERT INTO patient_notes (note_date, patient_name, symptoms, diagnosis, treatment,doctor_name) VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement pstmt = connect.prepareStatement(sql);
        pstmt.setDate(1, Date.valueOf(date.getValue()));
        pstmt.setString(2, patientname.getText());
        pstmt.setString(3, symptoms.getText());
        pstmt.setString(4, diagnosis.getText());
        pstmt.setString(5, treatment.getText());
        pstmt.setString(6, doctorName);
        pstmt.executeUpdate();
        
        alert.successMessage("Patient note has been added successfully.");
        loadPatientNotes();
        
    } catch (SQLException e) {
        e.printStackTrace();
        alert.errorMessage("Error occurred while inserting the patient note.");
    }
}
    //update patient notes
    public void updateNote() {
        if (selectedNoteId == null) {
            alert.errorMessage("No note selected for updating.");
            return;
        }
        
    try (Connection conn = dbConnection.connect()) {
        String sql = "UPDATE patient_notes SET note_date = ?, patient_name = ?, symptoms = ?, diagnosis = ?, treatment = ? WHERE note_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDate(1, Date.valueOf(date.getValue()));
        pstmt.setString(2, patientname.getText());
        pstmt.setString(3, symptoms.getText());
        pstmt.setString(4, diagnosis.getText());
        pstmt.setString(5, treatment.getText());
        pstmt.setInt(6, selectedNoteId);
        pstmt.executeUpdate();
        
        alert.successMessage("Patient note has been updated.");
        loadPatientNotes();
        
    } catch (SQLException e) {
        e.printStackTrace();
        alert.errorMessage("Error occurred while updating the patient note.");
    }
    }
    
    //delete patient note
    public void deleteNote() {
    try (Connection conn = dbConnection.connect()) {
        String sql = "DELETE FROM patient_notes WHERE note_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setInt(1, selectedNoteId); // Replace with the ID of the selected note
        pstmt.executeUpdate();
        
        alert.successMessage("Patient note has been deleted");
        loadPatientNotes();  
        clearFields();
    } catch (SQLException e) {
        e.printStackTrace();
        alert.errorMessage("Error occurred while deleting the patient note.");
    }
}
    //clear all fields
    public void clearFields() {
    date.setValue(null);
    patientname.clear();
    symptoms.clear();
    diagnosis.clear();
    treatment.clear();
    selectedNoteId = null;
}
    
    //goto dashboard
    public void back() {
    try {
        
        Stage currentStage = (Stage) date.getScene().getWindow();
        currentStage.close();

        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent dashboardRoot = loader.load();

        
        Scene dashboardScene = new Scene(dashboardRoot);
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.show();
    } catch (IOException e) {
        alert.errorMessage("Error loading Dashboard.");
        e.printStackTrace();
    }
}
    public void setDoctorName(String doctorName) {
        this.doctorname.setText("Doctor: " + doctorName);
    }
    
    //view patient notes 
    public void loadPatientNotes() {
    patientNotesList.clear(); 
    try (Connection connect = dbConnection.connect()) {
        String sql = "SELECT * FROM patient_notes";
        PreparedStatement pstmt = connect.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            int noteID = rs.getInt("note_id");
            String noteDate = rs.getDate("note_date").toString();
            String patientName = rs.getString("patient_name");
            String symptoms = rs.getString("symptoms");
            String diagnosis = rs.getString("diagnosis");
            String treatment = rs.getString("treatment");
            String doctorName = rs.getString("doctor_name");

            
            patientNotesList.add(new Patientnotes(noteID,noteDate, patientName, symptoms, diagnosis, treatment, doctorName));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        alert.errorMessage("Error loading patient notes.");
    }
}
    
@FXML
public void initialize() {
    
    datetb.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNoteDate()));
    patientnametb.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientName()));
    symptomstb.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSymptoms()));
    diagnosistb.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiagnosis()));
    treatmentstb.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTreatment()));

    
    patienttable.setItems(patientNotesList);

   
    loadPatientNotes();
    
    patienttable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFormFields(newSelection);
            }
        });
}
    
private void populateFormFields(Patientnotes note) {
        selectedNoteId = note.getNoteid();  
        date.setValue(LocalDate.parse(note.getNoteDate()));
        patientname.setText(note.getPatientName());
        symptoms.setText(note.getSymptoms());
        diagnosis.setText(note.getDiagnosis());
        treatment.setText(note.getTreatment());
    }  
}






    
    
    

