package grupo2.SistemaAutomotor.modelo;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Boleta {
    @Id
    @GeneratedValue
    private Integer IdBoleta;
    private String Estado;
    private BigDecimal Monto;
    private Date FechaVencimiento;
    private Date FechaPago;

}
