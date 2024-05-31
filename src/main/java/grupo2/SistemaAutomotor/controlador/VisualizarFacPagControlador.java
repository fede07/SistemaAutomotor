package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.servicio.boleta.BoletaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.List;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class VisualizarFacPagControlador implements Initializable {

    public TextField dominioTextField;
    public Button buscarButton;
    public ComboBox fechaapartirComboBox;
    private final BoletaServicio boletaServicio;


    @FXML
    private TableView<Boleta> boletaTabla = new TableView<>();

    @FXML
    private TableColumn<Boleta, Integer> cuotaColumna;

    @FXML
    private TableColumn<Boleta, BigDecimal> importeColumna;

    @FXML
    private TableColumn<Boleta, Date> fechadepagColumna;

    @FXML

    private final ObservableList<Boleta> boletaList = FXCollections.observableArrayList();

    public VisualizarFacPagControlador(BoletaServicio boletaServicio) {
        this.boletaServicio = boletaServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boletaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        buscarButton.setOnAction(e -> buscarBoletas());
    }

    private void configurarColumnas(){
        cuotaColumna.setCellValueFactory(new PropertyValueFactory<>("cuota"));
        importeColumna.setCellValueFactory(new PropertyValueFactory<>("importe"));
        fechadepagColumna.setCellValueFactory(new PropertyValueFactory<>("fpag"));

    }

    public void listarCuotas(){
        boletaList.clear();
        boletaList.addAll(boletaServicio.listarBoletas());
        boletaTabla.setItems(boletaList);
    }

    public void buscarBoletas() {
        String dominio = dominioTextField.getText();
        if (dominio.isEmpty()) {
            mostrarMensaje("Error", "El dominio no puede estar vacio");
            return;
        }
        boletaList.clear();
        Automotor automotor = new Automotor();
        automotor.setDominio(dominio);
        List<Boleta> boletas = boletaServicio.buscarBoletasPorDominioYEestado(automotor, true);
        if (boletas.isEmpty()) {
            mostrarMensaje("Info", "No se encontraron facturas con el dominio " + dominio);
        }

        boletaList.addAll(boletas);
        boletaTabla.setItems(boletaList);
    }

    private void mostrarMensaje(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
