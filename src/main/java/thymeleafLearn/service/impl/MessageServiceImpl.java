package thymeleafLearn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import thymeleafLearn.domain.MediaPipelineType;
import thymeleafLearn.domain.UserType;
import thymeleafLearn.domain.userSession;
import thymeleafLearn.messages.ConversationMessage;
import thymeleafLearn.messages.MessageType;
import thymeleafLearn.service.MediaPipelineService;
import thymeleafLearn.service.MessageService;
import thymeleafLearn.service.OnlineSession;

/**
 * Created by ashraf on 6/11/15.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MediaPipelineService mediaPipelineService;
    @Autowired
    OnlineSession onlineSession;
    @Autowired
    SimpMessagingTemplate template;


    // we will also send message to message.content to invite to pipeline
    @Override
    public void HandelINVITE_TO_PIPELINE_MESSAGE(ConversationMessage message, String sessionId, String name) {
        boolean b = onlineSession.AddUserToMediaPipeline(name, message.getTo());

        if (b) {
            template.convertAndSendToUser(message.getTo(), "/topic/message",
                    new ConversationMessage(MessageType.INVITE_TO_PIPELINE_MESSAGE, message.getTo(), null, name, null));
        }
    }

    @Override
    public void HandelCREATE_PIPELINE_MESSAGE(ConversationMessage message, String sessionId, String name) {
        MediaPipelineType mediaPipelineType = message.getMediaPipelineType();
        UserType userType = UserType.Send;
        if (mediaPipelineType == MediaPipelineType.One_To_One) {
            userType = UserType.SendAndReceive;
        }
        mediaPipelineService.CreatePipeline(mediaPipelineType,
                new userSession(name, sessionId, userType), message.getContent());
    }

    @Override
    public void HandeJOIN_PIPELINE_MESSAGE(ConversationMessage message, String sessionId, String name) {
        if (onlineSession.IHaveAccessToMediaPipeline(message.getTo(), name)) {
            userSession session = new userSession(name, sessionId, UserType.Receive);
            mediaPipelineService.addUserToPipeline(message.getTo(), session, message.getContent());
        } else {
            template.convertAndSendToUser(name, "/topic/message",
                    new ConversationMessage(MessageType.ERROR, "you don't ability to access  "+message.getTo()+" ask him to invite you or it may be not exist", null, name, null));

        }
    }
}
