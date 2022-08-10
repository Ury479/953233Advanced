module se233.chapter2_2 {
    requires javafx.controls;
    requires javafx.fxml;

    //2.9
    requires java.json;
    requires org.apache.commons.io;

    opens se233.chapter2_2 to javafx.fxml;
    exports se233.chapter2_2;
}