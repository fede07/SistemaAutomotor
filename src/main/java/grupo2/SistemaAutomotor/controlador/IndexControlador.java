package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Titular;
import grupo2.SistemaAutomotor.servicio.automotor.AutomotorServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexControlador implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @Autowired
    private AutomotorServicio automotorServicio;

    @FXML
    private TableView<Automotor> automotorTabla = new TableView<>();

    @FXML
    private TableColumn<Automotor, String> dominioAutomotorColumna;

    @FXML
    private TableColumn<Automotor, Titular> titularAutomotorColumna;

    @FXML
    private TableColumn<Automotor, String> modeloAutomotorColumna;

    @FXML
    private TableColumn<Automotor, String> marcaAutomotorColumna;

    @FXML
    private TableColumn<Automotor, Integer> anioAutomotorColumna;

    @FXML
    private TableColumn<Automotor, Integer> municipioAutomotorColumna;

    private final ObservableList<Automotor> automotorList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        automotorTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarAutomotor();
    }

    private void configurarColumnas() {
        dominioAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("dominio"));
        titularAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("dniTitular"));
        modeloAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        marcaAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("marca"));
        anioAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("anoFabricacion"));
        municipioAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("idMunicipio"));
    }

    private void listarAutomotor() {
        logger.info("Listando automotor");
        automotorList.clear();
        automotorList.addAll(automotorServicio.listarAutomotor());
        automotorTabla.setItems(automotorList);
    }

}
