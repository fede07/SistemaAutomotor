package grupo2.SistemaAutomotor.modelo;

import jakarta.persistence.*;
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
    @Id @Column(length = 6)
    private String dominio;
    @ManyToOne
    @JoinColumn(name = "Titular", foreignKey = @ForeignKey(name = "dni"))
    private Titular dniTitular;
    private String marca;
    private String modelo;
    private int anioFabricacion;
    @ManyToOne
    @JoinColumn(name = "Municipio", foreignKey = @ForeignKey(name = "id"))
    private Municipio idMunicipio;

    public int getDniTitular(){
        return dniTitular.getDni();
    }

    public String getIdMunicipio(){
        return idMunicipio.getNombre();
    }

}
