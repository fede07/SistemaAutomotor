package grupo2.SistemaAutomotor.servicio.automotor;

import grupo2.SistemaAutomotor.modelo.Automotor;
import java.util.List;

public interface IAutomotorServicio {
    List<Automotor> listarAutomotor();
    Automotor buscarAutomotor(String dominio);
    void guardarAutomotor(Automotor automotor);
    void eliminarAutomotor(String dominio);
    List<Automotor> listarAutomoresConDeuda();
}
