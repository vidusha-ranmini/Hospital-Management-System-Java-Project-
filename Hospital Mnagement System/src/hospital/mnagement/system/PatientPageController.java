/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.mnagement.system;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Vidusha Ranmini
 */
public class PatientPageController implements Initializable {
    
    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane login_form;

    @FXML
    private TextField login_patientID;

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
    private AnchorPane register_form;

    @FXML
    private TextField register_fullname;

    @FXML
    private TextField register_email;

    @FXML
    private TextField register_doctorid;

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
    private preparedStatement prepare;
    private ResultSet result;
    
    private AlertMessage alert = new AlertMessage();
    
    @FXML
    void loginAccount() {
        if (login_patientID.getText().isEmpty()
                || login_Password.getText().isEmpty()) {
            alert.errorMessage("Incorrect Username/Password");
        } else {

            String sql = "SELECT * FROM patient WHERE patient_id = ? AND  password = ? AND date_delete IS NULL";
            connect = Database.connectionDB();

            try {

                String checkStatus = "SELECT status FROM patient WHERE patient_id='"
                        + login_patientID.getText() + "' AND password = '"
                        + login_Password.getText() + "' AND status = 'Confirm'";

                prepare = (preparedStatement) connect.prepareStatement(checkStatus);
                result = prepare.executeQuery();

                if (result.next()) {
                    if (!login_showpassword.isVisible()) {
                        if (!login_showpassword.getText().equals(login_Password.getText())) {
                            login_showpassword.setText(login_Password.getText());
                        }
                    } else {
                        if (!login_showpassword.getText().equals(login_Password.getText())) {
                            login_Password.setText(login_showpassword.getText());
                        }
                    }
                    alert.errorMessage("Need the confirmation of the Admin!");
                } else {
                    prepare = (preparedStatement) connect.prepareStatement(sql);
                    prepare.setString(1, login_patientID.getText());
                    prepare.setString(2, login_Password.getText());

                    result = prepare.executeQuery();
                    if (result.next()) {
                        alert.successMessage("Login Succesfully");
                    } else {
                        alert.errorMessage("Incorrect Username/Password");
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void loginShowPassword() {
        if (login_checkBox.isSelected()) {
            login_showpassword.setText(login_Password.getText());
            login_Password.setVisible(false);
            login_showpassword.setVisible(true);
        } else {
            login_Password.setText(login_showpassword.getText());
            login_Password.setVisible(true);
            login_showpassword.setVisible(false);
        }
    }
    

    @FXML
    void registerAccount() {

    }

    @FXML
    void registerShowPassword() {

    }

    @FXML
    void switchForm() {

    }
    
    public void userList() {

        List<String> listU = new ArrayList<>();

        for (String data : Users.user) {
            listU.add(data);
        }

        ObservableList listData = FXCollections.observableList(listU);
        login_user.setItems(listData);

    }

    @FXML
    void switchPage() {
        if (login_user.getSelectionModel().getSelectedItem() == "Admin Portal") {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
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

        } else if (login_user.getSelectionModel().getSelectedItem() == "Doctor Portal") {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("DoctorPage.fxml"));
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

        } else if (login_user.getSelectionModel().getSelectedItem() == "Patient Portal") {

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

    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        userList();
    }
    
}
