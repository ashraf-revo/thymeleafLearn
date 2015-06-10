package thymeleafLearn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thymeleafLearn.domain.SecurityUser;
import thymeleafLearn.repositery.userRepository;

/**
 * Created by ashraf on 6/7/15.
 */
@Service
@Transactional
public class UserDetiles implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    userRepository repositery;

    @Override
    public SocialUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(repositery.findByEmail(username));
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        return loadUserByUsername(userId);
    }
}
