package grupo2.SistemaAutomotor.servicio;

import java.util.List;
import grupo2.SistemaAutomotor.modelo.Titular;

public interface ITitularServicio {
    public List<Titular> listarTitular();
    public Titular buscarTitular(Integer dni);
    public void insertarTitular(Titular titular);
    public void modificarTitular(Titular titular);
    public void eliminarTitular(Titular titular);

}
