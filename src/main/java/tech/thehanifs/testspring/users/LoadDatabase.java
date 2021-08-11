package tech.thehanifs.testspring.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.thehanifs.testspring.Config;

@Configuration
public class LoadDatabase {
    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        User userAdmin = repository.findByUsername(Config.adminUsername);
        if (userAdmin == null) return args -> logger.info("Preloading " + repository.save(new User(Config.adminName, 1, Config.adminUsername, Config.adminPassword, Config.adminEmail)));
        else return args -> {
            logger.info("Admin user has exist! Credentials already in config");
        };
    }
}
