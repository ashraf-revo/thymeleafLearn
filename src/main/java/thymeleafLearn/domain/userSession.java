package thymeleafLearn.domain;

import org.kurento.client.WebRtcEndpoint;

/**
 * Created by ashraf on 6/9/15.
 */
public class userSession {

    private String Name;
    private String Session;
    private UserType UserType;
    WebRtcEndpoint WebRtcEndpoint;

    public userSession() {
    }

    public userSession(String name, String session, thymeleafLearn.domain.UserType userType) {
        Name = name;
        Session = session;
        UserType = userType;
    }

    public userSession(String name, String session, UserType userType, WebRtcEndpoint webRtcEndpoint) {
        Name = name;
        Session = session;
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

    public UserType getUserType() {
        return UserType;
    }

    public userSession setUserType(UserType userType) {
        UserType = userType;
        return this;
    }

    public WebRtcEndpoint getWebRtcEndpoint() {
        return WebRtcEndpoint;
    }

    public userSession setWebRtcEndpoint(WebRtcEndpoint webRtcEndpoint) {
        WebRtcEndpoint = webRtcEndpoint;
        return this;
    }
}
