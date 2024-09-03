package internship.project.election.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import internship.project.election.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class WebSecurity {

    private JwtFilter jwtFilter;

    public WebSecurity(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                /*
                 * Disable CSRF (authenticating with JWT so no cookies are being used)
                 */
                .csrf(AbstractHttpConfigurer::disable)
                /*
                 * Disable default form login redirection and change 403 to 401 status
                 */
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                authException.getMessage())))
                .authorizeHttpRequests(authorize -> authorize
                        // public endpoints
                        .requestMatchers("/error/**", "/public/**",
                                "/test/**", "/auth/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                /*
                 * Disable session management (no session will be created because we are using
                 * JWT)
                 */
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /*
                 * Add JWT filter before UsernamePasswordAuthenticationFilter
                 */
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
