package grupo2.SistemaAutomotor.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
@Component
public class MainControlador implements Initializable {

    public Button facturasPagadasButton;
    public Button facturasImpagasButton;
    public Button gestionarBDDButton;
    public Button deudaButton;
    public Button recaudacionButton;
    public Button GestionarBDDButton;

    private Scene facPagScene;
    private Scene automotorScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void abrirAutomotorScene(ActionEvent actionEvent) {
        Stage automotorStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        double height = automotorStage.getHeight();
        double width = automotorStage.getWidth();
        automotorStage.setHeight(height);
        automotorStage.setWidth(width);
        automotorStage.setScene(automotorScene);
    }

    public void abrirFacPagScene(ActionEvent actionEvent){
        Stage facpagStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        facpagStage.setScene(facPagScene);
    }

}
