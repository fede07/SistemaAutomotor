package grupo2.SistemaAutomotor.servicio;

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
    public List<Titular> listarTitular() {
        return titularRepositorio.findAll();
    }

    @Override
    public Titular buscarTitular(Integer dni) {
        return titularRepositorio.getReferenceById(dni);
    }

    @Override
    public void insertarTitular(Titular titular) {
        titularRepositorio.save(titular);
    }

    @Override
    public void modificarTitular(Titular titular) {
        titularRepositorio.save(titular);
    }

    @Override
    public void eliminarTitular(Titular titular) {
        titularRepositorio.delete(titular);
    }

}
