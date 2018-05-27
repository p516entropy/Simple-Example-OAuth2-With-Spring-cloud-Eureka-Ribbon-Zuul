package com.lungesoft.architecture.core;

import com.lungesoft.architecture.core.jpa.entity.Project;
import com.lungesoft.architecture.core.jpa.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class RestApplication {

    private static final Logger log = LoggerFactory.getLogger(RestApplication.class);

    public static void main(final String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ProjectRepository repository) {
        return (args) -> {
            repository.save(new Project("Gooble"));
            repository.save(new Project("Facelook"));
            repository.save(new Project("GlobalX"));
            repository.save(new Project("GidHub"));

            // fetch all Users
            log.info("Project found with findAll():");
            for (Project project : repository.findAll()) {
                log.info(project.getTitle());
            }
            log.info("");

        };
    }

}
