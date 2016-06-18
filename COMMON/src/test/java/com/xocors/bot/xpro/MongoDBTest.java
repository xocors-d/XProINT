package com.xocors.bot.xpro;

import com.xocors.bot.xpro.client.gui.db.XProAppRepository;
import com.xocors.bot.xpro.common.XProApplication;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * Created by xocor_zg5ru7n on 2016/6/15.
 */

@SpringBootApplication
@PropertySource(value = { "classpath:application.properties" })
public class MongoDBTest extends Application implements CommandLineRunner {

    private static ConfigurableApplicationContext context;
    @Autowired
    private XProAppRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(MongoDBTest.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        String[] beanDefinitionNames = context.getBeanDefinitionNames();
//        System.out.println(String.join("\n",beanDefinitionNames));
        List<XProApplication> apps = repository.findAll();
        System.out.println(apps.size());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}