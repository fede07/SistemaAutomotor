package grupo2.SistemaAutomotor.presentacion;

import grupo2.SistemaAutomotor.SistemaAutomotorApplication;
import grupo2.SistemaAutomotor.controlador.AutomotorControlador;
import grupo2.SistemaAutomotor.controlador.BoletaControlador;
import grupo2.SistemaAutomotor.controlador.MainControlador;
import grupo2.SistemaAutomotor.controlador.TitularControlador;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SistemaAutomotorFX extends Application {

    private ConfigurableApplicationContext contexto;

    public void init(){
        this.contexto = new SpringApplicationBuilder(SistemaAutomotorApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Creo las escenas
        FXMLLoader mainLoader = new FXMLLoader(SistemaAutomotorApplication.class.getResource("/templates/mainScene.fxml"));
        mainLoader.setControllerFactory(contexto::getBean);
        Scene mainScene = new Scene(mainLoader.load());

        FXMLLoader automotorLoader = new FXMLLoader(SistemaAutomotorApplication.class.getResource("/templates/automotorScene.fxml"));
        automotorLoader.setControllerFactory(contexto::getBean);
        Scene automotorScene = new Scene(automotorLoader.load());

        FXMLLoader titularLoader = new FXMLLoader(SistemaAutomotorApplication.class.getResource("/templates/titularScene.fxml"));
        titularLoader.setControllerFactory(contexto::getBean);
        Scene titularScene = new Scene(titularLoader.load());

        FXMLLoader boletaLoader = new FXMLLoader(SistemaAutomotorApplication.class.getResource("/templates/boletaScene.fxml"));
        boletaLoader.setControllerFactory(contexto::getBean);
        Scene boletaScene = new Scene(boletaLoader.load());

        //Vinculo las escenas con Main
        AutomotorControlador automotorControlador = automotorLoader.getController();
        automotorControlador.setMainScene(mainScene);

        TitularControlador titularControlador = titularLoader.getController();
        titularControlador.setMainScene(mainScene);

        BoletaControlador boletaControlador = boletaLoader.getController();
        boletaControlador.setMainScene(mainScene);

        //Vinculo Main con las escenas
        MainControlador mainControlador = mainLoader.getController();
        mainControlador.setAutomotorScene(automotorScene);
        mainControlador.setTitularScene(titularScene);
        mainControlador.setBoletaScene(boletaScene);

        //Inicio la escena principal
        stage.setScene(mainScene);
        stage.show();
    }

    @Override
    public void stop(){
        contexto.close();
        Platform.exit();
    }
}
