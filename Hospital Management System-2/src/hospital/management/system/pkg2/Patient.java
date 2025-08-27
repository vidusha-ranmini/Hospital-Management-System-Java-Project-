 package hospital.management.system.pkg2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital"; 
    private static final String USER = "root"; 
    private static final String PASS = ""; 

    private String fullName;
    private int age;
    private String gender;
    private String nic;
    private String password;

    public Patient(String fullName, int age, String gender, String nic, String password) {
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.nic = nic;
        this.password = password;
    }

    //patient registration
    public boolean register() {
        String sql = "INSERT INTO patient (full_name, gender,age, nic, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fullName);
            pstmt.setString(2, gender);
            pstmt.setInt(3, age);
            pstmt.setString(4, nic);
            pstmt.setString(5, password);
            pstmt.executeUpdate();
            return true;
        }catch (SQLException e) {
            System.out.println("Error inserting registration details: " + e.getMessage());
    }
        return false;
    }

    public static boolean login(String nic, String password) {
        String sql = "SELECT * FROM patient WHERE nic = ? AND password = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nic);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); 
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
