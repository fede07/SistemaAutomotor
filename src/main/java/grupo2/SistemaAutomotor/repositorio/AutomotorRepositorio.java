package grupo2.SistemaAutomotor.repositorio;

import grupo2.SistemaAutomotor.modelo.Automotor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutomotorRepositorio extends JpaRepository<Automotor, String> {

    @Query("SELECT a from Automotor a where a.dominio in (select b.dominio.dominio from Boleta b where b.estado = false)")
    List<Automotor> findAllByDeuda();
}
