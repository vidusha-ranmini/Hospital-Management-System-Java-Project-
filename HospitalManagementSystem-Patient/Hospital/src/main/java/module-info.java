module com.mycompany.hospital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.hospital to javafx.fxml;
    exports com.mycompany.hospital;
}
