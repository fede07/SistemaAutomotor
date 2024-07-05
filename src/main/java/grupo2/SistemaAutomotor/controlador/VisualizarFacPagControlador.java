package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.clase.Escritor;
import grupo2.SistemaAutomotor.clase.Mensajero;
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
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.List;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class VisualizarFacPagControlador implements Initializable {

    private final AutomotorServicio automotorServicio;
    private final TitularServicio titularServicio;
    private final BoletaServicio boletaServicio;
    private final Escritor escritor;

    @FXML
    private ComboBox<Integer> fechaapartirComboBox;

    @FXML
    private ComboBox<Integer> fechaHastaComboBox;

    @FXML
    private TableView<Boleta> boletaTabla = new TableView<>();

    @FXML
    private TableColumn<Boleta, Integer> cuotaColumna;

    @FXML
    private TableColumn<Boleta, Float> importeColumna;

    @FXML
    private TableColumn<Boleta, Date> fechadepagColumna;

    @FXML
    private Button detalleButton;

    @FXML
    private TextField dominioTextField;

    @FXML
    private Button buscarButton;

    @FXML
    private Button exportarButton;
    private final ObservableList<Boleta> boletaList = FXCollections.observableArrayList();
    private final ObservableList<Integer> mesesList = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    private final Mensajero mensajero = new Mensajero();
    private List<Boleta> boletasCache;

    public VisualizarFacPagControlador(BoletaServicio boletaServicio, Escritor escritor, AutomotorServicio automotorServicio, TitularServicio titularServicio) {
        this.boletaServicio = boletaServicio;
        this.escritor = escritor;
        this.automotorServicio = automotorServicio;
        this.titularServicio = titularServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boletaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        configurarComboBox();
        detalleButton.setOnAction(e -> mostrarDetalle());
        buscarButton.setOnAction(e -> buscarBoletas());
        exportarButton.setOnAction(e -> exportarFacturasPagadas());
    }

    private void configurarComboBox() {
        fechaapartirComboBox.setItems(mesesList);
        fechaapartirComboBox.setValue(1);
        fechaHastaComboBox.setItems(mesesList);
        fechaHastaComboBox.setValue(12);
    }

    private void configurarColumnas() {
        cuotaColumna.setCellValueFactory(new PropertyValueFactory<>("cuota"));
        importeColumna.setCellValueFactory(new PropertyValueFactory<>("importeFloat"));
        fechadepagColumna.setCellValueFactory(new PropertyValueFactory<>("fpag"));
        importeColumna.getStyleClass().add("table-column-right");
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        importeColumna.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(currency.format(item));
                }
            }
        });
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
            mensajero.mostrarMensaje("Error", "El dominio no puede estar vacio", Alert.AlertType.WARNING);
            return null;
        }
        boletaList.clear();
        Automotor automotor = new Automotor();
        automotor.setDominio(dominio);
        int cuotaDesde = fechaapartirComboBox.getValue();
        int cuotaHasta = fechaHastaComboBox.getValue();
        boletasCache = boletaServicio.buscarBoletaPorDominioEntre(automotor, true, cuotaDesde, cuotaHasta);
        if (boletasCache.isEmpty()) {
            mensajero.mostrarMensaje("Información", "No se encontraron facturas para el dominio " + dominio, Alert.AlertType.WARNING);
        }
        return boletasCache;
    }

    public void exportarFacturasPagadas() {
        if (boletaList.isEmpty()) {
            mensajero.mostrarMensaje("Error", "No hay facturas para exportar", Alert.AlertType.CONFIRMATION);
            return;
        }
        if (escritor.exportar(boletasCache)) {
            mensajero.mostrarMensaje("Información", "Factura exportada correctamente", Alert.AlertType.CONFIRMATION);
        } else {
            mensajero.mostrarMensaje("Error", "Error de Exportacion", Alert.AlertType.ERROR);
        }

    }

    private void mostrarDetalle() {

        Boleta boleta = boletaTabla.getSelectionModel().getSelectedItem();
        if (boleta == null) {
            mensajero.mostrarMensaje("Información", "Seleccione una factura", Alert.AlertType.WARNING);
            return;
        }

        Automotor automotor = automotorServicio.buscarAutomotor(boleta.getDominio());
        Titular titular = titularServicio.buscarTitular(automotor.getDniTitular());

        Alert alert = getAlert(titular, automotor, boleta);
        alert.showAndWait();
    }

    private static Alert getAlert(Titular titular, Automotor automotor, Boleta boleta) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalle de factura");
        alert.setHeaderText(null);
        alert.setGraphic(null);

        String message =
                "Cliente: " + titular.getNombre() + " " + titular.getApellido() + "\n" +
                        "Dominio: " + automotor.getDominio() + "\n\n" +
                        "Id Factura: " + boleta.getIdBoleta() + "\n" +
                        "Cuota: " + boleta.getCuota() + "\n" +
                        "Importe: $" + boleta.getImporte() + "\n" +
                        "Fecha de Vencimiento: " + boleta.getFven() + "\n" +
                        "Fecha de Pago: " + boleta.getFpag() + "\n";
        alert.setContentText(message);
        return alert;
    }

}
