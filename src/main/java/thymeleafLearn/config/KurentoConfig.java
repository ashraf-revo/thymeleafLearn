package thymeleafLearn.config;

import org.kurento.client.KurentoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ashraf on 6/8/15.
 */
@Configuration
public class KurentoConfig {

    @Bean
    public KurentoClient kurentoClient() {
        return KurentoClient.create(System.getProperty("kms.ws.uri", "ws://localhost:8888/kurento"));
    }
}
