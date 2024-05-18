package grupo2.SistemaAutomotor.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class Patente {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idPatente;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Automotor", foreignKey = @ForeignKey(name = "dominio"))
    private Automotor dominio;
    private String estado;
}
