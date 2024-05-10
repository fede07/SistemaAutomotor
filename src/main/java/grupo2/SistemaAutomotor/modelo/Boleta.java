package grupo2.SistemaAutomotor.modelo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Boleta {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "Patente", foreignKey = @ForeignKey(name = "IdPatente"))
    private Patente idPatente;
    private Integer IdBoleta;
    private String Estado;
    private BigDecimal Monto;
    private Date FechaVencimiento;
    private Date FechaPago;

}
