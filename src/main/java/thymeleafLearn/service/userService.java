package thymeleafLearn.service;

import thymeleafLearn.domain.user;

import java.util.List;

/**
 * Created by ashraf on 6/7/15.
 */
public interface userService {
    public user findByEmail(String email);

    public List<user> findall();

    public user save(user user);
}