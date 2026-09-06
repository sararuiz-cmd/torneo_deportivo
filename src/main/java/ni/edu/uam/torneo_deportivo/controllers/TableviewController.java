package ni.edu.uam.torneo_deportivo.controllers;

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
import java.util.List;

public class TableviewController {

    @FXML
    private TextField NombreCompleto;

    @FXML
    private TextField Edad;

    @FXML
    private TextField telefono;

    @FXML
    private ComboBox<String> categoria;

    @FXML
    private RadioButton femenino;

    @FXML
    private RadioButton masculino;

    @FXML
    private RadioButton individual;

    @FXML
    private RadioButton pareja;

    @FXML
    private RadioButton equipo;

    @FXML
    private CheckBox federado;

    @FXML
    private CheckBox experiencia;

    @FXML
    private CheckBox dispo;

    @FXML
    private CheckBox seguro;

    @FXML
    private ListView<String> lista;

    @FXML
    private Label mensaje;

    private final ToggleGroup grupoGenero = new ToggleGroup();
    private final ToggleGroup grupoModalidad = new ToggleGroup();

    private final ParticipanteService participanteService =
            ParticipanteService.getInstance();

    @FXML
    public void initialize() {
        femenino.setToggleGroup(grupoGenero);
        masculino.setToggleGroup(grupoGenero);

        individual.setToggleGroup(grupoModalidad);
        pareja.setToggleGroup(grupoModalidad);
        equipo.setToggleGroup(grupoModalidad);

        categoria.getItems().addAll(
                "Juvenil",
                "Intermedia",
                "Senior"
        );

        lista.getItems().addAll(
                "Fútbol",
                "Baloncesto",
                "Voleibol",
                "Atletismo",
                "Natación",
                "Tenis"
        );
    }

    @FXML
    private void onRegistrar(ActionEvent event) {
        mensaje.setText("");

        String nombre = NombreCompleto.getText().trim();
        String textoEdad = Edad.getText().trim();
        String textoTelefono = telefono.getText().trim();

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
            edad = Integer.parseInt(textoEdad);
        } catch (NumberFormatException e) {
            mostrarError("La edad debe ser un número.");
            return;
        }

        if (edad < 15 || edad > 60) {
            mostrarError("La edad debe estar entre 15 y 60 años.");
            return;
        }

        if (!textoTelefono.matches("\\d+")) {
            mostrarError("El teléfono solamente debe aceptar números.");
            return;
        }

        int numeroTelefono;

        try {
            numeroTelefono = Integer.parseInt(textoTelefono);
        } catch (NumberFormatException e) {
            mostrarError("El teléfono es demasiado largo.");
            return;
        }

        if (categoria.getValue() == null) {
            mostrarError("Debe seleccionarse una categoría.");
            return;
        }

        Toggle generoSeleccionado = grupoGenero.getSelectedToggle();

        if (generoSeleccionado == null) {
            mostrarError("Debe seleccionarse un género.");
            return;
        }

        Toggle modalidadSeleccionada = grupoModalidad.getSelectedToggle();

        if (modalidadSeleccionada == null) {
            mostrarError("Debe seleccionarse una modalidad.");
            return;
        }

        String disciplina = lista.getSelectionModel().getSelectedItem();

        if (disciplina == null) {
            mostrarError("Debe seleccionarse una disciplina.");
            return;
        }

        String genero = ((RadioButton) generoSeleccionado).getText();
        String modalidad = ((RadioButton) modalidadSeleccionada).getText();

        Participante participante = new Participante(
                nombre,
                edad,
                numeroTelefono,
                categoria.getValue(),
                genero,
                modalidad,
                disciplina,
                obtenerCaracteristicas(),
                "Inscrito"
        );

        participanteService.registrar(participante);

        mensaje.setStyle("-fx-text-fill: green;");
        mensaje.setText("Participante registrado correctamente.");

        limpiarFormulario();
    }

    private String obtenerCaracteristicas() {
        List<String> caracteristicas = new ArrayList<String>();

        if (federado.isSelected()) {
            caracteristicas.add("Federado");
        }

        if (experiencia.isSelected()) {
            caracteristicas.add("Experiencia previa");
        }

        if (dispo.isSelected()) {
            caracteristicas.add("Disponibilidad fines de semana");
        }

        if (seguro.isSelected()) {
            caracteristicas.add("Seguro deportivo");
        }

        if (caracteristicas.isEmpty()) {
            return "Ninguna";
        }

        return String.join(", ", caracteristicas);
    }

    private void limpiarFormulario() {
        NombreCompleto.clear();
        Edad.clear();
        telefono.clear();

        categoria.getSelectionModel().clearSelection();
        grupoGenero.selectToggle(null);
        grupoModalidad.selectToggle(null);
        lista.getSelectionModel().clearSelection();

        federado.setSelected(false);
        experiencia.setSelected(false);
        dispo.setSelected(false);
        seguro.setSelected(false);
    }

    private void mostrarError(String texto) {
        mensaje.setStyle("-fx-text-fill: red;");
        mensaje.setText(texto);
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
