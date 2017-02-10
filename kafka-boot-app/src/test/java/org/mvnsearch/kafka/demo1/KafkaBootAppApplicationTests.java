package org.mvnsearch.kafka.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaBootAppApplicationTests {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testSendMessage() {
        String key = "key-1";
        String payload = "good";
        kafkaTemplate.send("testTopic", key, payload);
    }

}
