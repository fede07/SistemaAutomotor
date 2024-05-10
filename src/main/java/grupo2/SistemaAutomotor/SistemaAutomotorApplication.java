package grupo2.SistemaAutomotor;

import grupo2.SistemaAutomotor.modelo.Titular;
import grupo2.SistemaAutomotor.presentacion.SistemaAutomotorFX;
import grupo2.SistemaAutomotor.servicio.titular.TitularServicio;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SistemaAutomotorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		Application.launch(SistemaAutomotorFX.class, args);
	}

	@Override
	public void run(String... args){
	}

}
