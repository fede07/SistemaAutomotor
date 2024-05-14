package grupo2.SistemaAutomotor.modelo;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Getter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Municipio {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;

    public String toString(){
        return nombre;
    }

}
