package fr.baralecorp.elevia;

import fr.baralecorp.elevia.dao.DatabaseSetup;
import fr.baralecorp.elevia.service.data.AppData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;

@SpringBootApplication
public class EleviaApplication {

    Logger logger = LoggerFactory.getLogger(EleviaApplication.class);

    @Value("${env}")
    private String environment;

    @Autowired
    private DatabaseSetup datasourceConfig;

    @Autowired
    private ConfigurableEnvironment env;

    @Autowired
    private AppData appData;

    public static void main(String[] args) {
        SpringApplication.run(EleviaApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        StringBuilder builder = new StringBuilder();
        builder.append("Application is starting up with profiles ").append(Arrays.toString(env.getActiveProfiles()));
        logger.info(builder.toString());
        builder.setLength(0);
        builder.append("Environment : ");
        builder.append(environment);
        logger.info(builder.toString());
        datasourceConfig.setup();
        appData.setup(environment);
    }

}
