package grupo2.SistemaAutomotor.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Titular {
    @Id
    private int dni;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;

    @Override
    public String toString() {
        return String.valueOf(this.dni);
    }
}

