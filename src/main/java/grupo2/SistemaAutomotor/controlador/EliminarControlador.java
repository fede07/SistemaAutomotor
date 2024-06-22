package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Titular;
import grupo2.SistemaAutomotor.servicio.automotor.AutomotorServicio;
import grupo2.SistemaAutomotor.servicio.boleta.BoletaServicio;
import grupo2.SistemaAutomotor.servicio.titular.TitularServicio;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class EliminarControlador implements Initializable {
    private final AutomotorServicio automotorServicio;
    private final TitularServicio titularServicio;
    private final BoletaServicio boletaServicio;
    @FXML
    private TextField dominioTextField;
    @FXML
    private TextField dniTextField;
    @FXML
    private Button eliminarButton;

    public EliminarControlador(AutomotorServicio automotorServicio, TitularServicio titularServicio, BoletaServicio boletaServicio) {
        this.automotorServicio = automotorServicio;
        this.titularServicio = titularServicio;
        this.boletaServicio = boletaServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eliminarButton.setOnAction(e -> eliminarAutomotor());
    }

    public void eliminarAutomotor() {
        if (dominioTextField.getText().isEmpty()) {
            mostrarMensaje("Informacion", "Ingrese el dominio del automotor");
            return;
        }
        if (dniTextField.getText().isEmpty()) {
            mostrarMensaje("Error Validacion", "Ingrese un DNI");
            return;
        }
        var automotor = new Automotor();
        if (recolectarDatosFormulario(automotor)) {
            //mostrarDatos(automotor);
            boletaServicio.eliminarBoletas(automotor);
            automotorServicio.eliminarAutomotor(automotor.getDominio());
            mostrarMensaje("Informacion", "Automotor " + automotor.getDominio() + " eliminado correctamente");
        }
    }

    private boolean recolectarDatosFormulario(Automotor automotor) {
        automotor.setDominio(dominioTextField.getText());
        Titular titular = titularServicio.buscarTitular(Integer.valueOf(dniTextField.getText()));
        if (titular == null) {
            mostrarMensaje("Error Validacion", "Titular no encontrado");
            return false;
        }
        automotor.setDniTitular(titular);
        return true;
    }

    private void mostrarMensaje(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}