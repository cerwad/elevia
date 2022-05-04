package fr.baralecorp.elevia.dao;

import fr.baralecorp.elevia.batch.InitH2DB;
import fr.baralecorp.elevia.service.BestScoresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevDatabaseSetup implements DatabaseSetup {
    Logger logger = LoggerFactory.getLogger(DevDatabaseSetup.class);

    @Autowired
    private InitH2DB initH2DB;

    @Autowired
    private BestScoresService bestScoresService;

    @Override
    public void setup() {

        logger.info("Initializing in Memory DB H2");
        initH2DB.addUsers();
        initH2DB.addResults();
        initH2DB.testSaveInCascade();
        bestScoresService.generateScoresFromResults();
    }
}