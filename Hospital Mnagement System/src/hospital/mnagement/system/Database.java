/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.mnagement.system;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Vidusha Ranmini
 */
public class Database {
    
    public static Connection connectionDB(){
        
        try{
            
            Class. forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/hospital","root","");
            return connect;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
