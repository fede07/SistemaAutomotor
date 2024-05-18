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
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Boleta {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idBoleta;
    @ManyToOne
    @JoinColumn (name = "Automotor", referencedColumnName = "dominio", foreignKey = @ForeignKey(name = "dominio"))
    private Automotor dominioAut;
    private String estado;
    private BigDecimal importe;
    private Date fechaVencimiento;
    private Date fechaPago;
    private Integer cuota;

    @SuppressWarnings("unused")
    public int getAnioBoleta(){
        Calendar cal = new GregorianCalendar();
        cal.setTime(fechaVencimiento);
        return cal.get(Calendar.YEAR);
    }

    @SuppressWarnings("unused")
    public String getFven(){
        Calendar cal = new GregorianCalendar();
        cal.setTime(fechaVencimiento);
        return cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
    }

}
