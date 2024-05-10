package grupo2.SistemaAutomotor.servicio.patente;

import grupo2.SistemaAutomotor.modelo.Patente;
import grupo2.SistemaAutomotor.repositorio.PatenteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatenteServicio implements IPatenteServicio{

    @Autowired
    private PatenteRepositorio patenteRepositorio;


    @Override
    public List<Patente> listarPatentes() {
        return patenteRepositorio.findAll();
    }

    @Override
    public Patente buscarPatente(int id) {
        return patenteRepositorio.findById(id).orElse(null);
    }

    @Override
    public void guardarPatente(Patente patente) {
        patenteRepositorio.save(patente);
    }

    @Override
    public void eliminarPatente(int id) {
        patenteRepositorio.deleteById(id);
    }
}
