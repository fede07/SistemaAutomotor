package grupo2.SistemaAutomotor.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor

public class Municipio {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String Descripcion;
    private String Localidad;
}
