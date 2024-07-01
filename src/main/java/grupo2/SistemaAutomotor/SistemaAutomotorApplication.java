package grupo2.SistemaAutomotor;

import grupo2.SistemaAutomotor.presentacion.SistemaAutomotorFX;
import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class 		SistemaAutomotorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		Application.launch(SistemaAutomotorFX.class, args);
	}

	@Override
	public void run(String... args){
	}

}
