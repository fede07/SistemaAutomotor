package grupo2.SistemaAutomotor.servicio.municipio;

import grupo2.SistemaAutomotor.modelo.Municipio;

import java.util.List;

public interface IMunicipioServicio {
    public List<Municipio> listarMunicipios();
    public Municipio buscarMunicipio(int id);
    public void guardarMunicipio(Municipio municipio);
    public void eliminarMunicipio(int id);
}
