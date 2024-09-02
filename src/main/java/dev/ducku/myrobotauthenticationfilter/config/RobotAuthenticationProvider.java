package dev.ducku.myrobotauthenticationfilter.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public class RobotAuthenticationProvider implements AuthenticationProvider {

    private final List<String> keys;

    public RobotAuthenticationProvider(List<String> keys) {
        this.keys = keys;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RobotAuthentication robotAuthentication = (RobotAuthentication) authentication;
        String requestKey = robotAuthentication.getKey();
        if (keys.contains(requestKey)) {
            return RobotAuthentication.authenticated();
        }
        throw new BadCredentialsException("You are not Ms Robot 🤖⛔");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RobotAuthentication.class.isAssignableFrom(authentication);
    }
}
