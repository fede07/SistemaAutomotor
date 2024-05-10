package grupo2.SistemaAutomotor.servicio.titular;

import java.util.List;
import grupo2.SistemaAutomotor.modelo.Titular;

public interface ITitularServicio {
    public List<Titular> listarTitulares();
    public Titular buscarTitular(Integer dni);
    public void guardarTitular(Titular titular);
    public void eliminarTitular(Titular titular);

}
