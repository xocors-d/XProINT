package com.xocors.bot.xpro.client.gui;


import com.xocors.bot.xpro.client.gui.component.MainPaneController;
import com.xocors.bot.xpro.client.gui.db.XProAppRepository;
import com.xocors.bot.xpro.common.XProApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import java.io.InputStream;

/**
 * Created by yoyow on 2016/5/31.
 */
@SpringBootApplication
//@ComponentScan("com.xocors.bot.xpro")
@ImportResource({"classpath:CLIENT_AMQP.xml"})
@PropertySource(value = { "classpath:application.properties" })
public class XProClient extends Application {


//    private Group root = new Group();
    private AnchorPane page;
    private static ConfigurableApplicationContext context;
    private MainPaneController mainPageController;
//    private final double MINIMUM_WINDOW_WIDTH = 800.0;
//    private final double MINIMUM_WINDOW_HEIGHT = 500.0;

    public Parent createContent() {
        gotoMainPage();
        return page;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //context = new AnnotationConfigApplicationContext(AppConfig.class);

        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();

    }

    public static void main(String[] args) {
        context = SpringApplication.run(XProClient.class);
        launch(args);
    }

    private void gotoMainPage() {
        try {
            mainPageController = (MainPaneController) replaceSceneContent("/gui_layouts/MainPane.fxml");
            mainPageController.setContext(context);
            mainPageController.refresh();
       } catch (Exception ex) {
            //Logger.getLogger(this.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = XProClient.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(XProClient.class.getResource(fxml));

        try {
            page = loader.load(in);
        } finally {
            in.close();
        }


//        root.getChildren().removeAll();
//        root.getChildren().addAll(page);
        return (Initializable) loader.getController();
    }

    public void updateApplication(XProApplication application){
        mainPageController.updateApplication(application);
    }
}