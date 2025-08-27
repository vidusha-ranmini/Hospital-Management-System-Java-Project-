package hospital.management.system.pkg2;

import java.time.LocalDate;

public class Bill {
    private int billId;
    private int patientId;
    private String patientname;
    private String doctorname;
    private String service;
    private LocalDate date;
    private double amount;
    private String status;

    
    public Bill(int billId,int patientId,String patientname,String doctorname,String service,double amount,String status,LocalDate date){
        this.billId = billId;
        this.patientId= patientId;
        this.patientname=patientname;
        this.doctorname = doctorname;
        this.service = service;
        this.amount = amount;
        this.status=status;
        this.date = date;
        
    }

    Bill(){}

        
    public int getBillId(){return billId;}
    public void setBillId(int billId) { this.billId = billId; }
    
    public int getPatientId(){return patientId;}
    public void setPatientId(int patienId) { this.patientId =patientId; }
    
    public String getPatientName(){return patientname;}
    public void setPatientName(String patientname) { this.patientname =patientname; }
    
    public String getDoctorName(){return doctorname;}
    public void setDoctorName(String doctorname) { this.doctorname = doctorname; }
    
    public String getService(){return service;}
    public void setService(String service) { this.service =service; }
    
    public LocalDate getBillDate() {return date;}
    public void setDate(LocalDate date) { this.date = date; }
    
    public double getAmount(){return amount;}
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getStatus(){return status;}
    public void setStatus(String status) { this.status = status; }

    
}
    

   
