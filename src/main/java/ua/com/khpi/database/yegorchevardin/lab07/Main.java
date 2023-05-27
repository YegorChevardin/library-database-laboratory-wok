package ua.com.khpi.database.yegorchevardin.lab07;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.khpi.database.yegorchevardin.lab07.program.configuration.ProgramConfiguration;
import ua.com.khpi.database.yegorchevardin.lab07.program.startup.ProgramStartup;

/**
 * Point of entrance
 * @author yegorchevardin
 * @version 0.0.1
 */
public class Main {
    public static void main(String[] args) {
        boolean withDump = false;
        String customPropertiesPath = null;

        for (String arg : args) {
            if (arg.startsWith("--customProperties=")) {
                customPropertiesPath = arg.substring(arg.indexOf("=") + 1);
                break;
            }
            if (arg.startsWith("--refreshInsertions")) {
                withDump = true;
            }
        }

        if (customPropertiesPath != null) {
            System.setProperty("spring.config.name", "custom");
            System.setProperty("spring.config.location", "file:" + customPropertiesPath);
        } else {
            System.setProperty("spring.config.name", "default");
            System.setProperty("spring.config.location", "classpath:application.properties");
        }

        ApplicationContext context = new AnnotationConfigApplicationContext(ProgramConfiguration.class);
        ProgramStartup programStartup = context.getBean(ProgramStartup.class);
        programStartup.start();
    }
}