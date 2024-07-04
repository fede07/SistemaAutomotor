package grupo2.SistemaAutomotor.clase;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

@Component
public class Mensajero {

    public void mostrarMensaje(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
