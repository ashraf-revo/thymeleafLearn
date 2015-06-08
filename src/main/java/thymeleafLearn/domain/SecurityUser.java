package thymeleafLearn.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;

/**
 * Created by ashraf on 6/8/15.
 */
public class SecurityUser implements SocialUserDetails {
    private user u;

    public SecurityUser(user u) {
        this.u = u;
    }

    @Override
    public String getUserId() {
        return u.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return u.getAuthorities();
    }

    @Override
    public String getPassword() {
        return u.getPassword();
    }

    @Override
    public String getUsername() {
        return u.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
