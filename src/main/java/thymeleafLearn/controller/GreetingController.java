package thymeleafLearn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import thymeleafLearn.domain.Greeting;
import thymeleafLearn.domain.HelloMessage;

import java.security.Principal;

@Controller
public class GreetingController {

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/hello")
    public void greeting(Principal principal,HelloMessage message) throws Exception {
        System.out.println(principal.getName());
        template.convertAndSendToUser("revo","/topic/greetings", new Greeting("Hello, " + message.getName() + "!"));
  }

}
