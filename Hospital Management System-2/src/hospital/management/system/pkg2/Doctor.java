/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.management.system.pkg2;

/**
 *
 * @author Vidusha Ranmini
 */
public class Doctor {
    
    
    private int doctorId;
    private String username;
    private String password;
    private String doctorname;
    private String email;
    private String gender;
    private String mobileno;
    private String specialized;
    
    
     public Doctor(int doctorId, String username, String password,String doctorname,String email,String gender,String mobileno,String specialized) {
        this.doctorId = doctorId;
        this.username = username;
        this.password = password;
        this.doctorname = doctorname;
        this.email = email;
        this.gender = gender;
        this.mobileno = mobileno;
        this.specialized = specialized;
    }

    
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

   
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getDoctorname(){
        return doctorname;
    }
    
    public void setDoctorname(String doctorname){
        this.doctorname = doctorname;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getMobileno(){
        return mobileno;
    }
    
    public void setMobileno(String mobileno){
        this.mobileno = mobileno;
    }
    
    public String getGender(){
        return gender;
    }
    
    public void setGender(String gender){
        this.gender = gender;
    }
    
    public String getSpecialized(){
        return specialized;
    }
    
    public void setSpecialized(String specialized){
        this.specialized = specialized;
    }

}
