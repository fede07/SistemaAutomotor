package grupo2.SistemaAutomotor.servicio.patente;

import grupo2.SistemaAutomotor.modelo.Patente;
import java.util.List;

public interface IPatenteServicio {
    public List<Patente> listarPatentes();
    public Patente buscarPatente(int id);
    public void guardarPatente(Patente patente);
    public void eliminarPatente(int id);
}
