package grupo2.SistemaAutomotor.servicio;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.repositorio.AutomotorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutomotorServicio implements IAutomotorServicio {

    @Autowired
    private AutomotorRepositorio automotorRepositorio;

    @Override
    public List<Automotor> listarAutomotor() {
        return automotorRepositorio.findAll();
    }

    @Override
    public Automotor buscarAutomotor(int id) {
        return automotorRepositorio.findById(id).orElse(null);
    }

    @Override
    public void insertarAutomotor(Automotor automotor) {
        automotorRepositorio.save(automotor);
    }

    @Override
    public void modificarAutomotor(Automotor automotor) {
        automotorRepositorio.save(automotor);
    }

    @Override
    public void eliminarAutomotor(int id) {
        automotorRepositorio.deleteById(id);
    }
}
