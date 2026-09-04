module ni.edu.uam.torneo_deportivo {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.torneo_deportivo to javafx.fxml;
    exports ni.edu.uam.torneo_deportivo;
}