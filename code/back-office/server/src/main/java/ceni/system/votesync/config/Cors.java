package ceni.system.votesync.config;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class Cors implements CorsConfigurationSource {

    public static final List<String> ALLOWED_ORIGINS = List.of("*");
    public static final List<String> ALLOWED_METHODS = List.of("GET", "POST", "PUT", "DELETE");
    public static final List<String> ALLOWED_HEADERS = List.of("*");

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest arg0) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(ALLOWED_ORIGINS);
        config.setAllowedMethods(ALLOWED_METHODS);
        config.setAllowedHeaders(ALLOWED_HEADERS);
        return config;
    }
}
