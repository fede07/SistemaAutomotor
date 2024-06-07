package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.clase.Escritor;
import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.servicio.boleta.BoletaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private final BoletaServicio boletaServicio;
    private final Escritor escritor;

    @FXML
    private ComboBox<Integer> fechaapartirComboBox;

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
    private final ObservableList<Integer> mesesList = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12);

    public VisualizarFacPagControlador(BoletaServicio boletaServicio, Escritor escritor) {
        this.boletaServicio = boletaServicio;
        this.escritor = escritor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boletaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        configurarComboBox();
    }

    private void configurarComboBox() {
        fechaapartirComboBox.setItems(mesesList);
        fechaapartirComboBox.setValue(1);
    }

    private void configurarColumnas(){
        cuotaColumna.setCellValueFactory(new PropertyValueFactory<>("cuota"));
        importeColumna.setCellValueFactory(new PropertyValueFactory<>("importe"));
        fechadepagColumna.setCellValueFactory(new PropertyValueFactory<>("fpag"));

    }

    public void buscarBoletas() {
        List<Boleta> boletas = getBoletas();
        if (boletas == null) return;
        boletaList.addAll(boletas);
        boletaTabla.setItems(boletaList);
    }

    private List<Boleta> getBoletas() {
        String dominio = dominioTextField.getText();
        if (dominio.isEmpty()) {
            mostrarMensaje("Error", "El dominio no puede estar vacio");
            return null;
        }
        boletaList.clear();
        Automotor automotor = new Automotor();
        automotor.setDominio(dominio);
        int fecha = fechaapartirComboBox.getValue() - 1;
        List<Boleta> boletas = boletaServicio.buscarBoletasPorDomonioYFechaDesde(automotor,true, fecha);
        if (boletas.isEmpty()) {
            mostrarMensaje("Info", "No se encontraron facturas para el dominio " + dominio);
        }
        return boletas;
    }

    private void mostrarMensaje(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void exportarFacturasPagadas(){
        if (boletaList.isEmpty()) {
            mostrarMensaje("Error", "No hay facturas para exportar");
            return;
        }
        escritor.exportar(getBoletas());

    }

}
