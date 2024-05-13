package grupo2.SistemaAutomotor.modelo;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Municipio {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Getter
    private String nombre;

}
