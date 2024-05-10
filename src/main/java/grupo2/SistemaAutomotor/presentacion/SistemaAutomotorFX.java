package grupo2.SistemaAutomotor.presentacion;

import grupo2.SistemaAutomotor.SistemaAutomotorApplication;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SistemaAutomotorFX extends Application {

    private ConfigurableApplicationContext contexto;

    public void init(){
        this.contexto = new SpringApplicationBuilder(SistemaAutomotorApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
