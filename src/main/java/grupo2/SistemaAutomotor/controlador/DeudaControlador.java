package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.modelo.Titular;
import grupo2.SistemaAutomotor.servicio.automotor.AutomotorServicio;
import grupo2.SistemaAutomotor.servicio.boleta.BoletaServicio;
import grupo2.SistemaAutomotor.servicio.titular.TitularServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class DeudaControlador implements Initializable {

    private final AutomotorServicio automotorServicio;
    private final TitularServicio titularServicio;
    private final BoletaServicio boletaServicio;

    @FXML
    private TableView<Automotor> deudaTable = new TableView<>();
    @FXML
    private TableColumn<Titular, Integer> dniColumn;
    @FXML
    private TableColumn<Automotor, String> dominioColumn;
    @FXML
    private TableColumn<Automotor, String> modeloColumn;
    @FXML
    private TableColumn<Automotor,String> marcaColumn;
    @FXML
    private TableColumn<Boleta,Integer> deudaColumn;

    private final ObservableList<Boleta> deudaList = FXCollections.observableArrayList();

    public DeudaControlador(AutomotorServicio automotorServicio, TitularServicio titularServicio, BoletaServicio boletaServicio) {
        this.automotorServicio = automotorServicio;
        this.titularServicio = titularServicio;
        this.boletaServicio = boletaServicio;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deudaTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
    }

    private void configurarColumnas() {
        dominioColumn.setCellValueFactory(new PropertyValueFactory<>("dominio"));
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dniTitular"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        //deudaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));

    }
}
