package dev.ducku.myrobotauthenticationfilter.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class RobotFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    public RobotFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //0 SHOULD WE HANDLE THIS 🤔
        if (!Collections.list(request.getHeaderNames()).contains("x-robot-key")) {
            filterChain.doFilter(request, response);
            return;
        }
        String requestKey = request.getHeader("x-robot-key");
        RobotAuthentication robotAuthentication = RobotAuthentication.authenticationToken(requestKey);

        //1. MAKING DECISION....
        try {
            Authentication authenticated = authenticationManager.authenticate(robotAuthentication);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticated);
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);
            return;
        } catch (AuthenticationException e) {
            response.setContentType("application/html;charset=utf-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(e.getMessage());
            return;
        }
        //2. DO THE REST
    }
}
