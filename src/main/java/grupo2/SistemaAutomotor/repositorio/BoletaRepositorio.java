package grupo2.SistemaAutomotor.repositorio;

import grupo2.SistemaAutomotor.modelo.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoletaRepositorio extends JpaRepository<Boleta, Integer> {
}