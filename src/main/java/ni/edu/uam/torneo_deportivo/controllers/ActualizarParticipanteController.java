package ni.edu.uam.torneo_deportivo.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ni.edu.uam.torneo_deportivo.HelloApplication;
import ni.edu.uam.torneo_deportivo.models.Participante;
import ni.edu.uam.torneo_deportivo.services.ParticipanteService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActualizarParticipanteController {

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

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtTelefono;

    @FXML
    private ComboBox<String> cbCategoria;

    @FXML
    private ComboBox<String> cbGenero;

    @FXML
    private ComboBox<String> cbModalidad;

    @FXML
    private ComboBox<String> cbDisciplina;

    @FXML
    private CheckBox chkFederado;

    @FXML
    private CheckBox chkExperiencia;

    @FXML
    private CheckBox chkDisponibilidad;

    @FXML
    private CheckBox chkSeguro;

    @FXML
    private TextField txtEstado;

    @FXML
    private Label lblMensaje;

    private final ParticipanteService participanteService =
            ParticipanteService.getInstance();

    @FXML
    public void initialize() {
        configurarColumnas();
        configurarCombos();

        tablaParticipantes.setItems(participanteService.getParticipantes());

        tablaParticipantes.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, actual) -> {
                    if (actual != null) {
                        cargarParticipante(actual);
                    }
                });
    }

    private void configurarColumnas() {
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
    }

    private void configurarCombos() {
        cbCategoria.getItems().addAll(
                "Juvenil",
                "Intermedia",
                "Senior"
        );

        cbGenero.getItems().addAll(
                "Femenino",
                "Masculino"
        );

        cbModalidad.getItems().addAll(
                "Individual",
                "Pareja",
                "Equipo"
        );

        cbDisciplina.getItems().addAll(
                "Fútbol",
                "Baloncesto",
                "Voleibol",
                "Atletismo",
                "Natación",
                "Tenis"
        );
    }

    private void cargarParticipante(Participante participante) {
        txtNombre.setText(participante.getNombre());
        txtEdad.setText(String.valueOf(participante.getEdad()));
        txtTelefono.setText(String.valueOf(participante.getTelefono()));

        cbCategoria.setValue(participante.getCategoria());
        cbGenero.setValue(participante.getGenero());
        cbModalidad.setValue(participante.getModalidad());
        cbDisciplina.setValue(participante.getDisciplina());

        txtEstado.setText(participante.getEstado());

        String caracteristicas = participante.getCaracteristicas();

        chkFederado.setSelected(caracteristicas.contains("Federado"));
        chkExperiencia.setSelected(caracteristicas.contains("Experiencia previa"));
        chkDisponibilidad.setSelected(
                caracteristicas.contains("Disponibilidad fines de semana")
        );
        chkSeguro.setSelected(caracteristicas.contains("Seguro deportivo"));

        lblMensaje.setText("");
    }

    @FXML
    private void onActualizar(ActionEvent event) {
        Participante seleccionado =
                tablaParticipantes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Seleccione un participante en la tabla.");
            return;
        }

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            mostrarError("El nombre es obligatorio.");
            return;
        }

        if (nombre.length() < 5) {
            mostrarError("El nombre debe tener mínimo 5 caracteres.");
            return;
        }

        int edad;

        try {
            edad = Integer.parseInt(txtEdad.getText().trim());
        } catch (NumberFormatException e) {
            mostrarError("La edad debe ser un número.");
            return;
        }

        if (edad < 15 || edad > 60) {
            mostrarError("La edad debe estar entre 15 y 60 años.");
            return;
        }

        String telefono = txtTelefono.getText().trim();

        if (!telefono.matches("\\d+")) {
            mostrarError("El teléfono solamente debe aceptar números.");
            return;
        }

        int numeroTelefono;

        try {
            numeroTelefono = Integer.parseInt(telefono);
        } catch (NumberFormatException e) {
            mostrarError("El teléfono es demasiado largo.");
            return;
        }

        if (cbCategoria.getValue() == null) {
            mostrarError("Debe seleccionarse una categoría.");
            return;
        }

        if (cbGenero.getValue() == null) {
            mostrarError("Debe seleccionarse un género.");
            return;
        }

        if (cbModalidad.getValue() == null) {
            mostrarError("Debe seleccionarse una modalidad.");
            return;
        }

        if (cbDisciplina.getValue() == null) {
            mostrarError("Debe seleccionarse una disciplina.");
            return;
        }

        String estado = txtEstado.getText().trim();

        if (estado.isEmpty()) {
            estado = "Inscrito";
        }

        Participante actualizado = new Participante(
                nombre,
                edad,
                numeroTelefono,
                cbCategoria.getValue(),
                cbGenero.getValue(),
                cbModalidad.getValue(),
                cbDisciplina.getValue(),
                obtenerCaracteristicas(),
                estado
        );

        boolean resultado =
                participanteService.actualizar(seleccionado, actualizado);

        if (resultado) {
            tablaParticipantes.refresh();
            tablaParticipantes.getSelectionModel().select(actualizado);

            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setText("Datos actualizados correctamente.");
        } else {
            mostrarError("No se pudo actualizar el participante.");
        }
    }

    private String obtenerCaracteristicas() {
        List<String> caracteristicas = new ArrayList<String>();

        if (chkFederado.isSelected()) {
            caracteristicas.add("Federado");
        }

        if (chkExperiencia.isSelected()) {
            caracteristicas.add("Experiencia previa");
        }

        if (chkDisponibilidad.isSelected()) {
            caracteristicas.add("Disponibilidad fines de semana");
        }

        if (chkSeguro.isSelected()) {
            caracteristicas.add("Seguro deportivo");
        }

        if (caracteristicas.isEmpty()) {
            return "Ninguna";
        }

        return String.join(", ", caracteristicas);
    }

    private void mostrarError(String texto) {
        lblMensaje.setStyle("-fx-text-fill: red;");
        lblMensaje.setText(texto);
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
