module htsc {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jbcrypt;
    requires mysql.connector.j;

    opens htsc to javafx.fxml;
    opens htsc.dao to javafx.fxml;
    opens htsc.dto to javafx.fxml;
    opens htsc.util to javafx.fxml;

    exports htsc;
    exports htsc.src_BE.dao;
    exports htsc.src_BE.dto;
    exports htsc.src_BE.util;
}
