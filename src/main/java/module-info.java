module com.example.checkers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens Widok to javafx.fxml;
    exports Widok;
    exports Network;
    opens Network to javafx.fxml;
}