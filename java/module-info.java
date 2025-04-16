module com.littlesteps.littlesteps {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires javafx.graphics;

    opens com.littlesteps.littlesteps to javafx.fxml;
    exports com.littlesteps.littlesteps;
    exports com.littlesteps.littlesteps.Controllers;
    opens com.littlesteps.littlesteps.Controllers to javafx.fxml;
}