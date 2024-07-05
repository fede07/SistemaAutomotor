package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.clase.Mensajero;
import grupo2.SistemaAutomotor.clase.Validador;
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

    private final Validador validador = new Validador();
    private final Mensajero mensajero = new Mensajero();

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
        String dominio = dominioTextField.getText();
        if (dominio.isEmpty()) {
            mensajero.mostrarMensaje("Información", "Ingrese el dominio del automotor.", Alert.AlertType.INFORMATION);
            return;
        }

        if(validador.isNotDominio(dominio)) {
            mensajero.mostrarMensaje("Error", "El dominio no es válido.", Alert.AlertType.ERROR);
        }

        String dni = dniTextField.getText();
        if (dni.isEmpty()) {
            mensajero.mostrarMensaje("Error", "Ingrese un DNI.", Alert.AlertType.ERROR);
            return;
        }

        if(validador.isNotNumeric(dni)) {
            mensajero.mostrarMensaje("Error", "El DNI no es válido.", Alert.AlertType.ERROR);
            return;
        }

        var automotor = new Automotor();
        if (recolectarDatosFormulario(automotor)) {
            //mostrarDatos(automotor);
            if(mensajero.mostrarConfirmacion("Eliminar", "¿Desea eliminar el automotor con dominio " + automotor.getDominio()+ "?")){
                boletaServicio.eliminarBoletas(automotor);
                automotorServicio.eliminarAutomotor(automotor.getDominio());
                mensajero.mostrarMensaje("Información", "Automotor " + automotor.getDominio() + " eliminado correctamente.", Alert.AlertType.INFORMATION);
            }
        }
    }

    private boolean recolectarDatosFormulario(Automotor automotor) {


        automotor.setDominio(dominioTextField.getText());
        if(automotorServicio.buscarAutomotor(automotor.getDominio()) == null) {
            mensajero.mostrarMensaje("Error", "El automotor no existe", Alert.AlertType.ERROR);
            return false;
        }
        Titular titular = titularServicio.buscarTitular(Integer.valueOf(dniTextField.getText()));
        if (titular == null) {
            mensajero.mostrarMensaje("Error", "Titular no encontrado.", Alert.AlertType.ERROR);
            return false;
        }
        automotor.setDniTitular(titular);

        return true;
    }


}