package grupo2.SistemaAutomotor.servicio.boleta;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.modelo.Municipio;
import grupo2.SistemaAutomotor.repositorio.BoletaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class BoletaServicio implements IBoletaServicio {

    @Autowired
    private BoletaRepositorio boletaRepositorio;

    @Override
    public List<Boleta> listarBoletas() {
        return boletaRepositorio.findAll();
    }

    @Override
    public Boleta buscarBoleta(Integer id) {
        return boletaRepositorio.findById(id).orElse(null);
    }

    @Override
    public void guardarBoleta(Boleta boleta) {
        boletaRepositorio.save(boleta);
    }

    @Override
    public void eliminarBoleta(Integer id) {
        boletaRepositorio.deleteById(id);
    }

    @Override
    public List<Boleta> buscarBoletasPorDominio(Automotor automotor) {
        return boletaRepositorio.findBoletasByDominio(automotor);
    }

    @Override
    public BigDecimal sumarBoletasPorMunicipio(Municipio municipio) {
        return boletaRepositorio.sumAllByMunicipio(municipio);
    }

    @SuppressWarnings("unused")
    public List<Boleta> buscarBoletasPorDominioYEestado(Automotor automotor, Boolean estado) {
        return boletaRepositorio.findBoletasByDominioAndEstado(automotor, estado);
    }

    public List<Boleta> buscarBoletasPorMunicipio(Municipio municipio){
        return boletaRepositorio.findAllByMunicipio(municipio);
    }

    @Override
    public List<Boleta> buscarBoletasPorDomonioYFechaDesde(Automotor automotor, boolean estado, int cuota) {
        return boletaRepositorio.findBoletasByDominioAndEstadoAndCuotaAfter(automotor, estado, cuota);
    }

    @Override
    public List<Boleta> buscarBoletasPorDomonioYFechaHasta(Automotor automotor, boolean estado, int cuota) {
        return boletaRepositorio.findBoletasByDominioAndEstadoAndCuotaAfter(automotor, estado, cuota);
    }

    @Override
    public List<Boleta> buscarBoletaPorDominioEntre(Automotor automotor, boolean estado, int fechaDesde, int fechaHasta) {
        return boletaRepositorio.findBoletasByDominioAndEstadoAndCuotaBetween(automotor, estado, fechaDesde,fechaHasta);
    }

    @Override
    public BigDecimal sumarBoletasImpagasPorDominio(String dominio) {
        return boletaRepositorio.SumAllByDominio(dominio);
    }

    public void eliminarBoletas(Automotor dominio){
        boletaRepositorio.deleteAllBydominio(dominio);
    }

}