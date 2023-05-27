package ua.com.khpi.database.yegorchevardin.lab07.program.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.sql.DataSource;
import java.util.Scanner;

/**
 * Class for configuring spring and program start up
 * @author yegorchevardin
 * @version 0.0.1
 */
@Configuration
@PropertySource(value = "${spring.config.location}", ignoreResourceNotFound = true)
@ComponentScan("ua.com.khpi.database.yegorchevardin.lab07")
public class ProgramConfiguration {
    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Bean
    public Scanner consoleScanner() {
        return new Scanner(System.in);
    }

    @Bean
    public DataSource dataSource(HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    public HikariConfig hikariConfig(@Value("${db.url}") String url,
                                     @Value("${db.user}") String username,
                                     @Value("${db.password}") String password,
                                     @Value("${db.driver}") String driverName,
                                     @Value("${db.connectionNumber}") int connectionNumber) {
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(url);
        config.setDriverClassName(driverName);
        config.setMaximumPoolSize(connectionNumber);
        return config;
    }

    /**
     * Configuring Hibernate validation bean
     * @return MethodValidationProcessor object
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }
}
