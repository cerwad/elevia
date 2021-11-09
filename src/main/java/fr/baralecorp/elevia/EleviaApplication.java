package fr.baralecorp.elevia;

import fr.baralecorp.elevia.batch.InitH2DB;
import fr.baralecorp.elevia.service.BestScoresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EleviaApplication {

    Logger logger = LoggerFactory.getLogger(EleviaApplication.class);

    @Value("${env}")
    private String environment;

    @Autowired
    private BestScoresService bestScoresService;

    @Autowired
    private InitH2DB initH2DB;

    public static void main(String[] args) {
        SpringApplication.run(EleviaApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initH2Db() {
        logger.info("Application is starting up in environment " + environment);
        if ("dev".equals(environment)) {
            logger.info("Initializing in Memory DB H2");
            initH2DB.addUsers();
            initH2DB.addResults();
            bestScoresService.generateScoresFromResults();
        }
    }
}
