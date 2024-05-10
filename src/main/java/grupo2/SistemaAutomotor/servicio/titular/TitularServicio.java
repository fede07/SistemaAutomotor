package grupo2.SistemaAutomotor.servicio.titular;

import grupo2.SistemaAutomotor.modelo.Titular;
import grupo2.SistemaAutomotor.repositorio.TitularRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitularServicio implements ITitularServicio{

    @Autowired
    private TitularRepositorio titularRepositorio;

    @Override
    public List<Titular> listarTitulares() {
        return titularRepositorio.findAll();
    }

    @Override
    public Titular buscarTitular(Integer dni) {
        return titularRepositorio.findById(dni).orElse(null);
    }

    @Override
    public void guardarTitular(Titular titular) {
        titularRepositorio.save(titular);
    }

    @Override
    public void eliminarTitular(Titular titular) {
        titularRepositorio.delete(titular);
    }

}
