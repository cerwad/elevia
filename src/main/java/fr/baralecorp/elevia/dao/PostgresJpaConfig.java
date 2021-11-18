package fr.baralecorp.elevia.dao;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;
import java.util.Properties;

@Profile("staging")
@Configuration
public class PostgresJpaConfig {

    @Bean
    public DataSource getDataSource() {
        URI dbUri = URI.create(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        //dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    @Bean
    public Properties additionalProps() {
        Properties jpaProps = new Properties();

        jpaProps.put("spring.jpa.hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProps.put("spring.jpa.hibernate.ddl-auto", "update");
        //jpaProps.put(showSql, true);
        //jpaProps.put(formatSql, true);
        jpaProps.put("javax.persistence.create-database-schemas", true);

        return jpaProps;
    }
}