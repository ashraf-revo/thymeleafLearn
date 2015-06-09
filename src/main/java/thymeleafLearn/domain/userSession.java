package thymeleafLearn.domain;

import org.kurento.client.WebRtcEndpoint;

/**
 * Created by ashraf on 6/9/15.
 */
public class userSession {

    private String Name;
    private String Session;
    private String SdpOffer;
    private UserType UserType;
    WebRtcEndpoint WebRtcEndpoint;

    public userSession() {
    }

    public userSession(String name, String session, String sdpOffer, thymeleafLearn.domain.UserType userType, org.kurento.client.WebRtcEndpoint webRtcEndpoint) {
        Name = name;
        Session = session;
        SdpOffer = sdpOffer;
        UserType = userType;
        WebRtcEndpoint = webRtcEndpoint;
    }

    public String getName() {
        return Name;
    }

    public userSession setName(String name) {
        Name = name;
        return this;
    }

    public String getSession() {
        return Session;
    }

    public userSession setSession(String session) {
        Session = session;
        return this;
    }

    public String getSdpOffer() {
        return SdpOffer;
    }

    public userSession setSdpOffer(String sdpOffer) {
        SdpOffer = sdpOffer;
        return this;
    }

    public thymeleafLearn.domain.UserType getUserType() {
        return UserType;
    }

    public userSession setUserType(thymeleafLearn.domain.UserType userType) {
        UserType = userType;
        return this;
    }

    public org.kurento.client.WebRtcEndpoint getWebRtcEndpoint() {
        return WebRtcEndpoint;
    }

    public userSession setWebRtcEndpoint(org.kurento.client.WebRtcEndpoint webRtcEndpoint) {
        WebRtcEndpoint = webRtcEndpoint;
        return this;
    }
}
