/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.management.system.pkg2;

import javafx.collections.ObservableList;

/**
 *
 * @author Vidusha Ranmini
 */
public class Patientnotes {

    static void setItems(ObservableList<Patientnotes> patientNotesList) {
        
    }
    private Integer noteID;
    private String noteDate;
    private String patientName;
    private String symptoms;
    private String diagnosis;
    private String treatment;
    private String doctorName;

    public Patientnotes(Integer noteID,String noteDate, String patientName, String symptoms, String diagnosis, String treatment, String doctorName) {
        this.noteID = noteID;
        this.noteDate = noteDate;
        this.patientName = patientName;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.doctorName = doctorName;
    }
    public Integer getNoteid(){
       return noteID; 
    }
    public String getNoteDate() {
        return noteDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getDoctorName() {
        return doctorName;
    }
    
    public void setNoteid(Integer noteID) { this.noteID = noteID; }
    public void setNoteDate(String noteDate) { this.noteDate = noteDate; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
}



