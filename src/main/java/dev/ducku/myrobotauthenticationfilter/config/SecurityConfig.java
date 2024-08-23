package dev.ducku.myrobotauthenticationfilter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Value("${robot.x-robot-key}")
    private String key;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var tempAuthenticationManager = new ProviderManager(new RobotAuthenticationProvider(key));
        http.authorizeHttpRequests(requestConfig -> {
            requestConfig.requestMatchers("/").permitAll();
            requestConfig.anyRequest().authenticated();
        });
        http.httpBasic(Customizer.withDefaults());
        http.formLogin(Customizer.withDefaults());
        http.addFilterAfter(new RobotFilter(tempAuthenticationManager), BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}password").authorities("USER").build();
        return new InMemoryUserDetailsManager(user);
    }
}
