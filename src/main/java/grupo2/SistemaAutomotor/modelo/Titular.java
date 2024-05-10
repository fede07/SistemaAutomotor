package grupo2.SistemaAutomotor.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Titular {
    @Id
    private int dni;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String dominioVehiculo;
}
