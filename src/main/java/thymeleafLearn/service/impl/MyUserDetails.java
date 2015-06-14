package thymeleafLearn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thymeleafLearn.domain.MySocialUser;
import thymeleafLearn.domain.user;
import thymeleafLearn.repositery.userRepository;

/**
 * Created by ashraf on 6/7/15.
 */
@Service
@Transactional
public class MyUserDetails implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    userRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user byEmail = repository.findByEmail(username);
        return new User(byEmail.getEmail(), byEmail.getPassword(), byEmail.getAuthorities());
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        return new MySocialUser(repository.findByEmail(userId));
    }
}
