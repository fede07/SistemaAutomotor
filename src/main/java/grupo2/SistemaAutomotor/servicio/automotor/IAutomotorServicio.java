package grupo2.SistemaAutomotor.servicio.automotor;

import grupo2.SistemaAutomotor.modelo.Automotor;
import java.util.List;

public interface IAutomotorServicio {
    public List<Automotor> listarAutomotor();
    public Automotor buscarAutomotor(String dominio);
    public void guardarAutomotor(Automotor automotor);
    public void eliminarAutomotor(String dominio);
}
