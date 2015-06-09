package thymeleafLearn.domain;

import org.kurento.client.MediaPipeline;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ashraf on 6/9/15.
 */
public class CallMediaPipeline {
    private MediaPipeline MediaPipeline;
    private MediaPipelineType MediaPipelineType;
    private Set<userSession> sessions = new HashSet<>();

    public CallMediaPipeline() {
    }

    public CallMediaPipeline(org.kurento.client.MediaPipeline mediaPipeline, thymeleafLearn.domain.MediaPipelineType mediaPipelineType, Set<userSession> sessions) {
        MediaPipeline = mediaPipeline;
        MediaPipelineType = mediaPipelineType;
        this.sessions = sessions;
    }

    public org.kurento.client.MediaPipeline getMediaPipeline() {
        return MediaPipeline;
    }

    public CallMediaPipeline setMediaPipeline(org.kurento.client.MediaPipeline mediaPipeline) {
        MediaPipeline = mediaPipeline;
        return this;
    }

    public thymeleafLearn.domain.MediaPipelineType getMediaPipelineType() {
        return MediaPipelineType;
    }

    public CallMediaPipeline setMediaPipelineType(thymeleafLearn.domain.MediaPipelineType mediaPipelineType) {
        MediaPipelineType = mediaPipelineType;
        return this;
    }

    public Set<userSession> getSessions() {
        return sessions;
    }

    public CallMediaPipeline setSessions(Set<userSession> sessions) {
        this.sessions = sessions;
        return this;
    }
}
