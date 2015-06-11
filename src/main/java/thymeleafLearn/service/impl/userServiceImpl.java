package thymeleafLearn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thymeleafLearn.domain.user;
import thymeleafLearn.repositery.userRepository;
import thymeleafLearn.service.userService;

import java.util.List;

/**
 * Created by ashraf on 6/7/15.
 */
@Service
@Transactional
public class userServiceImpl implements userService {
    @Autowired
    userRepository Repository;

    @Override
    public user findByEmail(String email) {
        return Repository.findByEmail(email);
    }

    @Override
    public List<user> findAll() {
        return Repository.findall();

    }

    @Override
    public user save(user user) {
        return Repository.save(user);
    }

}
