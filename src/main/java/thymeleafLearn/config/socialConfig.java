package thymeleafLearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;

/**
 * Created by ashraf on 5/4/15.
 */
@Configuration
public class socialConfig extends SocialConfigurerAdapter {
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public ProviderSignInUtils signInUtils(ConnectionFactoryLocator connectionFactoryLocator,UsersConnectionRepository connectionRepository) {
        return new ProviderSignInUtils(connectionFactoryLocator,connectionRepository );
    }
}
