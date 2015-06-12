package thymeleafLearn.service;

import org.springframework.beans.factory.annotation.Autowired;
import thymeleafLearn.messages.ConversationMessage;

/**
 * Created by ashraf on 6/11/15.
 */
public interface MessageService {

    public void HandelINVITE_TO_PIPELINE_MESSAGE(ConversationMessage message, String sessionId, String name);

    public void HandelCREATE_PIPELINE_MESSAGE(ConversationMessage message, String sessionId, String name);

    public void HandeJOIN_PIPELINE_MESSAGE(ConversationMessage message, String sessionId, String name);
}
