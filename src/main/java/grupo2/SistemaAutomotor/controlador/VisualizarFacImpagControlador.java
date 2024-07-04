package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.clase.Mensajero;
import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.servicio.boleta.BoletaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.List;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;

@Component
public class VisualizarFacImpagControlador implements Initializable {

    public TextField dominioTextField;
    public Button buscarButton;
    private final BoletaServicio boletaServicio;
    @FXML
    private Button realizarPagoButton;

    @FXML
    private TableView<Boleta> boletaTabla = new TableView<>();

    @FXML
    private TableColumn<Boleta, Integer> cuotaColumna;

    @FXML
    private TableColumn<Boleta, Float> importeColumna;

    @FXML
    private TableColumn<Boleta, Date> fechadevenColumna;

    @FXML
    private ComboBox<Integer> fechaapartirComboBox;

    @FXML
    private ComboBox<Integer> fechaHastaComboBox;

    private final ObservableList<Boleta> boletaList = FXCollections.observableArrayList();
    private final ObservableList<Integer> mesesList = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12);
    private final Mensajero mensajero = new Mensajero();
    public VisualizarFacImpagControlador(BoletaServicio boletaServicio) {
        this.boletaServicio = boletaServicio;
    }
    private Automotor automotorGuardado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boletaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        configurarComboBox();
        automotorGuardado = new Automotor();
        realizarPagoButton.setOnAction(e->pagarBoletas());
    }

    private void configurarComboBox() {
        fechaapartirComboBox.setItems(mesesList);
        fechaapartirComboBox.setValue(1);
        fechaHastaComboBox.setItems(mesesList);
        fechaHastaComboBox.setValue(12);
    }

    private void configurarColumnas(){
        cuotaColumna.setCellValueFactory(new PropertyValueFactory<>("cuota"));
        importeColumna.setCellValueFactory(new PropertyValueFactory<>("importeFloat"));
        fechadevenColumna.setCellValueFactory(new PropertyValueFactory<>("fven"));
        importeColumna.getStyleClass().add("table-column-right");
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        importeColumna.setCellFactory(tc-> new TableCell<>() {
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
        String dominio = dominioTextField.getText();
        if (dominio.isEmpty()) {
            mensajero.mostrarMensaje("Error", "El dominio no puede estar vacio", Alert.AlertType.WARNING);
            return;
        }
        boletaList.clear();
        Automotor automotor = new Automotor();
        automotor.setDominio(dominio);
        automotorGuardado.setDominio(dominio);
        int cuotaDesde = fechaapartirComboBox.getValue();
        int cuotaHasta = fechaHastaComboBox.getValue();
        List<Boleta> boletas = boletaServicio.buscarBoletaPorDominioEntre(automotor,false,cuotaDesde,cuotaHasta);
        if (boletas.isEmpty()) {
            mensajero.mostrarMensaje("Información", "No se encontraron facturas con el dominio " + dominio, Alert.AlertType.INFORMATION);
        }
        boletaList.addAll(boletas);
        boletaTabla.setItems(boletaList);
    }


    public void pagarBoletas(){
        Boleta boleta = boletaTabla.getSelectionModel().getSelectedItem();
        if (boleta == null) {
            mensajero.mostrarMensaje("Información", "Seleccione una factura", Alert.AlertType.INFORMATION);
            return;
        }
        Calendar calendar = Calendar.getInstance();
        boleta.setEstado(true);
        Date hoy = calendar.getTime();
        boleta.setFechaPago(hoy);
        boletaServicio.guardarBoleta(boleta);
        mensajero.mostrarMensaje("Informacion", "Factura Pagada", Alert.AlertType.INFORMATION);
        actualizarBoletas();
    }

    private void actualizarBoletas(){
        boletaList.clear();
        dominioTextField.clear();
        dominioTextField.setText(automotorGuardado.getDominio());
        buscarBoletas();
    }

}
