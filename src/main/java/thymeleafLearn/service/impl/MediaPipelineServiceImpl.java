package thymeleafLearn.service.impl;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.kurento.client.KurentoClient;
import org.kurento.client.WebRtcEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import thymeleafLearn.domain.CallMediaPipeline;
import thymeleafLearn.domain.MediaPipelineType;
import thymeleafLearn.domain.UserType;
import thymeleafLearn.domain.userSession;
import thymeleafLearn.messages.ConversationMessage;
import thymeleafLearn.messages.MessageType;
import thymeleafLearn.service.MediaPipelineService;
import thymeleafLearn.service.OnlineSession;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by ashraf on 6/10/15.
 */
@Service
public class MediaPipelineServiceImpl implements MediaPipelineService {
    public ConcurrentHashSet<CallMediaPipeline> mediaPipelines = new ConcurrentHashSet<CallMediaPipeline>();
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    KurentoClient kurentoClient;
    @Autowired
    OnlineSession onlineSession;


    @Override
    public void CreatePipeline(MediaPipelineType mediaPipelineType, userSession sessions, String sdpOffer) {

        if (!HaveOrInMediaPipeline(sessions.getName())) {
            CallMediaPipeline pipeline = new CallMediaPipeline();
            pipeline.setMediaPipeline(kurentoClient.createMediaPipeline());
            pipeline.AdduserSession(sessions.setWebRtcEndpoint(new WebRtcEndpoint.Builder(pipeline.getMediaPipeline()).build()));
            pipeline.setMediaPipelineType(mediaPipelineType);
            mediaPipelines.add(pipeline);
            Optional<userSession> first = pipeline.getSessions().stream().findFirst();
            first.ifPresent(x -> {
                String s = x.getWebRtcEndpoint().processOffer(sdpOffer);
                template.convertAndSendToUser(sessions.getName(), "/topic/message",
                        new ConversationMessage(MessageType.SDPOFFER_MESSAGE, s, null, null));
            });

        }
        template.convertAndSendToUser(sessions.getName(), "/topic/message",
                new ConversationMessage(MessageType.ERROR, "Error Create " + mediaPipelineType + " We Think You In Running Video Chat ", null, null));

    }

    @Override
    public void addUserToPipeline(String NameOfCreatorOfPipeline, userSession sessions, String sdpOffer) {
        if (!HaveOrInMediaPipeline(sessions.getName())) {
            {
                CallMediaPipeline callMediaPipeline = getCallMediaPipeline(NameOfCreatorOfPipeline);
                if (callMediaPipeline != null) {
                    WebRtcEndpoint rtcEndpoint = new WebRtcEndpoint.Builder(callMediaPipeline.getMediaPipeline()).build();
                    Optional<userSession> first = callMediaPipeline.getSessions().stream().findFirst();
                    first.ifPresent(x -> x.getWebRtcEndpoint().connect(rtcEndpoint));
                    callMediaPipeline.AdduserSession(sessions.setWebRtcEndpoint(rtcEndpoint));
                    Optional<String> x = Optional.of(sessions.getWebRtcEndpoint().processOffer(sdpOffer));
                    x.ifPresent(s -> {
                        template.convertAndSendToUser(sessions.getName(), "/topic/message",
                                new ConversationMessage(MessageType.SDPOFFER_MESSAGE, s, null, null));
                    });

                }
            }

        }
        template.convertAndSendToUser(sessions.getName(), "/topic/message",
                new ConversationMessage(MessageType.ERROR, "Error Adding You in This We Think You In Running Video Chat ", null, null));


    }

    @Override
    public void ReleasePipelineUsingNameOfCreatorOfPipeline(String NameOfCreatorOfPipeline) {
        CallMediaPipeline callMediaPipeline = getCallMediaPipeline(NameOfCreatorOfPipeline);
        if (callMediaPipeline != null) {
            callMediaPipeline.getMediaPipeline().release();
            callMediaPipeline.getSessions().stream().filter(x -> !x.getName().equals(NameOfCreatorOfPipeline)).forEach(x -> {
                template.convertAndSendToUser(x.getName(), "/topic/message",
                        new ConversationMessage(MessageType.RELEASE_PIPELINE_MESSAGE, null, null, null));

            });
            mediaPipelines.remove(callMediaPipeline);
            onlineSession.RemoveMediaPipeline(NameOfCreatorOfPipeline);
        }
    }

    @Override
    public void ReleasePipelineUsingSessionId(CallMediaPipeline CallMediaPipeline, List<userSession> collect, String simpSessionId) {
        CallMediaPipeline.getMediaPipeline().release();
        collect.stream().filter(z -> {
            if (z.getSession() != simpSessionId) return true;
            else {
                onlineSession.RemoveMediaPipeline(z.getName());
                return false;
            }
        }).forEach(a -> {
            template.convertAndSendToUser(a.getName(), "/topic/message",
                    new ConversationMessage(MessageType.RELEASE_PIPELINE_MESSAGE, null, null, null));
        });
        mediaPipelines.remove(CallMediaPipeline);

    }

    @Override
    public boolean HaveOrInMediaPipeline(String Name) {
        return mediaPipelines.stream().map(x -> x.getSessions().stream().anyMatch(u -> u.getName().endsWith(Name))).anyMatch(x -> x);
    }

    @Override
    public CallMediaPipeline getCallMediaPipeline(String Creator) {

        Optional<CallMediaPipeline> any = mediaPipelines.stream().filter(x -> {
            Optional<userSession> first = x.getSessions().stream().findFirst();
            if (first.isPresent() && first.get().getName().equals(Creator)) {
                return true;
            }
            return false;
        }).findAny();

        if (any.isPresent()) return any.get();

        return null;
    }

    @Override
    public void SomeOneRemoveSessionCheckHim(String simpSessionId) {
        Optional<CallMediaPipeline> any = mediaPipelines.stream().filter(x ->
                        x.getSessions().stream().anyMatch(z -> z.getSession().equals(simpSessionId))
        ).findAny();

        any.ifPresent(x -> {
            List<userSession> collect = x.getSessions().stream().collect(Collectors.toList());
            if (x.getMediaPipelineType() == MediaPipelineType.One_To_Many) {
                Optional<userSession> sender = collect.stream().filter(z -> z.getUserType() == UserType.Send).findAny();
                sender.ifPresent(z -> {
                    if (z.getSession().equals(simpSessionId)) {
                        ReleasePipelineUsingSessionId(x, collect, simpSessionId);
                    } else {
                        x.getSessions().removeIf(a -> a.getSession().equals(simpSessionId));
                    }
                });

            } else if (x.getMediaPipelineType() == MediaPipelineType.One_To_One) {
                ReleasePipelineUsingSessionId(x, collect, simpSessionId);
            }
        });
    }
}
