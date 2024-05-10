package grupo2.SistemaAutomotor.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Boleta {
    @Id
    private Integer IdBoleta;
    private String Estado;
    private Float Monto;
    private Date FechaVencimiento;
    private Date FechaPago;

}
