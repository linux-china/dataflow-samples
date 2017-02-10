package org.mvnsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class KafkaBootAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaBootAppApplication.class, args);
    }

    @KafkaListener(topics = "testTopic")
    public void processMessage(String content) {
        System.out.println("from kafka: " + content);
    }
}
