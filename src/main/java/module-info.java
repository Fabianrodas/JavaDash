module com.fabianrodas.javadash {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.fabianrodas.javadash to javafx.fxml;
    exports com.fabianrodas.javadash;
}
