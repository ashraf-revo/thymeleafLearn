package thymeleafLearn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Created by ashraf on 6/3/15.
 */
@Configuration
@EnableWebMvcSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetails;
    private String key = "revo_key_remember";


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/signup/**", "/auth/**").permitAll().
                and().authorizeRequests().antMatchers("/**").authenticated().
                and().formLogin().and().rememberMe().key(key).rememberMeServices(rememberMeServices())
                .and().apply(new SpringSocialConfigurer());    }

    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices services = new TokenBasedRememberMeServices(key, userDetails);
        services.setAlwaysRemember(true);
        return services;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }
}
