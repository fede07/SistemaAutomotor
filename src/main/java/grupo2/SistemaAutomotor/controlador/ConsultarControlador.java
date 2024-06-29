package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Titular;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ConsultarControlador implements Initializable {
    public TextField dominioTextField;
    public Button buscarAutomotorButton;
    public TableColumn<Automotor, String> dominioColumna;
    public TableColumn<Automotor, Integer> titularColumna;
    public TableColumn<Automotor, String> marcaColumna;
    public TableColumn<Automotor, String> modeloColumna;
    public TableColumn<Automotor, String> municipioColumna;
    public TableColumn<Automotor, Integer> anioColumna;
    public TextField dniTextField;
    public Button buscarDniButton;
    public TableColumn<Titular, Integer> dniTitularColumna;
    public TableColumn<Titular, String>nombreTitularColumna;
    public TableColumn<Titular, String> apellidoTitularColumna;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
