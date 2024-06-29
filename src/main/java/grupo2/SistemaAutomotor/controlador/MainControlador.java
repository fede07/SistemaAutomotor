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
    private Scene facturasImpagasScene;
    private Scene automotorScene;
    private Scene deudasScene;
    private Scene recaudacionScene;

    @Setter
    private RecaudacionMunicipioControlador recaudacion;
    private DeudaControlador deudaControlador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void abrirAutomotorScene(ActionEvent actionEvent) {
        Stage automotorStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stageSetSize(automotorStage);
        automotorStage.setScene(automotorScene);
    }

    public void abrirFacPagScene(ActionEvent actionEvent){
        Stage facpagStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageSetSize(facpagStage);
        facpagStage.setScene(facPagScene);
    }

    public void abrirDeudaScene(ActionEvent actionEvent){
        Stage deudaStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageSetSize(deudaStage);
        deudaStage.setScene(deudasScene);
        deudaControlador.actualizar();
    }
    public void abrirRecaudacionScene(ActionEvent actionEvent){
        Stage recaudacionStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageSetSize(recaudacionStage);
        recaudacionStage.setScene(recaudacionScene);
        recaudacion.actualizar();
    }

    public void abrirFacturasImpagasScene(ActionEvent actionEvent){
        Stage facpagStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageSetSize(facpagStage);
        facpagStage.setScene(facturasImpagasScene);
    }

    private static void stageSetSize(Stage recaudacionStage) {
        double height = recaudacionStage.getHeight();
        double width = recaudacionStage.getWidth();
        recaudacionStage.setHeight(height);
        recaudacionStage.setWidth(width);
    }

}
