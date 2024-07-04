package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.clase.Mensajero;
import grupo2.SistemaAutomotor.clase.Validador;
import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.modelo.Municipio;
import grupo2.SistemaAutomotor.modelo.Titular;
import grupo2.SistemaAutomotor.servicio.automotor.AutomotorServicio;
import grupo2.SistemaAutomotor.servicio.boleta.BoletaServicio;
import grupo2.SistemaAutomotor.servicio.municipio.MunicipioServicio;
import grupo2.SistemaAutomotor.servicio.titular.TitularServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

@Component
public class AgregarControlador implements Initializable {

    private final AutomotorServicio automotorServicio;
    private final TitularServicio titularServicio;
    private final MunicipioServicio municipioServicio;
    private final BoletaServicio boletaServicio;
    private final Validador validador = new Validador();

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
    private Button agregarButton;
    private final ObservableList<Automotor> automotorList = FXCollections.observableArrayList();
    private final Mensajero mensajero = new Mensajero();

    public AgregarControlador(AutomotorServicio automotorServicio, TitularServicio titularServicio, MunicipioServicio municipioServicio, BoletaServicio boletaServicio) {
        this.automotorServicio = automotorServicio;
        this.titularServicio = titularServicio;
        this.municipioServicio = municipioServicio;
        this.boletaServicio = boletaServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        automotorTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarMunicipios();

        nuevoTitularCheckBox.setOnAction(e -> {
            if (nuevoTitularCheckBox.isSelected()) {
                nombreTextField.setDisable(false);
                nombreLabel.setDisable(false);
                apellidoTextField.setDisable(false);
                apellidoLabel.setDisable(false);
            }
            else {
                nombreTextField.setDisable(true);
                nombreLabel.setDisable(true);
                apellidoTextField.setDisable(true);
                apellidoLabel.setDisable(true);
            }
        });
        agregarButton.setOnAction(e -> agregar());
    }

    private void configurarColumnas() {
        dominioColumna.setCellValueFactory(new PropertyValueFactory<>("dominio"));
        dniColumna.setCellValueFactory(new PropertyValueFactory<>("dniTitular"));
        modeloColumna.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        marcaColumna.setCellValueFactory(new PropertyValueFactory<>("marca"));
        anioColumna.setCellValueFactory(new PropertyValueFactory<>("anioFabricacion"));
        municipioColumna.setCellValueFactory(new PropertyValueFactory<>("nombreMunicipio"));
    }

    private void listarAutomotor() {
        automotorList.clear();
        automotorList.addAll(automotorServicio.buscarAutomotor(dominioTextField.getText()));
        automotorTableView.setItems(automotorList);
    }

    private void listarMunicipios() {
        List<Municipio> municipioList = municipioServicio.listarMunicipios();
        for (Municipio municipio : municipioList) {
            municipioComboBox.getItems().add(municipio);
        }
        municipioComboBox.setEditable(false);
    }

    public void agregar() {
        if(nuevoTitularCheckBox.isSelected()) {
            if(!agregarTitular()){
                return;
            }
        }
        agregarAutomotor();
    }

    private boolean agregarTitular() {
        Titular titular = new Titular();
        String dni = dniTextField.getText();
        if(!dni.isEmpty() || validador.isNotNumeric(dni)) {
            mensajero.mostrarMensaje("Error", "DNI inválido.", Alert.AlertType.ERROR);
            return false;
        }
        titular.setDni(Integer.parseInt(dni));
        titular.setNombre(nombreTextField.getText());
        titular.setApellido(apellidoTextField.getText());
        titularServicio.guardarTitular(titular);
        return true;
    }

    public boolean agregarAutomotor() {
        String dominio = dominioTextField.getText();

        if(dominio.isEmpty()) {
            mensajero.mostrarMensaje("Error", "El campo Dominio no puede estar vacio.", Alert.AlertType.ERROR);
            dominioTextField.requestFocus();
            return false;
        }

        if(validador.isNotDominio(dominio)){
            mensajero.mostrarMensaje("Error", "Dominio Invalido.", Alert.AlertType.ERROR);
            dominioTextField.requestFocus();
            return false;
        }

        if(automotorServicio.buscarAutomotor(dominio) != null) {
            mensajero.mostrarMensaje("Advertencia", "El Automotor " + dominio + " ya existe.",Alert.AlertType.WARNING);
            return false;
        }

        var automotor = new Automotor();
        if (recolectarDatosFormulario(automotor)){
            System.out.println(automotor);
            automotorServicio.guardarAutomotor(automotor);
            generarBoletas(automotor.getDominio());
            mensajero.mostrarMensaje("Información", "Automotor guardado correctamente.", Alert.AlertType.INFORMATION);
            listarAutomotor();
            limpiarFormulario();
        }
        return true;
    }

    private void generarBoletas(String dominio){
        Automotor automotor = new Automotor();
        automotor.setDominio(dominio);
        Calendar calendar = Calendar.getInstance();
        List<Boleta> boletas = new ArrayList<>();
        int mes = calendar.get(Calendar.MONTH) + 2;
        System.out.println(mes);
        float importe = 6000;
        BigDecimal importeBD = BigDecimal.valueOf(importe);
        int anio = calendar.get(Calendar.YEAR);
        for(int i = mes; i <= 12; i++) {
            Boleta boleta = new Boleta();
            boleta.setDominio(automotor);
            boleta.setCuota(i);
            boleta.setEstado(false);
            boleta.setImporte(importeBD);
            GregorianCalendar fechaV = new GregorianCalendar(anio, i-1, 30);
            boleta.setFechaVencimiento(fechaV.getTime());
            System.out.println(boleta);
            boletas.add(boleta);
        }
        for(Boleta boleta : boletas){
            boletaServicio.guardarBoleta(boleta);
        }
    }

    private boolean recolectarDatosFormulario(Automotor automotor) {

        automotor.setDominio(dominioTextField.getText());

        Titular titular = titularServicio.buscarTitular(Integer.valueOf(dniTextField.getText()));

        if(titular == null) {
            mensajero.mostrarMensaje("Error", "Titular no encontrado.", Alert.AlertType.ERROR);
            return false;
        }

        automotor.setDniTitular(titular);
        automotor.setMarca(marcaTextField.getText());
        automotor.setModelo(modeloTextField.getText());
        automotor.setAnioFabricacion(Integer.parseInt(anioTextField.getText()));
        Municipio municipio = municipioServicio.buscarMunicipio(municipioComboBox.getSelectionModel().getSelectedIndex()+1);

        if(municipio == null) {
            mensajero.mostrarMensaje("Error", "Municipio no encontrado.", Alert.AlertType.ERROR);
            return false;
        }
        automotor.setIdMunicipio(municipio);
        return true;
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
