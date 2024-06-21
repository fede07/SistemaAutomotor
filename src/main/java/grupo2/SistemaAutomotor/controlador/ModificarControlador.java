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
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ModificarControlador implements Initializable {
    private final AutomotorServicio automotorServicio;
    private final TitularServicio titularServicio;
    private final MunicipioServicio municipioServicio;

    @FXML
    private TableView<Automotor> automotorTableView;
    @FXML
    private TableColumn<Automotor, String> dominioColumna;
    @FXML
    private TableColumn<Automotor, Integer> dniColumna;
    @FXML
    private TableColumn<Automotor, String> modeloColumna;
    @FXML
    private TableColumn<Automotor, String> marcaColumna;
    @FXML
    private TableColumn<Automotor, Integer> anioColumna;
    @FXML
    private TableColumn<Automotor, Municipio> municipioColumna;
    @FXML
    private TextField dominioTextField;
    @FXML
    private TextField marcaTextField;
    @FXML
    private TextField anioTextField;
    @FXML
    private TextField dniTextField;
    @FXML
    private TextField modeloTextField;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidoTextField;
    @FXML
    private ComboBox<Municipio> municipioComboBox;
    @FXML
    private CheckBox nuevoTitularCheckBox;
    @FXML
    private Button modificarButton;
    @FXML
    private Button buscarButton;

    private final ObservableList<Automotor> automotorList = FXCollections.observableArrayList();

    public ModificarControlador(AutomotorServicio automotorServicio, TitularServicio titularServicio, MunicipioServicio municipioServicio) {
        this.automotorServicio = automotorServicio;
        this.titularServicio = titularServicio;
        this.municipioServicio = municipioServicio;
    }
    private void configurarColumnas() {
        dominioColumna.setCellValueFactory(new PropertyValueFactory<>("dominio"));
        dniColumna.setCellValueFactory(new PropertyValueFactory<>("dniTitular"));
        modeloColumna.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        marcaColumna.setCellValueFactory(new PropertyValueFactory<>("marca"));
        anioColumna.setCellValueFactory(new PropertyValueFactory<>("anioFabricacion"));
        municipioColumna.setCellValueFactory(new PropertyValueFactory<>("nombreMunicipio"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        automotorTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarMunicipios();
        modificarButton.setOnAction(e->modificarAutomotor());
        buscarButton.setOnAction(e->buscarAutomotor());
    }

    public void modificarAutomotor() {
        if(dominioTextField == null){
            mostrarMensaje("Informacion", "Debe Seleccionar un automotor");
            return;
        }
        if(dominioTextField.getText().isEmpty()) {
            mostrarMensaje("Error Validacion", "El campo Dominio no puede estar vacio");
            dominioTextField.requestFocus();
            return;
        }
        var automotor = new Automotor();
        if(recolectarDatosFormulario(automotor)){
            //mostrarDatos(automotor);
            automotorServicio.guardarAutomotor(automotor);
            mostrarMensaje("Informacion", "Automotor modificado correctamente");
            limpiarFormulario();
            listarAutomotor();
        }
    }

    public void buscarAutomotor() {
        String dominio = dominioTextField.getText();
        if (dominio.isEmpty()) {
            mostrarMensaje("Error", "El dominio no puede estar vacio");
            return;
        }

        Automotor automotor = automotorServicio.buscarAutomotor(dominio);
        if (automotor == null) {
            mostrarMensaje("Info", "No se encontraron facturas con el dominio " + dominio);
            return;
        }
        dniTextField.setText(String.valueOf(automotor.getDniTitular()));
    }

    private boolean recolectarDatosFormulario(Automotor automotor) {

        automotor.setDominio(dominioTextField.getText());

        Titular titular = titularServicio.buscarTitular(Integer.valueOf(dniTextField.getText()));

        if(titular == null) {
            mostrarMensaje("Error Validacion", "Titular no encontrado");
            return false;
        }

        automotor.setDniTitular(titular);

        automotor.setMarca(marcaTextField.getText());
        automotor.setModelo(modeloTextField.getText());
        automotor.setAnioFabricacion(Integer.parseInt(anioTextField.getText()));

        Municipio municipio = municipioServicio.buscarMunicipio(municipioComboBox.getSelectionModel().getSelectedIndex()+1);


        //TODO Crear desplegable de municipios, el siguiente if seria redundante. Quitar mensajes de error del metodo
        if(municipio == null) {
            mostrarMensaje("Error Validacion", "Municipio no encontrado");
            return false;
        }
        automotor.setIdMunicipio(municipio);

        return true;
    }



    private void listarMunicipios() {
        List<Municipio> municipioList = municipioServicio.listarMunicipios();
        for (Municipio municipio : municipioList) {
            municipioComboBox.getItems().add(municipio);
        }
        municipioComboBox.setEditable(false);
    }

    private void listarAutomotor() {
        automotorList.clear();
        automotorList.addAll(automotorServicio.buscarAutomotor(dominioTextField.getText()));
        automotorTableView.setItems(automotorList);
    }

    private void mostrarMensaje(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void limpiarFormulario() {
        dominioTextField.clear();
        dniTextField.clear();
        modeloTextField.clear();
        marcaTextField.clear();
        anioTextField.clear();
        municipioComboBox.getSelectionModel().clearSelection();
    }

}
