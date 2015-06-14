package thymeleafLearn.controller;

import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.kurento.client.WebRtcEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import thymeleafLearn.domain.Greeting;
import thymeleafLearn.domain.HelloMessage;
import thymeleafLearn.messages.ConversationMessage;
import thymeleafLearn.messages.MessageType;
import thymeleafLearn.service.MessageService;

import java.security.Principal;

@Controller
public class GreetingController {

    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    KurentoClient kurentoClient;
    MediaPipeline pipeline;
    WebRtcEndpoint webRtcEndpoint;
    WebRtcEndpoint nextWebRtc;
    @Autowired
    MessageService messageService;

    @MessageMapping("/hello")
    public void greeting(Principal principal, HelloMessage message) throws Exception {
        template.convertAndSendToUser("revo", "/topic/greetings", new Greeting("Hello, " + message.getName() + "!"));
    }

    @MessageMapping("/IwillSend")
    public void IwillSend(String sdpOffer, Principal principal) {
        pipeline = kurentoClient.createMediaPipeline();
        webRtcEndpoint = new WebRtcEndpoint.Builder(pipeline).build();
        template.convertAndSendToUser(principal.getName(), "/topic/Send", webRtcEndpoint.processOffer(sdpOffer));
    }


    @MessageMapping("/IwillRecive")
    public void IwillRecive(String sdpOffer, Principal principal) {
        nextWebRtc = new WebRtcEndpoint.Builder(pipeline).build();
        webRtcEndpoint.connect(nextWebRtc);
        template.convertAndSendToUser(principal.getName(), "/topic/Recive", nextWebRtc.processOffer(sdpOffer));
    }

    @MessageMapping("/message")
    public void message(Principal principal, @Payload ConversationMessage message, SimpMessageHeaderAccessor header) {
        String sessionId = header.getSessionId();
        String name = principal.getName();
        if (message.getMessageType() != null) {
            if (message.getMessageType() == MessageType.INVITE_TO_PIPELINE_MESSAGE) {
                messageService.HandelINVITE_TO_PIPELINE_MESSAGE(message, sessionId, name);
            } else if (message.getMessageType() == MessageType.CREATE_PIPELINE_MESSAGE) {
                messageService.HandelCREATE_PIPELINE_MESSAGE(message, sessionId, name);
            } else if (message.getMessageType() == MessageType.JOIN_PIPELINE_MESSAGE) {
                messageService.HandeJOIN_PIPELINE_MESSAGE(message, sessionId, name);
            }
            else{
                System.out.println("nnnnnnnnnn");
            }
        }
    }
}
