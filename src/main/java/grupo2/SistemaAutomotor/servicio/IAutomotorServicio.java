package grupo2.SistemaAutomotor.servicio;

import grupo2.SistemaAutomotor.modelo.Automotor;
import java.util.List;

public interface IAutomotorServicio {
    public List<Automotor> listarAutomotor();
    public Automotor buscarAutomotor(int id);
    public void insertarAutomotor(Automotor automotor);
    public void modificarAutomotor(Automotor automotor);
    public void eliminarAutomotor(int id);
}
