package ru.digitalsoft.document;


import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EntityScan
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class MainAppplication {

    public static void main (String... args) {

        SpringApplication.run(MainAppplication.class, args);

    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/cumulative.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

}
