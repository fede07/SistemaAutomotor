package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.clase.Mensajero;
import grupo2.SistemaAutomotor.clase.Validador;
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
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ModificarControlador implements Initializable {
    private final AutomotorServicio automotorServicio;
    private final TitularServicio titularServicio;
    private final MunicipioServicio municipioServicio;
    @FXML
    private Label titularNuevoLabel;
    @FXML
    private CheckBox cambioTitularCheckBox;
    @FXML
    private TextField dniNuevoTextField;
    @FXML
    private Label nombreLabel;
    @FXML
    private Label apellidoLabel;
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
    private TextField dniTextField;
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
    private final Validador validador = new Validador();
    private final Mensajero mensajero = new Mensajero();

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
        nuevoTitularCheckBox.setOnAction(e->{
            if(nuevoTitularCheckBox.isSelected()){
                nombreTextField.setDisable(false);
                apellidoTextField.setDisable(false);
                nombreLabel.setDisable(false);
                apellidoLabel.setDisable(false);
            }else
            {
                nombreTextField.setDisable(true);
                apellidoTextField.setDisable(true);
                nombreLabel.setDisable(true);
                apellidoLabel.setDisable(true);
            }
        });
        cambioTitularCheckBox.setOnAction(e-> {
            if(cambioTitularCheckBox.isSelected()){
                dniNuevoTextField.setDisable(false);
                titularNuevoLabel.setDisable(false);
            }else {
                dniNuevoTextField.setDisable(true);
                titularNuevoLabel.setDisable(true);
            }
        });

    }

    public void modificarAutomotor() {
        if(dominioTextField.getText().isEmpty()){
            mensajero.mostrarMensaje("Información", "Debe Seleccionar un automotor", Alert.AlertType.INFORMATION);
            dominioTextField.requestFocus();
            return;
        }
        if(dniTextField.getText().isEmpty()) {
            mensajero.mostrarMensaje("Advertencia", "Debe ingresar un dni", Alert.AlertType.WARNING);
            dniTextField.requestFocus();
            return;
        }

        if(nuevoTitularCheckBox.isSelected()){
            if (!agregarTitular()){
                return;
            }
        }

        var automotor = new Automotor();
        if(recolectarDatosFormulario(automotor)){
            System.out.println(automotor);
            automotorServicio.guardarAutomotor(automotor);
            mensajero.mostrarMensaje("Información", "Automotor modificado correctamente", Alert.AlertType.INFORMATION);
            listarAutomotor();
            limpiarFormulario();
        }
    }

    private boolean agregarTitular() {
        if(nombreTextField.getText().isEmpty()){
            mensajero.mostrarMensaje("Información", "El campo Nombre no puede estar vacío.", Alert.AlertType.WARNING);
            return false;
        }
        if(apellidoTextField.getText().isEmpty()) {
            mensajero.mostrarMensaje("Información", "El campo Apellido no puede estar vacío.", Alert.AlertType.WARNING);
            return false;
        }

        String dni;

        if(nuevoTitularCheckBox.isSelected()){
            dni = dniNuevoTextField.getText();
        }else {
            dni = dniTextField.getText();
        }

        if(dni.isEmpty()) {
            mensajero.mostrarMensaje("Información", "El campo DNI no puede estar vacío", Alert.AlertType.WARNING);
            return false;
        }

        if(validador.isNotDNI(dni)) {
            mensajero.mostrarMensaje("Error", "El DNI es inválido", Alert.AlertType.ERROR);
            return false;
        }


        Titular titular = new Titular();
        titular.setNombre(nombreTextField.getText());
        titular.setApellido(apellidoTextField.getText());
        titular.setDni(Integer.parseInt(dniNuevoTextField.getText()));

        titularServicio.guardarTitular(titular);
        return true;
    }

    public void buscarAutomotor() {
        String dominio = dominioTextField.getText();
        if (dominio.isEmpty()) {
            mensajero.mostrarMensaje("Información", "El dominio no puede estar vacio", Alert.AlertType.ERROR);
            return;
        }

        if(validador.isNotDominio(dominio)){
            mensajero.mostrarMensaje("Advertencia", "El dominio es inválido", Alert.AlertType.WARNING);
            return;
        }

        Automotor automotor = automotorServicio.buscarAutomotor(dominio);
        if (automotor == null) {
            mensajero.mostrarMensaje("Información", "No se encontro afiliado con el dominio " + dominio, Alert.AlertType.INFORMATION);
            return;
        }

        Municipio municipio = municipioServicio.buscarMunicipio(automotor.getIdMunicipio());

        dniTextField.setText(String.valueOf(automotor.getDniTitular()));
        municipioComboBox.setValue(municipio);
    }

    private boolean recolectarDatosFormulario(Automotor automotor) {

        Automotor automotorBuscado = automotorServicio.buscarAutomotor(dominioTextField.getText());

        if (automotorBuscado == null) {
            mensajero.mostrarMensaje("Error", "El dominio no puede estar vacio", Alert.AlertType.ERROR);
            return false;
        }

        automotor.setDominio(automotorBuscado.getDominio());
        automotor.setDniTitular(titularServicio.buscarTitular(automotorBuscado.getDniTitular()));
        automotor.setModelo(automotorBuscado.getModelo());
        automotor.setMarca(automotorBuscado.getMarca());
        automotor.setAnioFabricacion(automotorBuscado.getAnioFabricacion());
        automotor.setIdMunicipio(municipioServicio.buscarMunicipio(automotorBuscado.getIdMunicipio()));

        String dni = dniNuevoTextField.getText();
        if (dni.isEmpty()) {
            dni = dniTextField.getText();
        }

        if(dni.isEmpty()) {
            mensajero.mostrarMensaje("Error", "Debe ingresar un DNI", Alert.AlertType.ERROR);
            dniTextField.requestFocus();
            return false;
        }

        if(validador.isNotDNI(dni)) {
            mensajero.mostrarMensaje("Error", "El DNI es inválido", Alert.AlertType.ERROR);
            return false;
        }

        Titular titular = titularServicio.buscarTitular(Integer.valueOf(dni));

        if(titular == null) {
            mensajero.mostrarMensaje("Advertencia", "Titular no encontrado", Alert.AlertType.WARNING);
            return false;
        }

        automotor.setDniTitular(titular);

        Municipio municipio = municipioServicio.buscarMunicipio(municipioComboBox.getSelectionModel().getSelectedItem().getId());

        if(municipio == null) {
            mensajero.mostrarMensaje("Advertencia", "Municipio no encontrado", Alert.AlertType.WARNING);
            return false;
        }

        automotor.setIdMunicipio(municipio);

        return true;
    }

    private void listarMunicipios() {
        List<Municipio> municipioList = municipioServicio.listarMunicipios();
        municipioList.sort(Comparator.comparing(Municipio::getNombre));
        for (Municipio municipio : municipioList) {
            municipioComboBox.getItems().add(municipio);
        }
        municipioComboBox.setEditable(false);
    }

    private void listarAutomotor() {
        automotorList.clear();
        automotorList.addAll(automotorServicio.buscarAutomotor(dominioTextField.getText().toUpperCase()));
        automotorTableView.setItems(automotorList);
    }

    public void limpiarFormulario() {
        dominioTextField.clear();
        dniTextField.clear();
        dniNuevoTextField.clear();
        municipioComboBox.getSelectionModel().clearSelection();
        apellidoTextField.clear();
        nombreTextField.clear();
    }

}
