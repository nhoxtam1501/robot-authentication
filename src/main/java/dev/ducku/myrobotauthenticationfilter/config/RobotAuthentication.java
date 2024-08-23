package dev.ducku.myrobotauthenticationfilter.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RobotAuthentication implements Authentication {
    private final boolean authenticated;
    private String key;
    private List<? extends GrantedAuthority> authorities;

    private RobotAuthentication(String key, List<? extends GrantedAuthority> authorities) {
        this.key = key;
        this.authorities = Collections.unmodifiableList(authorities);
        this.authenticated = !CollectionUtils.isEmpty(authorities);
    }

    public static RobotAuthentication authenticationToken(String key) {
        return new RobotAuthentication(key, Collections.emptyList());
    }

    public static RobotAuthentication authenticated() {
        return new RobotAuthentication(null, List.of(() -> "ROLE_robot"));
    }

    public String getKey() {
        return key;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return "Ms Robot 🤖";
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new RuntimeException("Don't try this at home ⛔");
    }

    @Override
    public String getName() {
        return "Ms Robot 🤖";
    }
}
