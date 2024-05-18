package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.servicio.boleta.BoletaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class BoletaControlador implements Initializable {

    private final BoletaServicio boletaServicio;

    public Button buscarButton;

    @FXML
    private TableView<Boleta> boletasTable = new TableView<>();

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

    public BoletaControlador(BoletaServicio boletaServicio) {
        this.boletaServicio = boletaServicio;
    }

    @Setter
    private Scene mainScene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boletasTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
    }

    private void configurarColumnas() {
        anioBoletaColumna.setCellValueFactory(new PropertyValueFactory<>("anioBoleta"));
        cuotaBoletaColumna.setCellValueFactory(new PropertyValueFactory<>("cuota"));
        fvenBoletaColumna.setCellValueFactory(new PropertyValueFactory<>("fven"));
        importeBoletaColumna.setCellValueFactory(new PropertyValueFactory<>("importe"));
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
        List<Boleta> boletas = boletaServicio.buscarBoletasPorDominio(automotor);
        if (boletas.isEmpty()) {
            mostrarMensaje("Info", "No se encontraron facturas con el dominio " + dominio);
        }
        boletaList.addAll(boletas);
        boletasTable.setItems(boletaList);
    }

    private void mostrarMensaje(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void abrirMainScene(ActionEvent actionEvent) {
        Stage mainStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        mainStage.setScene(mainScene);
    }

}
