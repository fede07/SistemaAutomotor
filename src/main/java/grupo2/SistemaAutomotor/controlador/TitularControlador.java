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
public class TitularControlador implements Initializable {

    private Scene mainScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void abrirMainScene(ActionEvent actionEvent) {
        Stage mainStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        mainStage.setScene(mainScene);
    }
}
