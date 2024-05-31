package grupo2.SistemaAutomotor.servicio.boleta;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.modelo.Municipio;

import java.math.BigDecimal;
import java.util.List;

public interface IBoletaServicio {
    List<Boleta> listarBoletas();
    Boleta buscarBoleta(Integer id);
    void guardarBoleta(Boleta boleta);
    void eliminarBoleta(Integer id);
    List<Boleta> buscarBoletasPorDominio(Automotor automotor);
    BigDecimal sumarBoletasPorMunicipio(Municipio municipio);
    List<Boleta> buscarBoletasPorMunicipio(Municipio municipio);
    List<Boleta> buscarBoletasPorDomonioYFechaDesde(Automotor automotor, boolean estado, int fecha);

}
