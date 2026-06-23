module com.fabianrodas.javadash {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.fabianrodas.javadash to javafx.fxml;
    exports com.fabianrodas.javadash;
}
