module ni.edu.uam.torneo_deportivo {
    requires javafx.controls;
    requires javafx.fxml;

    exports ni.edu.uam.torneo_deportivo;
    exports ni.edu.uam.torneo_deportivo.controllers;
    exports ni.edu.uam.torneo_deportivo.models;
    exports ni.edu.uam.torneo_deportivo.services;

    opens ni.edu.uam.torneo_deportivo to javafx.fxml;
    opens ni.edu.uam.torneo_deportivo.controllers to javafx.fxml;
}
