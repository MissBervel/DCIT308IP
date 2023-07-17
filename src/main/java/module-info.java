module com.example.individualproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
            
                            
    opens com.example.individualproject to javafx.fxml;
    exports com.example.individualproject;
}