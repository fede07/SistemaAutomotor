package grupo2.SistemaAutomotor.presentacion;

import grupo2.SistemaAutomotor.SistemaAutomotorApplication;
import grupo2.SistemaAutomotor.controlador.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

public class SistemaAutomotorFX extends Application {

    private ConfigurableApplicationContext contexto;

    public void init(){
        this.contexto = new SpringApplicationBuilder(SistemaAutomotorApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Modelo.getInstance().getViewFactory().mostrarVerFactPag();
        FXMLLoader mainLoader = new FXMLLoader(SistemaAutomotorApplication.class.getResource("/templates/mainScene.fxml"));
        mainLoader.setControllerFactory(contexto::getBean);
        Scene mainScene = new Scene(mainLoader.load());
        mainScene.getHeight();

        FXMLLoader automotorLoader = new FXMLLoader(SistemaAutomotorApplication.class.getResource("/templates/gestionarScene.fxml"));
        automotorLoader.setControllerFactory(contexto::getBean);
        Scene automotorScene = new Scene(automotorLoader.load());

        FXMLLoader facPagLoader  = new FXMLLoader(SistemaAutomotorApplication.class.getResource("/templates/verFactPagScene.fxml"));
        facPagLoader.setControllerFactory(contexto::getBean);
        Scene facPagScene = new Scene(facPagLoader.load());

        FXMLLoader deudaLoader = new FXMLLoader(SistemaAutomotorApplication.class.getResource("/templates/deudaScene.fxml"));
        deudaLoader.setControllerFactory(contexto::getBean);
        Scene deudaScene = new Scene(deudaLoader.load());

        FXMLLoader recaudacionLoader = new FXMLLoader(SistemaAutomotorApplication.class.getResource("/templates/recaudacionScene.fxml"));
        recaudacionLoader.setControllerFactory(contexto::getBean);
        Scene recaudacionScene = new Scene(recaudacionLoader.load());

        FXMLLoader facImpagloader = new FXMLLoader(SistemaAutomotorApplication.class.getResource("/templates/verFactImpagScene.fxml"));
        facImpagloader.setControllerFactory(contexto::getBean);
        Scene facImpagScene = new Scene(facImpagloader.load());

        //Vinculo las escenas con Main
//        AutomotorControlador automotorControlador = automotorLoader.getController();
//        automotorControlador.setMainScene(mainScene);


        //Vinculo Main con las escenas
        MainControlador mainControlador = mainLoader.getController();
        mainControlador.setAutomotorScene(automotorScene);
        mainControlador.setFacPagScene(facPagScene);
        mainControlador.setDeudasScene(deudaScene);
        mainControlador.setRecaudacionScene(recaudacionScene);
        mainControlador.setFacturasImpagasScene(facImpagScene);

        //Inicio la escena principal
        stage.setScene(facPagScene);
        stage.setTitle("Sistema Automotor");
        stage.setMinHeight(800);
        stage.setMinWidth(1000);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/unlam.png"))));
        stage.show();
    }

    @Override
    public void stop(){
        contexto.close();
        Platform.exit();
    }

}


