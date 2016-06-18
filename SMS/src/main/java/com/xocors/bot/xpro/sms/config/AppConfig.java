package com.xocors.bot.xpro.sms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;



@Configuration
@ComponentScan("com.xocors.bot.xpro.sms")
@ImportResource({"classpath:SMSServer_AMQP.xml"})
@PropertySource(value = { "classpath:application.properties" })
public class AppConfig {
//	@Autowired
//	Environment environment;
}
