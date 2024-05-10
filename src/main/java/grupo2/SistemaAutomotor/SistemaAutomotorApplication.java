package grupo2.SistemaAutomotor;

import grupo2.SistemaAutomotor.modelo.Titular;
import grupo2.SistemaAutomotor.servicio.TitularServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaAutomotorApplication implements CommandLineRunner {

	@Autowired
	private TitularServicio titularServicio;

	private static final Logger logger = LoggerFactory.getLogger(SistemaAutomotorApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		SpringApplication.run(SistemaAutomotorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

		titularServicio.listarTitular();

		var nuevoTitular = new Titular();
		nuevoTitular.setNombre("Jorge");
		nuevoTitular.setApellido("Lopez");
		nuevoTitular.setEmail("lopez@gmail.com");
		nuevoTitular.setTelefono("55555555");
		nuevoTitular.setDireccion("Siempre Viva 123");
		nuevoTitular.setDni(39456987);
		titularServicio.insertarTitular(nuevoTitular);

		titularServicio.listarTitular();
	}

}
