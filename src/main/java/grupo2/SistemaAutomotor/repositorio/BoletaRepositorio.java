package grupo2.SistemaAutomotor.repositorio;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoletaRepositorio extends JpaRepository<Boleta, Integer> {
    List<Boleta> findBoletasByDominioAut(Automotor dominioAut);
}
