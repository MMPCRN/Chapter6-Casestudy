module se233.chapter5_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires junit;
    requires javafx.swing;
    requires org.slf4j;
    requires org.apache.logging.log4j;

    opens se233.chapter5_2 to javafx.fxml;
    exports se233.chapter5_2;
    exports se233.chapter5_2.model;
    opens se233.chapter5_2.model to javafx.fxml;
    exports se233.chapter5_2.view;
    opens se233.chapter5_2.view to javafx.fxml;
    exports se233.chapter5_2.controller;
    opens se233.chapter5_2.controller to javafx.fxml;
}