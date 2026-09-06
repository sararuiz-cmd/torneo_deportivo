package ni.edu.uam.torneo_deportivo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ni.edu.uam.torneo_deportivo.HelloApplication;

import java.io.IOException;

public class PrincipalController {

    public void onRegistrarParticipante(ActionEvent event) throws IOException {
        cambiarVista(event, "TableView.fxml", "Registrar participante");
    }

    public void onActualizarParticipante(ActionEvent event) throws IOException {
        cambiarVista(event, "actualizar-participante.fxml", "Actualizar participante");
    }

    public void onVisualizarParticipantes(ActionEvent event) throws IOException {
        cambiarVista(event, "participantes-view.fxml", "Participantes registrados");
    }

    private void cambiarVista(ActionEvent event, String fxml, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource(fxml)
        );

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(new Scene(root, 1000, 700));
        stage.show();
    }
}
