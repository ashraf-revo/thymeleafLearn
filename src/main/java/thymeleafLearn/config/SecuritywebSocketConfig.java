package thymeleafLearn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

/**
 * Created by ashraf on 6/14/15.
 */
@Configuration
public class SecuritywebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
}
