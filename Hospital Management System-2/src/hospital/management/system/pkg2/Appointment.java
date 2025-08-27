package hospital.management.system.pkg2;

import java.time.LocalDate;


public class Appointment {
    private int id;
    private LocalDate date;
    private String time;
    private String doctor;
    private String reason;
    private String status;
    private String nic;

    

    public Appointment(LocalDate date, String time, String doctor, String reason, String status, String nic) {
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.reason = reason;
        this.status = status;
        this.nic = nic;
    }

    public Appointment(int id, LocalDate date, String time, String doctor, String reason, String status, String nic) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.reason = reason;
        this.status = status;
        this.nic = nic;
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

    public String getnic() { return nic; }
    public void setnic(String nic) { this.nic= nic;}

}
  

