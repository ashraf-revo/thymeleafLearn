package thymeleafLearn.service;

import thymeleafLearn.domain.CallMediaPipeline;
import thymeleafLearn.domain.MediaPipelineType;
import thymeleafLearn.domain.userSession;

import java.util.List;
import java.util.Set;

/**
 * Created by ashraf on 6/10/15.
 */
public interface MediaPipelineService {

    public boolean CreatePipeline(MediaPipelineType mediaPipelineType, userSession sessions, String sdpOffer);

    public boolean addUserToPipeline(String NameOfCreatorOfPipeline, userSession userSession, String sdpOffer);

    public void ReleasePipelineUsingNameOfCreatorOfPipeline(String NameOfCreatorOfPipeline);

    public void ReleasePipelineUsingSessionId(CallMediaPipeline CallMediaPipeline,List<userSession> collect,String simpSessionId);

    public boolean HaveOrInMediaPipeline(String Name);

    public CallMediaPipeline getCallMediaPipeline(String Creator);

    void SomeOneRemoveSessionCheckHim(String simpSessionId);
}
