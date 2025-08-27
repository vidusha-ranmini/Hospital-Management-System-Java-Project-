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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Vidusha Ranmini
 */
public class DoctorPageController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane login_form;

    @FXML
    private TextField login_doctorID;

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
    private PreparedStatement prepare;
    private ResultSet result;

    private final AlertMessage alert = new AlertMessage();

    @FXML
    void loginAccount() {

        if (login_doctorID.getText().isEmpty()
                || login_Password.getText().isEmpty()) {
            alert.errorMessage("Incorrect Username/Password");
        } else {

            String sql = "SELECT * FROM doctors WHERE doctor_id = ? AND  password = ? AND delete_date IS NULL";
            connect = Database.connectionDB();

            try {

                String checkStatus = "SELECT status FROM doctors WHERE doctor_id='"
                        + login_doctorID.getText() + "' AND password = '"
                        + login_Password.getText() + "' AND status = 'Confirm'";

                prepare = connect.prepareStatement(checkStatus);
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
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, login_doctorID.getText());
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
        if (register_fullname.getText().isEmpty()
                || register_email.getText().isEmpty()
                || register_doctorid.getText().isEmpty()
                || register_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            String checkDoctorID = "SELECT * FROM doctors WHERE doctor_id = '"
                    + register_doctorid.getText() + "'";

            connect = Database.connectionDB();

            try {

                if (!register_showpassword.isVisible()) {
                    if (!register_showpassword.getText().equals(register_password.getText())) {
                        register_showpassword.setText(register_password.getText());
                    } else {
                        if (!register_showpassword.getText().equals(register_password.getText())) {
                            register_password.setText(register_showpassword.getText());

                        }
                    }
                }

                prepare = connect.prepareStatement(checkDoctorID);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert.errorMessage(register_doctorid.getText() + " is alredy taken");
                } else if (register_password.getText().length() < 0) {
                    alert.errorMessage("Invalid password, at least 8 characters needed");
                } else {
                    String insertData = "INSERT INTO doctors (full_name,email,doctor_id,password,date,status)" + "VALUES(?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(1, register_fullname.getText());
                    prepare.setString(2, register_email.getText());
                    prepare.setString(3, register_doctorid.getText());
                    prepare.setString(4, register_password.getText());
                    prepare.setString(5, String.valueOf(sqlDate));
                    prepare.setString(6, "Confirm");

                    prepare.executeUpdate();

                    alert.successMessage("Registered Succesfully!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void registerShowPassword() {
        if (register_checkbox.isSelected()) {
            register_showpassword.setText(register_password.getText());
            register_showpassword.setVisible(true);
            register_password.setVisible(false);
        } else {
            register_password.setText(register_showpassword.getText());
            register_showpassword.setVisible(false);
            register_password.setVisible(true);
        }
    }

    public void registerDoctorID() {
        String doctorid = "doc_";
        int tempID = 0;
        String sql = "SELECT MAX(id) FROM doctors";

        connect = Database.connectionDB();

        try {

            prepare = connect.prepareCall(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                tempID = result.getInt("MAX(id)");
            }

            if (tempID == 0) {
                tempID += 1;
                doctorid += tempID;
            } else {
                doctorid += (tempID + 1);
            }

            register_doctorid.setText(doctorid);
            register_doctorid.setDisable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userList() {

        List<String> listU = new ArrayList<>();

        for (String data : Users.user) {
            listU.add(data);
        }

        ObservableList listData = FXCollections.observableList(listU);
        login_user.setItems(listData);

    }

    public void switchPage() {

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

    @FXML
    void switchForm(ActionEvent event) {

        if (event.getSource() == register_loginHere) {
            login_form.setVisible(true);
            register_form.setVisible(false);
        } else if (event.getSource() == login_registerhere) {
            login_form.setVisible(false);
            register_form.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        registerDoctorID();
        userList();
    }
}
