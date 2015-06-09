package thymeleafLearn.controller;

import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.kurento.client.WebRtcEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import thymeleafLearn.domain.Greeting;
import thymeleafLearn.domain.HelloMessage;
import thymeleafLearn.service.OnlineSession;

import java.security.Principal;

@Controller
public class GreetingController {

    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    private KurentoClient kurento;
    MediaPipeline pipeline;
    WebRtcEndpoint webRtcEndpoint;
    @MessageMapping("/hello")
    public void greeting(Principal principal, HelloMessage message) throws Exception {
        template.convertAndSendToUser("revo", "/topic/greetings", new Greeting("Hello, " + message.getName() + "!"));
    }

    @MessageMapping("/IwillSend")
    public void IwillSend(String sdpOffer, Principal principal) {
        pipeline = kurento.createMediaPipeline();
        webRtcEndpoint = new WebRtcEndpoint.Builder(pipeline).build();
        template.convertAndSendToUser(principal.getName(), "/topic/Send", webRtcEndpoint.processOffer(sdpOffer));
    }

    @MessageMapping("/IwillRecive")
    public void IwillRecive(String sdpOffer, Principal principal) {
        WebRtcEndpoint nextWebRtc = new WebRtcEndpoint.Builder(pipeline).build();
        webRtcEndpoint.connect(nextWebRtc);
        template.convertAndSendToUser(principal.getName(), "/topic/Recive", nextWebRtc.processOffer(sdpOffer));
    }
}
