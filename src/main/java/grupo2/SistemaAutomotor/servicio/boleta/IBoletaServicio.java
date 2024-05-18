package grupo2.SistemaAutomotor.servicio.boleta;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import java.util.List;

public interface IBoletaServicio {
    public List<Boleta> listarBoletas();
    public Boleta buscarBoleta(Integer id);
    public void guardarBoleta(Boleta boleta);
    public void eliminarBoleta(Integer id);
    public List<Boleta> buscarBoletasPorDominio(Automotor automotor);
}
