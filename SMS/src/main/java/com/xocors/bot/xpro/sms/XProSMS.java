package com.xocors.bot.xpro.sms;


import com.xocors.bot.xpro.common.XProApplication;
import com.xocors.bot.xpro.sms.config.AppConfig;
import com.xocors.bot.xpro.sms.service.XProSMSServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.AbstractApplicationContext;

import javax.annotation.PreDestroy;

@SpringBootApplication

public class XProSMS {

    public XProSMS() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        new XProSMS().start();
    }

    protected void start() {

        //AbstractApplicationContext context = XProLoaderUtility.loadProfileContext("SMSServer_AMQP.xml", AppConfig.class, XProSMS.class, XProLoaderUtility.DEV);
        //AbstractApplicationContext context = XProLoaderUtility.loadProfileContext(AppConfig.class);

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        XProSMSServer smsServer;
        smsServer = (XProSMSServer) context.getBean("xProSMSServer");
        smsServer.start();
        XProApplication app = new XProApplication();
        app.setSmsContent("#ABC");
        smsServer.send(app);

//        AbstractApplicationContext context =
//                CafeDemoAppUtilities.loadProfileContext("SMSServer_AMQP.xml",
//                        CafeDemoAppAmqp.class, CafeDemoAppUtilities.DEV);
        context.registerShutdownHook();
    }



}
