package grupo2.SistemaAutomotor.repositorio;

import grupo2.SistemaAutomotor.modelo.Patente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatenteRepositorio extends JpaRepository<Patente, Integer> {
}
