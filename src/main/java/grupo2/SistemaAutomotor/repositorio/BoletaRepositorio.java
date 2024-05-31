package grupo2.SistemaAutomotor.repositorio;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.modelo.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BoletaRepositorio extends JpaRepository<Boleta, Integer> {
    List<Boleta> findBoletasByDominio(Automotor dominioAut);
    List<Boleta> findBoletasByDominioAndEstado(Automotor dominioAut, Boolean estado);
    @Query("SELECT b.dominio.idMunicipio.id, sum(b.importe) FROM Boleta b GROUP BY b.dominio.idMunicipio.id")
    float sumAllByMunicipio(Municipio municipio);

    @Query("SELECT b.dominio.idMunicipio.id from Boleta b GROUP BY b.dominio.idMunicipio.id")
    List<Boleta> findAllByMunicipio(Municipio municipio);

    List<Boleta> findBoletasByDominioAndEstadoAndCuotaAfter(Automotor dominioAut, Boolean estado, int cuotaAfter);

}
