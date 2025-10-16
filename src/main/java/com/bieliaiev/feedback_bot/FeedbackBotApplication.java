package com.bieliaiev.feedback_bot;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class FeedbackBotApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        SpringApplication app = new SpringApplication(FeedbackBotApplication.class);

        Map<String, Object> props = dotenv.entries().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> (Object) e.getValue()
                ));

        app.setDefaultProperties(props);
        app.run(args);
    }
}
