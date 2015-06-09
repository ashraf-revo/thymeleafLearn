package thymeleafLearn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import thymeleafLearn.domain.user;
import thymeleafLearn.service.OnlineSession;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ashraf on 6/9/15.
 */
@Service
public class OnlineSessionImpl implements OnlineSession {
    ConcurrentHashMap<String, Set<String>> concurrentHashMap=new ConcurrentHashMap<>();
    @Autowired
    SimpMessagingTemplate template;


    @Override
    public void AddOnlineUser(String name, String simpSessionId) {
        concurrentHashMap.put(name, new HashSet<>(Arrays.asList(simpSessionId)));

    }

    @Override
    public void RemoveOnlineUser(String name) {
        concurrentHashMap.remove(name);
    }

    @Override
    public Set<String> UserSessions(String name) {
        return concurrentHashMap.get(name);
    }

    @Override
    public void UpdateOne(String name, Set<String> s) {
        concurrentHashMap.put(name, s);
    }

    @Override
    public void PersonState(Boolean Sate, String name, String simpSessionId) {
        if (Sate) {
            AddOnlineUser(name, simpSessionId);
        } else {
            RemoveOnlineUser(name);
        }
        //update me allso in the database accordig to state-->boolean
//        Person person = ser.getPerson(name);
//        person.setIsonline(Sate);
//        ser.updatePerson(person);
    }
    public synchronized void Notfiy(List<user> personList, String group, String user) {
        for (user person : personList) {
            template.convertAndSendToUser(
                    person.getEmail(), "/topic/moveuser", "");
        }
    }
}
