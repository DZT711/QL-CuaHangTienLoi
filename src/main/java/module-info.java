module htsc {
    requires javafx.controls;
    requires javafx.fxml;

    opens htsc to javafx.fxml;
    exports htsc;
}
