package grupo2.SistemaAutomotor.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
@Component
public class MainControlador implements Initializable {

    private Scene facpagScene;
    private Scene automotorScene;
    private Scene titularScene;
    private Scene boletaScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void abrirAutomotorScene(ActionEvent actionEvent) {
        Stage automotorStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        automotorStage.setScene(automotorScene);
    }

    public void abrirTitularScene(ActionEvent actionEvent) {
        Stage titularStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        titularStage.setScene(titularScene);
    }

    public void abrirBoletaScene(ActionEvent actionEvent) {
        Stage boletaStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        boletaStage.setScene(boletaScene);
    }

    public void abrirFacpagScene(ActionEvent actionEvent){
        Stage facpagStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        facpagStage.setScene(facpagScene);
    }
}
