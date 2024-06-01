package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.AutomotorDeuda;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.modelo.Titular;
import grupo2.SistemaAutomotor.servicio.automotor.AutomotorServicio;
import grupo2.SistemaAutomotor.servicio.boleta.BoletaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DeudaControlador implements Initializable {

    private final AutomotorServicio automotorServicio;
    private final BoletaServicio boletaServicio;

    @FXML
    private TableView<AutomotorDeuda> deudaTable = new TableView<>();
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

    private final ObservableList<AutomotorDeuda> deudaList = FXCollections.observableArrayList();

    public DeudaControlador(AutomotorServicio automotorServicio, BoletaServicio boletaServicio) {
        this.automotorServicio = automotorServicio;
        this.boletaServicio = boletaServicio;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deudaTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarAutomotores();
    }

    private void configurarColumnas() {
        dominioColumn.setCellValueFactory(new PropertyValueFactory<>("dominio"));
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dniTitular"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        deudaColumn.setCellValueFactory(new PropertyValueFactory<>("deuda"));
    }

    private void listarAutomotores() {
        deudaList.clear();
        List<Automotor> automotores = automotorServicio.listarAutomoresConDeuda();
        List<AutomotorDeuda> automotoresDeudas = new ArrayList<>();
        for (Automotor automotor : automotores) {
            AutomotorDeuda automotorDeuda = new AutomotorDeuda();
            automotorDeuda.setAutomotor(automotor);
            automotorDeuda.setDeuda(boletaServicio.sumarBoletasImpagasPorDominio(automotor.getDominio()).floatValue());
            automotoresDeudas.add(automotorDeuda);
        }
        deudaList.addAll(automotoresDeudas);
        deudaTable.setItems(deudaList);
    }

}
