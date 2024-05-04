package co.com.tkl.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(@Value("${app.db.url}") String url) {
        return args -> System.out.println("URL DATABASE = " + url);
    }
}
