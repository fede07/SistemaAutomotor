package grupo2.SistemaAutomotor.clase;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class Recaudacion {
    private String nombreMunicipio;
    private BigDecimal total;

    public void setTotal(BigDecimal total) {
        this.total = total.setScale(2, RoundingMode.HALF_UP);
    }
}
