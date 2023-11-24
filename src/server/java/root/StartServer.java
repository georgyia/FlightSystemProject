package server.java.root;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;
@SpringBootApplication
@ComponentScan(basePackages = {"root.*"})
@EntityScan(basePackages = {"root.*"})
@EnableJpaRepositories(basePackages = {"root.*"})
public class StartServer {

    public static void main(String[] args) {
        SpringApplication.run(StartServer.class, args);
    }

}