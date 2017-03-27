package org.mvnsearch.boot.data.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.dataflow.server.EnableDataFlowServer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDataFlowServer
@EnableRedisHttpSession
public class CloudDataFlowServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudDataFlowServerApplication.class, args);
    }
}
