package thymeleafLearn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import thymeleafLearn.domain.user;
import thymeleafLearn.messages.ConversationMessage;
import thymeleafLearn.messages.MessageType;
import thymeleafLearn.service.OnlineSession;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by ashraf on 6/9/15.
 */
@Service
public class OnlineSessionImpl implements OnlineSession {
    ConcurrentHashMap<String, Set<String>> OnlineSessionData = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Set<String>> HaveAccessToMediaPipeline = new ConcurrentHashMap<>();
    @Autowired
    SimpMessagingTemplate template;

    @Override
    public void CreateMediaPipeline(String MediaPipeline) {
        HaveAccessToMediaPipeline.put(MediaPipeline, new HashSet<>());

    }

    @Override
    public void CreateMediaPipeline(String MediaPipeline, Set<String> HaveAccess) {
        HaveAccessToMediaPipeline.put(MediaPipeline, HaveAccess);
    }

    @Override
    public void CreateMediaPipeline(String MediaPipeline, String HaveAccess) {
        HaveAccessToMediaPipeline.put(MediaPipeline, new HashSet<>(Arrays.asList(HaveAccess)));
    }

    @Override
    public boolean AddUserToMediaPipeline(String MediaPipeline, String HaveAccess) {
        if (HaveAccessToMediaPipeline.containsKey(MediaPipeline)) {
            HaveAccessToMediaPipeline.get(MediaPipeline).add(HaveAccess);
            HaveAccessToMediaPipeline.forEach((s, strings) -> System.out.println(s + "  " + strings));
            return true;
        }
        return false;
    }

    @Override
    public void AddUserToMediaPipeline(String MediaPipeline, Set<String> HaveAccess) {
        if (HaveAccessToMediaPipeline.containsKey(MediaPipeline))
            HaveAccessToMediaPipeline.get(MediaPipeline).addAll(HaveAccess);
    }

    @Override
    public Set<String> WhatMediaPipelineICanAccess(String me) {
        return HaveAccessToMediaPipeline.entrySet().stream()
                .filter(x -> x.getValue().stream().anyMatch(q -> q.equals(me))).map(x -> x.getKey()).collect(Collectors.toSet());
    }

    @Override
    public void RemoveMediaPipeline(String MediaPipeline) {
        if (HaveAccessToMediaPipeline.containsKey(MediaPipeline)) HaveAccessToMediaPipeline.remove(MediaPipeline);
    }

    @Override
    public boolean IHaveAccessToMediaPipeline(String MediaPipeline, String ME) {
        if (HaveAccessToMediaPipeline.containsKey(MediaPipeline))
            return HaveAccessToMediaPipeline.get(MediaPipeline).stream().anyMatch(x -> x.equals(ME));
        return false;
    }

    @Override
    public void AddOnlineUser(String name, String simpSessionId) {
        OnlineSessionData.put(name, new HashSet<>(Arrays.asList(simpSessionId)));
    }

    @Override
    public void RemoveOnlineUser(String name) {
        OnlineSessionData.remove(name);
    }

    @Override
    public Set<String> UserSessions(String name) {
        return OnlineSessionData.get(name);
    }

    @Override
    public void UpdateOne(String name, Set<String> s) {
        OnlineSessionData.put(name, s);
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

    public synchronized void Notify(List<user> personList, String group, String user) {
        for (user person : personList) {
            template.convertAndSendToUser(
                    person.getEmail(), "/topic/message",
                    new ConversationMessage(MessageType.LOGOUT_MESSAGE, group, null, user, null));
        }
    }
}
