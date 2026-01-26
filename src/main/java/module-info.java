module htsc {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jbcrypt;
    requires mysql.connector.j;

    opens htsc to javafx.fxml;
    opens htsc.src_BE.dao to javafx.fxml;
    opens htsc.src_BE.dto to javafx.fxml;
    opens htsc.src_BE.util to javafx.fxml;

    exports htsc;
    exports htsc.src_BE.dao;
    exports htsc.src_BE.dto;
    exports htsc.src_BE.util;
}
