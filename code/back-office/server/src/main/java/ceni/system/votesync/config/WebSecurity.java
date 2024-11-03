package ceni.system.votesync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ceni.system.votesync.filter.JwtFilter;

@Configuration
public class WebSecurity {

        private JwtFilter jwtFilter;
        private Cors corsConfiguration;
        private AccessExceptionHandling accessExceptionHandling;

        public WebSecurity(JwtFilter jwtFilter, Cors corsConfiguration,
                        AccessExceptionHandling accessExceptionHandling) {
                this.jwtFilter = jwtFilter;
                this.corsConfiguration = corsConfiguration;
                this.accessExceptionHandling = accessExceptionHandling;
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                /*
                                 * Disable CSRF (authenticating with JWT so no cookies are being used)
                                 */
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(cors -> cors.configurationSource(corsConfiguration))
                                .exceptionHandling(exception -> accessExceptionHandling.handleException(exception))
                                .authorizeHttpRequests(authorize -> authorizationConfigurer(authorize))
                                /*
                                 * Disable session management (no session will be created because we are using
                                 * JWT)
                                 */
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                /*
                                 * Add JWT filter before UsernamePasswordAuthenticationFilter
                                 */
                                .addFilterBefore(
                                                jwtFilter,
                                                UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

        private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationConfigurer(
                        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize) {
                return authorize
                                // public endpoints
                                .requestMatchers("/error/**", "/public/**",
                                                "/test/**", "/auth/**", "/api/polling-stations/nearest")
                                .permitAll()
                                // PollingStation Manager endpoints
                                .requestMatchers("/api/polling-stations/data/**", "/api/elections/results/upload")
                                .hasAnyAuthority(Authority.MANAGER, Authority.ADMIN)
                                // Operator endpoints
                                .requestMatchers("/api/elections/results/pending", "/api/elections/results/validate")
                                .hasAnyAuthority(Authority.OPERATOR, Authority.ADMIN)
                                // Admin endpoints
                                .requestMatchers("/api/users/**", "/api/roles/**", "/api/stats/**", "/api/elections/**",
                                                "/api/election-types/**")
                                .hasAuthority(Authority.ADMIN)
                                // User endpoints
                                .anyRequest()
                                .authenticated();
        }
}
