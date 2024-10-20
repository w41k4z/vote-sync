package ceni.system.votesync.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessExceptionHandling {

    public ExceptionHandlingConfigurer<HttpSecurity> handleException(
            ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {
        return exceptionHandling
                .authenticationEntryPoint(
                        /*
                         * Disable default form login redirection and change 403 to 401 status
                         */
                        (request, response, authException) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                authException.getMessage()))
                .accessDeniedHandler(
                        (request, response, accessDeniedException) -> response.sendError(
                                HttpServletResponse.SC_FORBIDDEN,
                                accessDeniedException.getMessage()));
    }
}
