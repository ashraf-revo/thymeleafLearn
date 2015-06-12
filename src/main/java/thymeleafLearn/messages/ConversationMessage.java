package thymeleafLearn.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import thymeleafLearn.domain.MediaPipelineType;

import java.io.Serializable;

/**
 * Created by ashraf on 6/10/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversationMessage implements Serializable {
    private MessageType messageType;
    private String content;
    private MediaPipelineType mediaPipelineType;
    private String from;
    private String to;

    public ConversationMessage() {
    }


    public ConversationMessage(MessageType messageType, String content, MediaPipelineType mediaPipelineType, String from, String to) {
        this.messageType = messageType;
        this.content = content;
        this.mediaPipelineType = mediaPipelineType;
        this.from = from;
        this.to = to;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public ConversationMessage setMessageType(MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public ConversationMessage setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public ConversationMessage setTo(String to) {
        this.to = to;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ConversationMessage setContent(String content) {
        this.content = content;
        return this;
    }

    public MediaPipelineType getMediaPipelineType() {
        return mediaPipelineType;
    }

    public ConversationMessage setMediaPipelineType(MediaPipelineType mediaPipelineType) {
        this.mediaPipelineType = mediaPipelineType;
        return this;
    }
}
