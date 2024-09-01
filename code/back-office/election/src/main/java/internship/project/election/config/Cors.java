package internship.project.election.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Cors {

    @Value("${cors.allowed.origin.pattern}")
    private String ALLOWED_ORIGIN_PATTERN;

    // Development mode
    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods(CorsConfiguration.ALL)
                        .allowedHeaders(CorsConfiguration.ALL)
                        .allowedOriginPatterns(CorsConfiguration.ALL);
            }
        };
    }

    // // Production mode
    // @Bean
    // WebMvcConfigurer corsConfigurer() {
    // return new WebMvcConfigurer() {
    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    // registry
    // .addMapping("/ceni/app/**")
    // .allowedMethods("GET", "POST")
    // .allowedHeaders(CorsConfiguration.ALL)
    // .allowedOriginPatterns(ALLOWED_ORIGIN_PATTERN);
    // }
    // };
    // }
}
