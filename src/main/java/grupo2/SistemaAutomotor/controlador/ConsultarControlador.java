package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.clase.Mensajero;
import grupo2.SistemaAutomotor.clase.Validador;
import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Titular;
import grupo2.SistemaAutomotor.servicio.automotor.AutomotorServicio;
import grupo2.SistemaAutomotor.servicio.titular.TitularServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ConsultarControlador implements Initializable {
    @FXML
    private TableView<Automotor> automotorTableView;
    @FXML
    private TableView<Titular> titularTableView;
    @FXML
    private TextField dominioTextField;
    @FXML
    private Button buscarAutomotorButton;
    @FXML
    private TableColumn<Automotor, String> dominioColumna;
    @FXML
    private TableColumn<Automotor, Integer> titularColumna;
    @FXML
    private TableColumn<Automotor, String> marcaColumna;
    @FXML
    private TableColumn<Automotor, String> modeloColumna;
    @FXML
    private TableColumn<Automotor, String> municipioColumna;
    @FXML
    private TableColumn<Automotor, Integer> anioColumna;
    @FXML
    private TextField dniTextField;
    @FXML
    private Button buscarDniButton;
    @FXML
    private TableColumn<Titular, Integer> dniTitularColumna;
    @FXML
    private TableColumn<Titular, String>nombreTitularColumna;
    @FXML
    private TableColumn<Titular, String> apellidoTitularColumna;

    private final AutomotorServicio automotorServicio;
    private final TitularServicio titularServicio;
    private final ObservableList<Automotor> automotorList = FXCollections.observableArrayList();
    private final ObservableList<Titular> titularList = FXCollections.observableArrayList();
    private final Validador validador = new Validador();
    private final Mensajero mensajero = new Mensajero();

    public ConsultarControlador(AutomotorServicio automotorServicio, TitularServicio titularServicio) {
        this.automotorServicio = automotorServicio;
        this.titularServicio = titularServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnasAutomotor();
        configurarColumnasTitular();
        buscarAutomotorButton.setOnAction(e -> buscarAutomotor());
        buscarDniButton.setOnAction(e -> buscarTitular());

    }

    private void configurarColumnasAutomotor() {
        dominioColumna.setCellValueFactory(new PropertyValueFactory<>("dominio"));
        titularColumna.setCellValueFactory(new PropertyValueFactory<>("dniTitular"));
        modeloColumna.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        marcaColumna.setCellValueFactory(new PropertyValueFactory<>("marca"));
        anioColumna.setCellValueFactory(new PropertyValueFactory<>("anioFabricacion"));
        municipioColumna.setCellValueFactory(new PropertyValueFactory<>("nombreMunicipio"));
    }

    private void configurarColumnasTitular() {
        dniTitularColumna.setCellValueFactory(new PropertyValueFactory<>("dni"));
        nombreTitularColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoTitularColumna.setCellValueFactory(new PropertyValueFactory<>("apellido"));
    }

    public void buscarAutomotor() {
        String dominio = dominioTextField.getText();
        if (dominio.isEmpty()) {
            mensajero.mostrarMensaje("Error", "El dominio no puede estar vacio", Alert.AlertType.ERROR);
            return;
        }

        if(validador.isNotDominio(dominio)){
            mensajero.mostrarMensaje("Error", "El dominio no es valido", Alert.AlertType.ERROR);
            return;
        }

        Automotor automotor = automotorServicio.buscarAutomotor(dominio);
        if (automotor == null) {
            mensajero.mostrarMensaje("Información", "No se encontraron facturas con el dominio " + dominio, Alert.AlertType.INFORMATION);
            return;
        }
        listarAutomotor();
    }

    private void listarAutomotor() {
        automotorList.clear();
        automotorList.addAll(automotorServicio.buscarAutomotor(dominioTextField.getText()));
        automotorTableView.setItems(automotorList);
    }

    public void buscarTitular(){
        String dni = dniTextField.getText();
        if (dni.isEmpty()) {
            mensajero.mostrarMensaje("Error", "El DNI no puede estar vacío", Alert.AlertType.ERROR);
            return;
        }
        if(validador.isNotNumeric(dni)){
            mensajero.mostrarMensaje("Error", "El DNI no puede ser alfanumerico", Alert.AlertType.ERROR);
            return;
        }
        Titular titular = titularServicio.buscarTitular(Integer.valueOf(dni));
        if (titular == null) {
            mensajero.mostrarMensaje("Información", "No se encontro el titular con DNI " + dni, Alert.AlertType.INFORMATION);
            return;
        }
        listarTitular();
    }

    private void listarTitular() {
        titularList.clear();
        titularList.add(titularServicio.buscarTitular(Integer.valueOf(dniTextField.getText())));
        titularTableView.setItems(titularList);
    }

}
