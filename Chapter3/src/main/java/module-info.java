module se233.chapter3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires pdfbox.app;
    requires org.apache.logging.log4j;
    requires org.slf4j;


    opens se233.chapter3 to javafx.fxml;
    opens se233.chapter3.controller to javafx.fxml;
    exports se233.chapter3;
    exports se233.chapter3.controller;
}