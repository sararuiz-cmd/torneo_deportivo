package ni.edu.uam.torneo_deportivo.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ni.edu.uam.torneo_deportivo.models.Participante;

public class ParticipanteService {

    private static final ParticipanteService INSTANCIA = new ParticipanteService();

    private final ObservableList<Participante> participantes =
            FXCollections.observableArrayList();

    private ParticipanteService() {
    }

    public static ParticipanteService getInstance() {
        return INSTANCIA;
    }

    public ObservableList<Participante> getParticipantes() {
        return participantes;
    }

    public void registrar(Participante participante) {
        participantes.add(participante);
    }

    public boolean actualizar(Participante participanteSeleccionado,
                              Participante participanteActualizado) {

        int indice = participantes.indexOf(participanteSeleccionado);

        if (indice == -1) {
            return false;
        }

        participantes.set(indice, participanteActualizado);
        return true;
    }
}
