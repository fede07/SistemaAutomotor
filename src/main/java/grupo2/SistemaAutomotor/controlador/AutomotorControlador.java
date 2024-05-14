package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Municipio;
import grupo2.SistemaAutomotor.modelo.Titular;
import grupo2.SistemaAutomotor.servicio.automotor.AutomotorServicio;
import grupo2.SistemaAutomotor.servicio.municipio.MunicipioServicio;
import grupo2.SistemaAutomotor.servicio.titular.TitularServicio;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AutomotorControlador implements Initializable {

    private final AutomotorServicio automotorServicio;
    private final TitularServicio titularServicio;
    private final MunicipioServicio municipioServicio;
    @Setter
    private Scene mainScene;
    public Button agregarBoton;
    public Button modificarBoton;
    public Button EliminarBoton;
    public Button limpiarBoton;

    @FXML
    private TableView<Automotor> automotorTabla = new TableView<>();

    @FXML
    private TableColumn<Automotor, String> dominioAutomotorColumna;

    @FXML
    private TableColumn<Titular, Integer> titularAutomotorColumna;

    @FXML
    private TableColumn<Automotor, String> modeloAutomotorColumna;

    @FXML
    private TableColumn<Automotor, String> marcaAutomotorColumna;

    @FXML
    private TableColumn<Automotor, Integer> anioAutomotorColumna;

    @FXML
    private TableColumn<Automotor, Integer> municipioAutomotorColumna;

    private final ObservableList<Automotor> automotorList = FXCollections.observableArrayList();

    @FXML
    private TextField dominioAutomotorTexto;

    @FXML
    private TextField titularAutomotorTexto;

    @FXML
    private TextField modeloAutomotorTexto;

    @FXML
    private TextField marcaAutomotorTexto;

    @FXML
    private TextField anioAutomotorTexto;

    @FXML
    private ComboBox<Municipio> municipioComboBox;

    private String dominioAutomotorInterno;

    public AutomotorControlador(AutomotorServicio automotorServicio, TitularServicio titularServicio, MunicipioServicio municipioServicio) {
        this.automotorServicio = automotorServicio;
        this.titularServicio = titularServicio;
        this.municipioServicio = municipioServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        automotorTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarAutomotores();
        listarMunicipios();
    }

    private void configurarColumnas() {
        dominioAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("dominio"));
        titularAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("dniTitular"));
        modeloAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        marcaAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("marca"));
        anioAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("anioFabricacion"));
        municipioAutomotorColumna.setCellValueFactory(new PropertyValueFactory<>("nombreMunicipio"));
    }

    private void listarAutomotores() {
        automotorList.clear();
        automotorList.addAll(automotorServicio.listarAutomotor());
        automotorTabla.setItems(automotorList);
    }

    private void listarMunicipios() {
        List<Municipio> municipioList = municipioServicio.listarMunicipios();
        for (Municipio municipio : municipioList) {
            municipioComboBox.getItems().add(municipio);
        }
        municipioComboBox.setEditable(false);
    }

    public void agregarAutomotor() {
        if(dominioAutomotorTexto.getText().isEmpty()) {
            mostrarMensaje("Error Validacion", "El campo Dominio no puede estar vacio");
            dominioAutomotorTexto.requestFocus();
            return;
        }
        var automotor = new Automotor();
        if (recolectarDatosFormulario(automotor)){
            //mostrarDatos(automotor); //DEBUG
            automotorServicio.guardarAutomotor(automotor);
            mostrarMensaje("Información", "Automotor guardado correctamente");
            limpiarFormulario();
            listarAutomotores();
            //TODO generarBoletas();
        }
    }

    public void cargarAutomotorFormulario() {
        var automotor = automotorTabla.getSelectionModel().getSelectedItem();
        if (automotor != null) {
            dominioAutomotorInterno = automotor.getDominio();
            dominioAutomotorTexto.setText(automotor.getDominio());
            titularAutomotorTexto.setText(String.valueOf(automotor.getDniTitular()));
            modeloAutomotorTexto.setText(String.valueOf(automotor.getModelo()));
            marcaAutomotorTexto.setText(String.valueOf(automotor.getMarca()));
            anioAutomotorTexto.setText(String.valueOf(automotor.getAnioFabricacion()));
            municipioComboBox.getSelectionModel().select(automotor.getIdMunicipio()-1);
        }
    }

    private void mostrarMensaje(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @SuppressWarnings("unused")
    private void mostrarDatos(Automotor automotor) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion");
        alert.setHeaderText(null);
        alert.setContentText(automotor.toString());
        alert.showAndWait();
    }

    private boolean recolectarDatosFormulario(Automotor automotor) {

        automotor.setDominio(dominioAutomotorTexto.getText());

        Titular titular = titularServicio.buscarTitular(Integer.valueOf(titularAutomotorTexto.getText()));

        if(titular == null) {
            mostrarMensaje("Error Validacion", "Titular no encontrado");
            return false;
        }

        automotor.setDniTitular(titular);

        automotor.setMarca(marcaAutomotorTexto.getText());
        automotor.setModelo(modeloAutomotorTexto.getText());
        automotor.setAnioFabricacion(Integer.parseInt(anioAutomotorTexto.getText()));

        Municipio municipio = municipioServicio.buscarMunicipio(municipioComboBox.getSelectionModel().getSelectedIndex()+1);


        //TODO Crear desplegable de municipios, el siguiente if seria redundante. Quitar mensajes de error del metodo
        if(municipio == null) {
            mostrarMensaje("Error Validacion", "Municipio no encontrado");
            return false;
        }
        automotor.setIdMunicipio(municipio);

        return true;
    }

    public void modificarAutomotor() {
        if(dominioAutomotorInterno == null){
            mostrarMensaje("Informacion", "Debe Seleccionar un automotor");
            return;
        }
        if(dominioAutomotorTexto.getText().isEmpty()) {
            mostrarMensaje("Error Validacion", "El campo Dominio no puede estar vacio");
            dominioAutomotorTexto.requestFocus();
            return;
        }
        var automotor = new Automotor();
        recolectarDatosFormulario(automotor);
        //mostrarDatos(automotor);
        automotorServicio.guardarAutomotor(automotor);
        mostrarMensaje("Informacion", "Automotor modificado correctamente");
        limpiarFormulario();
        listarAutomotores();
    }

    public void eliminarAutomotor() {
        if(dominioAutomotorInterno == null){
            mostrarMensaje("Informacion", "Debe Seleccionar un automotor");
            return;
        }
        if(dominioAutomotorTexto.getText().isEmpty()) {
            mostrarMensaje("Error Validacion", "Debe Seleccionar un automotor");
            return;
        }
        var automotor = new Automotor();
        recolectarDatosFormulario(automotor);
        //mostrarDatos(automotor);
        automotorServicio.eliminarAutomotor(automotor.getDominio());
        mostrarMensaje("Informacion", "Automotor " + automotor.getDominio() + " eliminado correctamente");
        limpiarFormulario();
        listarAutomotores();
    }

    public void limpiarFormulario() {
        dominioAutomotorInterno = null;
        dominioAutomotorTexto.clear();
        titularAutomotorTexto.clear();
        modeloAutomotorTexto.clear();
        marcaAutomotorTexto.clear();
        anioAutomotorTexto.clear();
        municipioComboBox.getSelectionModel().clearSelection();
    }

    public void abrirMainScene(ActionEvent actionEvent) {
        Stage mainStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        mainStage.setScene(mainScene);
    }

}
