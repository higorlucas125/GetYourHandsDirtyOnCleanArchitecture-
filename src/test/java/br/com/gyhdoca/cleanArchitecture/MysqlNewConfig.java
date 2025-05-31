package br.com.gyhdoca.cleanArchitecture;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import javax.sql.DataSource;

@TestConfiguration
@Testcontainers
public abstract class MysqlNewConfig {

    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("project_clean")
            .withUsername("root")
            .withPassword("root")
            .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(60)));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () ->
                mySQLContainer.getJdbcUrl() + "?createDatabaseIfNotExist=true");
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
        registry.add("spring.flyway.locations", () -> "classpath:db/migration");
        registry.add("spring.flyway.baseline-on-migrate", () -> "true");
    }

    static {
        mySQLContainer.start();
        System.out.println("---------- " + mySQLContainer.getDriverClassName());
        System.out.println("---------- EEEE " + mySQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.driver-class-name", mySQLContainer.getDriverClassName());
        System.setProperty("spring.datasource.url", mySQLContainer.getJdbcUrl() + "?createDatabaseIfNotExist=true");
    }
}
