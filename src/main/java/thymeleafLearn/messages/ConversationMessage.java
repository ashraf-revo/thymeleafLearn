package thymeleafLearn.messages;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by ashraf on 6/10/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversationMessage implements Serializable {
    private MessageType messageType;
    private String From;
    private String To;
    private String Content;

    public ConversationMessage() {
    }

    public ConversationMessage(MessageType messageType, String from, String to, String content) {
        this.messageType = messageType;
        From = from;
        To = to;
        Content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public ConversationMessage setMessageType(MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public String getFrom() {
        return From;
    }

    public ConversationMessage setFrom(String from) {
        From = from;
        return this;
    }

    public String getTo() {
        return To;
    }

    public ConversationMessage setTo(String to) {
        To = to;
        return this;
    }

    public String getContent() {
        return Content;
    }

    public ConversationMessage setContent(String content) {
        Content = content;
        return this;
    }
}
