package ni.edu.uam.torneo_deportivo.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ni.edu.uam.torneo_deportivo.HelloApplication;
import ni.edu.uam.torneo_deportivo.models.Participante;
import ni.edu.uam.torneo_deportivo.services.ParticipanteService;

import java.io.IOException;

public class VisualizarParticipantesController {

    @FXML
    private TableView<Participante> tablaParticipantes;

    @FXML
    private TableColumn<Participante, String> colNombre;

    @FXML
    private TableColumn<Participante, Integer> colEdad;

    @FXML
    private TableColumn<Participante, String> colCategoria;

    @FXML
    private TableColumn<Participante, String> colModalidad;

    @FXML
    private TableColumn<Participante, String> colDisciplina;

    @FXML
    private TableColumn<Participante, String> colCaracteristicas;

    @FXML
    private TableColumn<Participante, String> colEstado;

    private final ParticipanteService participanteService =
            ParticipanteService.getInstance();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(
                dato -> new SimpleStringProperty(dato.getValue().getNombre())
        );

        colEdad.setCellValueFactory(
                dato -> new SimpleIntegerProperty(
                        dato.getValue().getEdad()
                ).asObject()
        );

        colCategoria.setCellValueFactory(
                dato -> new SimpleStringProperty(dato.getValue().getCategoria())
        );

        colModalidad.setCellValueFactory(
                dato -> new SimpleStringProperty(dato.getValue().getModalidad())
        );

        colDisciplina.setCellValueFactory(
                dato -> new SimpleStringProperty(dato.getValue().getDisciplina())
        );

        colCaracteristicas.setCellValueFactory(
                dato -> new SimpleStringProperty(
                        dato.getValue().getCaracteristicas()
                )
        );

        colEstado.setCellValueFactory(
                dato -> new SimpleStringProperty(dato.getValue().getEstado())
        );

        tablaParticipantes.setItems(
                participanteService.getParticipantes()
        );
    }

    @FXML
    private void onAtras(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("principal-view.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Sistema de Gestión de Torneo Deportivo");
        stage.setScene(new Scene(root, 1000, 700));
        stage.show();
    }
}
