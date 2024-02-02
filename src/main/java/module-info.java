module exam.hotel {
    requires javafx.controls;
    requires javafx.fxml;


    opens exam.hotel to javafx.fxml;
    exports exam.hotel;
}