package grupo2.SistemaAutomotor.servicio.automotor;

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
    public Automotor buscarAutomotor(String dominio) {
        return automotorRepositorio.findById(dominio).orElse(null);
    }

    @Override
    public void guardarAutomotor(Automotor automotor) {
        automotorRepositorio.save(automotor);
    }

    @Override
    public void eliminarAutomotor(String dominio) {
        automotorRepositorio.deleteById(dominio);
    }

    @Override
    public List<Automotor> listarAutomoresConDeuda() {
        return automotorRepositorio.findAllByDeuda();
    }

}
