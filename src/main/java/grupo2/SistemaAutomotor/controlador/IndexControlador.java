package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Municipio;
import grupo2.SistemaAutomotor.modelo.Titular;
import grupo2.SistemaAutomotor.servicio.automotor.AutomotorServicio;
import grupo2.SistemaAutomotor.servicio.municipio.MunicipioServicio;
import grupo2.SistemaAutomotor.servicio.titular.TitularServicio;
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
    private TableColumn<Titular, Integer> titularAutomotorColumna;

    @FXML
    private TableColumn<Automotor, String> modeloAutomotorColumna;

    @FXML
    private TableColumn<Automotor, String> marcaAutomotorColumna;

    @FXML
    private TableColumn<Automotor, Integer> anioAutomotorColumna;

    @FXML
    private TableColumn<Automotor, Integer> municipioAutomotorColumna;

    private final ObservableList<Automotor> automotorList = FXCollections.observableArrayList();

    @FXML
    private TextField dominioAutomotorTexto;

    @FXML
    private TextField titularAutomotorTexto;

    @FXML
    private TextField modeloAutomotorTexto;

    @FXML
    private TextField marcaAutomotorTexto;

    @FXML
    private TextField anioAutomotorTexto;

    @FXML
    private TextField municipioAutomotorTexto;
    @Autowired
    private TitularServicio titularServicio;
    @Autowired
    private MunicipioServicio municipioServicio;

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

    public void agregarAutomotor() {
        if(dominioAutomotorTexto.getText().isEmpty()) {
            mostrarMensaje("Error Validacion", "Los campos dominio y titular no pueden estar vacios");
        }
        else {
            var automotor = new Automotor();
            if (recolecarDatos(automotor)){
                mostrarDatos(automotor); //DEBUG
                automotorServicio.guardarAutomotor(automotor);
                mostrarMensaje("Información", "Automotor guardado correctamente");
            }

            limpiarFormulario();
            listarAutomotor();
        }
    }

    private void mostrarMensaje(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void mostrarDatos(Automotor automotor) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion");
        alert.setHeaderText(null);
        alert.setContentText(automotor.toString());
        alert.showAndWait();
    }

    private boolean recolecarDatos(Automotor automotor) {
        automotor.setDominio(dominioAutomotorTexto.getText());

        Titular titular = titularServicio.buscarTitular(Integer.valueOf(titularAutomotorTexto.getText()));

        if(titular == null) {
            mostrarMensaje("Error Validacion", "Titular no encontrado");
            return false;
        }

        automotor.setDniTitular(titular);

        automotor.setMarca(marcaAutomotorTexto.getText());
        automotor.setModelo(modeloAutomotorTexto.getText());
        automotor.setAnioFabricacion(Integer.parseInt(anioAutomotorTexto.getText()));

        Municipio municipio = municipioServicio.buscarMunicipio(Integer.parseInt(municipioAutomotorTexto.getText()));

        //TODO Crear desplegable de municipios, el siguiente if seria redundante. Quitar mensajes de error del metodo
        if(municipio == null) {
            mostrarMensaje("Error Validacion", "Municipio no encontrado");
            return false;
        }
        automotor.setIdMunicipio(municipio);

        return true;
    }

    private void limpiarFormulario() {
        dominioAutomotorTexto.clear();
        titularAutomotorTexto.clear();
        modeloAutomotorTexto.clear();
        marcaAutomotorTexto.clear();
        anioAutomotorTexto.clear();
        municipioAutomotorTexto.clear();
    }

}
