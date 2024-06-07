package grupo2.SistemaAutomotor.clase;

import grupo2.SistemaAutomotor.modelo.Boleta;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
public class Escritor {

    private static final String CSV_HEADER = "ID;Cuota;Estado;Vencimiento;Fecha de Pago;Importe;Dominio\n";
    private static final String CSV_SEPARATOR = ";";
    private static final String PATH= "Exports/facturas.csv";

    public void exportar (List<Boleta> boletas) {
        String csvContenido = generarCSV(boletas);
        try {
            FileWriter file = new FileWriter(PATH);
            PrintWriter writer = new PrintWriter(file);
            writer.print(csvContenido);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String generarCSV(List<Boleta> boletas){
        StringBuilder csvContenido = new StringBuilder();
        csvContenido.append(CSV_HEADER);
        for (Boleta boleta : boletas) {
            csvContenido.append(boleta.getIdBoleta()).append(CSV_SEPARATOR)
                    .append(boleta.getCuota()).append(CSV_SEPARATOR)
                    .append(boleta.getEstadoToString()).append(CSV_SEPARATOR)
                    .append(boleta.getFven()).append(CSV_SEPARATOR)
                    .append(boleta.getFpag()).append(CSV_SEPARATOR)
                    .append(boleta.getImporte()).append(CSV_SEPARATOR)
                    .append(boleta.getDominio()).append("\n");
        }
        System.out.println(csvContenido);
        return csvContenido.toString();
    }

}
