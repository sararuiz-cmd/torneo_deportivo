package ni.edu.uam.torneo_deportivo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("principal-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 1000, 700);
        stage.setTitle("Sistema de Gestión de Torneo Deportivo");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.show();
    }
}
