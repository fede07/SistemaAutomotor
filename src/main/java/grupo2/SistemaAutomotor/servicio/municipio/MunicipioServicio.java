package grupo2.SistemaAutomotor.servicio.municipio;

import grupo2.SistemaAutomotor.modelo.Municipio;
import grupo2.SistemaAutomotor.repositorio.MunicipioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipioServicio implements IMunicipioServicio{

    @Autowired
    private MunicipioRepositorio municipioRepositorio;

    @Override
    public List<Municipio> listarMunicipios() {
        return municipioRepositorio.findAll();
    }

    @Override
    public Municipio buscarMunicipio(int id) {
        return municipioRepositorio.findById(id).orElse(null);
    }

    @Override
    public void guardarMunicipio(Municipio municipio) {
        municipioRepositorio.save(municipio);
    }

    @Override
    public void eliminarMunicipio(int id) {
            municipioRepositorio.deleteById(id);
    }
}
