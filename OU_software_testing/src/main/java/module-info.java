module com.ou_software_testing.ou_software_testing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires gson;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires log4j;
    requires commons.codec;
    requires okhttp3;
    requires okio;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;

    opens com.ou_software_testing.ou_software_testing to javafx.fxml;
    exports com.ou_software_testing.ou_software_testing;
}