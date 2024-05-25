package grupo2.SistemaAutomotor.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class SampleController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private void handleShowView1(ActionEvent e) {
        loadFXML(getClass().getResource("/sample/view_1.fxml"));
    }

    @FXML
    private void handleShowView2(ActionEvent e) {
        loadFXML(getClass().getResource("/sample/view_2.fxml"));
    }

    @FXML
    private void handleShowView3(ActionEvent e) {
        loadFXML(getClass().getResource("/sample/view_3.fxml"));
    }

    private void loadFXML(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            mainBorderPane.setCenter(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}