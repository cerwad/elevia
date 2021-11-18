package fr.baralecorp.elevia.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class ProductionDatabaseSetup implements DatabaseSetup {

    Logger logger = LoggerFactory.getLogger(ProductionDatabaseSetup.class);

    @Override
    public void setup() {

        logger.info("Setting up datasource for PRODUCTION environment. ");
    }
}