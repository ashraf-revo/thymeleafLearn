package thymeleafLearn.config;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.kurento.client.KurentoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import thymeleafLearn.domain.CallMediaPipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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

    @Bean
    public ConcurrentHashSet<CallMediaPipeline> mediaPipelines() {
        return new ConcurrentHashSet<CallMediaPipeline>();
    }
}
