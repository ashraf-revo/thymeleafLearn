package thymeleafLearn.service;

import thymeleafLearn.domain.user;

import java.util.List;
import java.util.Set;

/**
 * Created by ashraf on 6/9/15.
 */
public interface OnlineSession {
    public void AddOnlineUser(String name, String simpSessionId);

    public void RemoveOnlineUser(String name);

    public Set<String> UserSessions(String name);

    public void UpdateOne(String name, Set<String> s);

    public void PersonState(Boolean Sate, String name, String simpSessionId);

    public void Notfiy(List<user> personList, String group, String user);
}
