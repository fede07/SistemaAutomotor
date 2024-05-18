package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.servicio.boleta.BoletaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

@Component
public class BoletaControlador implements Initializable {

    private final BoletaServicio boletaServicio = new BoletaServicio();

    public Button buscarButton;

    @FXML
    private TableView<Boleta> boletaTable = new TableView<>();

    @FXML
    private TableColumn<Boleta, Date> anioBoletaColumna;

    @FXML
    private TableColumn<Boleta, Integer> cuotaBoletaColumna;

    @FXML
    private TableColumn<Boleta, Date> fvenBoletaColumna;

    @FXML
    private TableColumn<Boleta, BigDecimal> importeBoletaColumna;

    private final ObservableList<Boleta> boletaList = FXCollections.observableArrayList();

    @FXML
    private TextField dominioTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boletaTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarBoletas();
    }

    private void configurarColumnas() {
        anioBoletaColumna.setCellValueFactory(new PropertyValueFactory<>("anioBoleta"));
        cuotaBoletaColumna.setCellValueFactory(new PropertyValueFactory<>("cuota"));
        fvenBoletaColumna.setCellValueFactory(new PropertyValueFactory<>("fven"));
        importeBoletaColumna.setCellValueFactory(new PropertyValueFactory<>("importe"));
    }

    private void listarBoletas() {
        boletaList.clear();
        boletaList.addAll(boletaServicio.listarBoletas());
        boletaTable.setItems(boletaList);
    }

    private void buscarBoleta(String dominioBoleta) {
        boletaList.clear();
    }
}
