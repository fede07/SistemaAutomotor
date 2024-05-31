package grupo2.SistemaAutomotor.controlador;

import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.modelo.Municipio;
import grupo2.SistemaAutomotor.modelo.Recaudacion;
import grupo2.SistemaAutomotor.repositorio.AutomotorRepositorio;
import grupo2.SistemaAutomotor.servicio.automotor.AutomotorServicio;
import grupo2.SistemaAutomotor.servicio.boleta.BoletaServicio;
import grupo2.SistemaAutomotor.servicio.municipio.MunicipioServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class RecadacionMunicipioControlador implements Initializable {

    private final AutomotorServicio automotorServicio;
    private final BoletaServicio boletaServicio;
    private final MunicipioServicio municipioServicio;
    @FXML
    private TableView<Recaudacion> recaudacionTable;
    @FXML
    private TableColumn<Recaudacion, String> municipioColumna;
    @FXML
    private TableColumn<Recaudacion, Float> recaudacionColumna;

    private AutomotorServicio automotorRepositorio;

    private final ObservableList<Recaudacion> recaudacionList = FXCollections.observableArrayList();

    public RecadacionMunicipioControlador(AutomotorServicio automotorServicio, BoletaServicio boletaServicio, MunicipioServicio municipioServicio) {
        this.automotorServicio = automotorServicio;
        this.boletaServicio = boletaServicio;
        this.municipioServicio = municipioServicio;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnas();
        listarRecaudacion();
    }

    private void listarRecaudacion() {
        recaudacionList.clear();
        List<Municipio> municipioList = municipioServicio.listarMunicipios();
        for(Municipio municipio : municipioList) {
            Recaudacion recaudacion = new Recaudacion();
            recaudacion.setNombreMunicipio(municipio.getNombre());
            float total = 0;
//            List<Boleta> boletas = boletaServicio.buscarBoletasPorMunicipio(municipio);
//            for(Boleta boleta : boletas) {
//                total = total + boleta.getImporte().floatValue();
//            }
            recaudacion.setTotal(total);
            recaudacionList.add(recaudacion);
        }
        recaudacionTable.setItems(recaudacionList);
    }

    private void configurarColumnas(){
        municipioColumna.setCellValueFactory(new PropertyValueFactory<>("nombreMunicipio"));
        recaudacionColumna.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

}
