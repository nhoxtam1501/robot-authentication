package dev.ducku.myrobotauthenticationfilter.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

public class RobotAuthenticationConfigurer extends AbstractHttpConfigurer<RobotAuthenticationConfigurer, HttpSecurity> {

    private final List<String> keys = new ArrayList<>();

    @Override
    public void init(HttpSecurity http) throws Exception {
        // Called when http.build() is called.
        // All Configurers have their .init() called first.
        // Typically used to set up some defaults and register AuthenticationProviders.
        http.authenticationProvider(new RobotAuthenticationProvider(keys));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Called when http.build() is called.
        // All Configurers have their .configure() called after ALL configurers had .init() called.
        // Here we have the most objects in the http.getSharedObject cache.
        // Other Configurers have been added and init'd, too.
        //
        // Typically: register Filters.
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new RobotFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
    }

    public RobotAuthenticationConfigurer key(String key) {
        this.keys.add(key);
        return this;
    }
}
