module com.exemple.autov1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.kwhat.jnativehook;


    opens com.exemple.autov1 to javafx.fxml;
    exports com.exemple.autov1.Sauvegarde;
    opens com.exemple.autov1.Sauvegarde to javafx.fxml;
    exports com.exemple.autov1.Graphique;
    exports com.exemple.autov1.Graphique.CreationConfig.Popup;
    exports com.exemple.autov1.Graphique.CreationConfig;
}