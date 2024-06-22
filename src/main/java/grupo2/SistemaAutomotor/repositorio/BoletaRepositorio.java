package grupo2.SistemaAutomotor.repositorio;

import grupo2.SistemaAutomotor.modelo.Automotor;
import grupo2.SistemaAutomotor.modelo.Boleta;
import grupo2.SistemaAutomotor.modelo.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface BoletaRepositorio extends JpaRepository<Boleta, Integer> {
    List<Boleta> findBoletasByDominio(Automotor dominioAut);
    List<Boleta> findBoletasByDominioAndEstado(Automotor dominioAut, Boolean estado);
    // "select m.nombre, sum(b.importe) as recaudacion FROM Boleta b join Automotor  a on b.dominio = a.dominio join Municipio m on a.idMunicipio = m group by m.nombre"
    // Recaudacion por municipio
    @Query("SELECT SUM(b.importe) FROM Boleta b WHERE b.dominio.idMunicipio = ?1 and b.estado = true")
    BigDecimal sumAllByMunicipio(Municipio municipio);

    @Query("select sum(b.importe) from Boleta b where b.dominio.dominio = ?1 and b.estado = false")
    BigDecimal SumAllByDominio(String dominio);

    @Query("SELECT b.dominio.idMunicipio.id from Boleta b GROUP BY b.dominio.idMunicipio.id")
    List<Boleta> findAllByMunicipio(Municipio municipio);

    List<Boleta> findBoletasByDominioAndEstadoAndCuotaAfter(Automotor dominioAut, Boolean estado, int cuotaAfter);
    List<Boleta> findBoletasByDominioAndEstadoAndCuotaBetween(Automotor dominioAut, Boolean estado,int cuotaBefore, int cuotaAfter);
    void deleteAllBydominio(Automotor dominioAut);
}
