package grupo2.SistemaAutomotor.modelo;

import lombok.Data;

@Data
public class AutomotorDeuda {
    Automotor automotor;
    float deuda;

    public String getDominio() {
        return automotor.getDominio();
    }

    @SuppressWarnings("unused")
    public int getDniTitular(){
        return automotor.getDniTitular();
    }

    @SuppressWarnings("unused")
    public String getMarca(){
        return automotor.getMarca();
    }

    public String getModelo(){
        return automotor.getModelo();
    }

}
