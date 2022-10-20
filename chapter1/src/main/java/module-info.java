module se233.chapter1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires org.slf4j;


    opens se233.chapter1 to javafx.fxml;
    exports se233.chapter1;
    exports se233.chapter1.model.character;
    exports se233.chapter1.model;
    exports se233.chapter1.model.item;

}