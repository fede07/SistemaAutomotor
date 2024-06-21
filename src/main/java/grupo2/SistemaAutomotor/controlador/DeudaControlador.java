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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    @FXML
    private Label totalAdeudado;
    @FXML
    private Label cantVehiDeuda;
    @FXML
    private Label porVehiDeuda;
    @FXML
    public Button bottonBuscarDeuda;
    @FXML
    public TextField InputBuscarDeuda;

    private final ObservableList<AutomotorDeuda> deudaList = FXCollections.observableArrayList();

    public DeudaControlador(AutomotorServicio automotorServicio, BoletaServicio boletaServicio) {
        this.automotorServicio = automotorServicio;
        this.boletaServicio = boletaServicio;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deudaTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setearParametros();
        configurarColumnas();
    }

    public void buscarDeuda() {
        String dominio = InputBuscarDeuda.getText();
        if (dominio.isEmpty()) {
            mostrarMensaje("Error", "El dominio no puede estar vacio");
            return;
        }
        deudaList.clear();
        Automotor automotor;
        automotor = (automotorServicio.buscarAutomotor(dominio));
        if (automotor == null) {
            mostrarMensaje("Info", "No se encontro el Automotor con dominio " + dominio);
            return;
        }
        BigDecimal deuda = boletaServicio.sumarBoletasImpagasPorDominio(automotor.getDominio());
        AutomotorDeuda automotorDeuda = new AutomotorDeuda();
        automotorDeuda.setAutomotor(automotor);
        automotorDeuda.setDeuda(deuda.floatValue());
        deudaList.add(automotorDeuda);
        deudaTable.setItems(deudaList);
    }

    private void mostrarMensaje(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void configurarColumnas() {
        dominioColumn.setCellValueFactory(new PropertyValueFactory<>("dominio"));
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dniTitular"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        deudaColumn.setCellValueFactory(new PropertyValueFactory<>("deuda"));
    }

    private void setearParametros() {
        List<Automotor> automotores = automotorServicio.listarAutomoresConDeuda();
        List<AutomotorDeuda> automotoresDeudas = new ArrayList<>();
        float deudaTotal= 0;
        for (Automotor automotor : automotores) {
            AutomotorDeuda automotorDeuda = new AutomotorDeuda();
            automotorDeuda.setAutomotor(automotor);
            automotorDeuda.setDeuda(boletaServicio.sumarBoletasImpagasPorDominio(automotor.getDominio()).floatValue());
            automotoresDeudas.add(automotorDeuda);
            deudaTotal += automotorDeuda.getDeuda();
        }
        totalAdeudado.setText("$"+ deudaTotal);
        float porcentaje = ((float) automotoresDeudas.size() /automotorServicio.cantidadAutomotores())*100;
        System.out.println(porcentaje);
        porVehiDeuda.setText(porcentaje +"%");
        cantVehiDeuda.setText(Integer.toString(automotoresDeudas.size()));
    }

}