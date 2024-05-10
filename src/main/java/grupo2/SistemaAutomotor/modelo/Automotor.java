package grupo2.SistemaAutomotor.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Automotor {
    @Id
    private int dominio;
    @ManyToOne
    @JoinColumn(name = "dniTitular", foreignKey = @ForeignKey(name = "FK_Automotor_Titular"))
    private Titular dniTitular;
    private String marca;
    private String modelo;
    private int municipio;


}
