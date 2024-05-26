package grupo2.SistemaAutomotor.servicio.boleta;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.repositorio.BoletaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public List<Boleta> buscarBoletasPorDominioYEestado(Automotor automotor, Boolean estado) {
        return boletaRepositorio.findBoletasByDominioAndEstado(automotor, estado);
    }
}