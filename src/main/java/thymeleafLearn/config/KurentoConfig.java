package thymeleafLearn.config;

import org.kurento.client.KurentoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ashraf on 6/8/15.
 */
@Configuration
public class KurentoConfig {
    final static String DEFAULT_KMS_WS_URI = "ws://localhost:8888/kurento";

    @Bean
    public KurentoClient kurentoClient() {
        return KurentoClient.create(System.getProperty("kms.ws.uri",
                DEFAULT_KMS_WS_URI));
    }
}
