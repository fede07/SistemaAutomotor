package grupo2.SistemaAutomotor.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Titular {
    @Getter
    @Id
    private int dni;
    private String nombre;
    private String apellido;

}

