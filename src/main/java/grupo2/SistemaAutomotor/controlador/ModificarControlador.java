package grupo2.SistemaAutomotor.controlador;

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
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ModificarControlador implements Initializable {
    private final AutomotorServicio automotorServicio;
    private final TitularServicio titularServicio;
    private final MunicipioServicio municipioServicio;
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

    }

    public void modificarAutomotor() {
        if(dominioTextField.getText().isEmpty()){
            mostrarMensaje("Informacion", "Debe Seleccionar un automotor");
            dominioTextField.requestFocus();
            return;
        }
        if(dniTextField.getText().isEmpty()) {
            mostrarMensaje("Error Validacion", "Debe ingresar un dni");
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
            mostrarMensaje("Informacion", "Automotor modificado correctamente");
            listarAutomotor();
            limpiarFormulario();
        }
    }

    private boolean agregarTitular() {
        if(nombreTextField.getText().isEmpty()){
            mostrarMensaje("Información", "El campo Nombre no puede estar vacío.");
            return false;
        }
        if(apellidoTextField.getText().isEmpty()) {
            mostrarMensaje("Información", "El campo Apellido no puede estar vacío.");
            return false;
        }

        String dni = dniTextField.getText();

        if(dni.isEmpty()) {
            mostrarMensaje("Información", "El campo DNI no puede estar vacío");
            return false;
        }

        if(validador.isNotNumeric(dni)) {
            mostrarMensaje("Error Validación", "El DNI es inválido");
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
            mostrarMensaje("Información", "El dominio no puede estar vacio");
            return;
        }

        if(validador.isNotDominio(dominio)){
            mostrarMensaje("Error Validación", "El dominio es inválido");
            return;
        }

        Automotor automotor = automotorServicio.buscarAutomotor(dominio);
        if (automotor == null) {
            mostrarMensaje("Información", "No se encontro afiliado con el dominio " + dominio);
            return;
        }

        Municipio municipio = municipioServicio.buscarMunicipio(automotor.getIdMunicipio());

        dniTextField.setText(String.valueOf(automotor.getDniTitular()));
        municipioComboBox.setValue(municipio);
    }

    private boolean recolectarDatosFormulario(Automotor automotor) {

        Automotor automotorBuscado = automotorServicio.buscarAutomotor(dominioTextField.getText());

        if (automotorBuscado == null) {
            mostrarMensaje("Error", "El dominio no puede estar vacio");
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
            mostrarMensaje("Error", "Debe ingresar un DNI");
            dniTextField.requestFocus();
            return false;
        }

        Titular titular = titularServicio.buscarTitular(Integer.valueOf(dni));

        if(titular == null) {
            mostrarMensaje("Error Validacion", "Titular no encontrado");
            return false;
        }

        automotor.setDniTitular(titular);

        Municipio municipio = municipioServicio.buscarMunicipio(municipioComboBox.getSelectionModel().getSelectedIndex()+1);

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
        dniNuevoTextField.clear();
        municipioComboBox.getSelectionModel().clearSelection();
        apellidoTextField.clear();
        nombreTextField.clear();
    }

}
