module com.example.stickheroproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires javafx.media;


    opens com.example.stickheroproject to javafx.fxml;
    exports com.example.stickheroproject;
    exports com.example.stickheroproject.UI.Main;
    exports com.example.stickheroproject.UI.Login;
}