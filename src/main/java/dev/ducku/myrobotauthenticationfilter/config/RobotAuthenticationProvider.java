package dev.ducku.myrobotauthenticationfilter.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class RobotAuthenticationProvider implements AuthenticationProvider {

    private final String key;

    public RobotAuthenticationProvider(String key) {
        this.key = key;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RobotAuthentication robotAuthentication = (RobotAuthentication) authentication;
        String requestKey = robotAuthentication.getKey();
        if (requestKey.equals(this.key)) {
            return RobotAuthentication.authenticated();
        }
        throw new BadCredentialsException("You are not Ms Robot 🤖⛔");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RobotAuthentication.class.isAssignableFrom(authentication);
    }
}
