package grupo2.SistemaAutomotor.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dominio")
    private Automotor dominio;
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
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return day + "/" + month + "/" + year;
    }

}
