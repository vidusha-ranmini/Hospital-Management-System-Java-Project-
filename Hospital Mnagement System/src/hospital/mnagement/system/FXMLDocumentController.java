/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.mnagement.system;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Vidusha Ranmini
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane login_form;

    @FXML
    private TextField login_username;

    @FXML
    private PasswordField login_Password;

    @FXML
    private TextField login_showpassword;

    @FXML
    private CheckBox login_checkBox;

    @FXML
    private Button login_loginBtn;

    @FXML
    private ComboBox<?> login_user;

    @FXML
    private Hyperlink login_registerhere;

    @FXML
    private AnchorPane register_form;

    @FXML
    private TextField register_email;

    @FXML
    private TextField register_username;

    @FXML
    private PasswordField register_password;

    @FXML
    private TextField register_showpassword;

    @FXML
    private CheckBox register_checkbox;

    @FXML
    private Button register_signupBtn;

    @FXML
    private Hyperlink register_loginHere;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private AlertMessage alert = new AlertMessage();
    
    public void loginAccount(){
        
    if (login_username.getText().isEmpty() || login_Password.getText().isEmpty()) {
            alert.errorMessage("Incorrect username/password");
     } else {
            String sql = "SELECT * FROM admin WHERE username = ? AND  password = ?";

            connect = Database.connectionDB();

            try {
                
                if(!login_showpassword.isVisible()){
                    if(!login_showpassword.getText().equals(login_Password.getText())){
                        login_showpassword.setText(login_Password.getText());
                    }
                }else{
                    if(!login_showpassword.getText().equals(login_Password.getText())){
                        login_Password.setText(login_showpassword.getText());
                }
               }
                
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, login_username.getText());
                prepare.setString(2, login_Password.getText());
                result = prepare.executeQuery();

                if (result.next()) {
                 alert.successMessage("Login Successfully!");
                }else{
                  alert.errorMessage("Incorrect username/password");
                }
            }catch(Exception e){
                e.printStackTrace();
            }   
        }
    }
    
    public void loginShowPassword() {

        if (login_checkBox.isSelected()) {
            login_showpassword.setText(login_Password.getText());
            login_showpassword.setVisible(true);
            login_Password.setVisible(false);
        } else {
            login_Password.setText(login_showpassword.getText());
            login_showpassword.setVisible(false);
            login_Password.setVisible(true);
        }
    }
    

    public void registerAccount() {

        if (register_email.getText().isEmpty()
                || register_username.getText().isEmpty()
                || register_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            String checkUsername = "SELECT * FROM admin WHERE username = '"
                    + register_username.getText() + "'";

            connect = Database.connectionDB();

            try {
                
                if(!register_showpassword.isVisible()){
                    if(!register_showpassword.getText().equals(register_password.getText())){
                        register_showpassword.setText(register_password.getText());
                    }
                }else{
                     if(!register_showpassword.getText().equals(register_password.getText())){
                        register_password.setText(register_showpassword.getText());   
                    }
                }

                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert.errorMessage(register_username.getText() + "is already exist!");
                } else if (register_password.getText().length() < 8) {

                    alert.errorMessage("Invalid password, at least 8 characters needed");
                } else {

                    String insertData = "INSERT INTO admin (email,username,password,date) VALUES(?,?,?,?)";

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, register_email.getText());
                    prepare.setString(2, register_username.getText());
                    prepare.setString(3, register_password.getText());
                    prepare.setString(4, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert.successMessage("Register Successfully");
                    registerClear();
                    
                    login_form.setVisible(true);
                    register_form.setVisible(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void registerShowPassword() {

        if (register_checkbox.isSelected()) {
            register_showpassword.setText(register_password.getText());
            register_showpassword.setVisible(true);
            register_showpassword.setVisible(false);
        } else {
            register_password.setText(register_showpassword.getText());
            register_showpassword.setVisible(false);
            register_showpassword.setVisible(true);
        }
    }
    
    public void registerClear(){
        register_email.clear();
        register_username.clear();
        register_password.clear();
        register_showpassword.clear();
    }
    
    public void userList(){
        
        List<String> listU = new ArrayList<>();
        
        for(String data : Users.user){
            listU.add(data);
        }
        
        ObservableList listData = FXCollections.observableList(listU);
        login_user.setItems(listData);
        
    }
    
    public void switchPage(){
        
        if(login_user.getSelectionModel().getSelectedItem() == "Admin Portal"){
            
            try{
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                
                stage.setTitle("Hospital Management System");
                
                stage.setMinWidth(340);
                stage.setMinHeight(580);
                
                stage.setScene(new Scene(root));
                stage.show();
                
                login_user.getScene().getWindow().hide();
                
            }catch(Exception e){e.printStackTrace();}
            
        }else if(login_user.getSelectionModel().getSelectedItem() == "Doctor Portal"){
            
            try{
                Parent root = FXMLLoader.load(getClass().getResource("DoctorPage.fxml"));
                Stage stage = new Stage();
                
                stage.setTitle("Hospital Management System");
                
                stage.setMinWidth(340);
                stage.setMinHeight(580);
                
                stage.setScene(new Scene(root));
                stage.show();
                
                login_user.getScene().getWindow().hide();
                
            }catch(Exception e){e.printStackTrace();}
            
        }else if(login_user.getSelectionModel().getSelectedItem() == "Patient Portal"){
            
              try {
                Parent root = FXMLLoader.load(getClass().getResource("PatientPage.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Hospital Management System");

                stage.setMinWidth(340);
                stage.setMinHeight(580);

                stage.setScene(new Scene(root));
                stage.show();

                login_user.getScene().getWindow().hide();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == login_registerhere) {

            login_form.setVisible(false);
            register_form.setVisible(true);

        } else if (event.getSource() == register_loginHere) {

            login_form.setVisible(true);
            register_form.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userList();
    }

}
