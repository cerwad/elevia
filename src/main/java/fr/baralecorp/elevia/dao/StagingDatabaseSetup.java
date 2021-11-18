package fr.baralecorp.elevia.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"staging", "preprod"})
public class StagingDatabaseSetup implements DatabaseSetup {

    Logger logger = LoggerFactory.getLogger(StagingDatabaseSetup.class);

    @Override
    public void setup() {
        logger.info("Setting up datasource for STAGING environment. ");
    }
}