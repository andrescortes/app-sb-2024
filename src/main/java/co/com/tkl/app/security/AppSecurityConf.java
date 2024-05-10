package co.com.tkl.app.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class AppSecurityConf {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Value("${app.origins}")
    private List<String> origins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth ->
                                auth
                                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/users").hasRole(ADMIN)
//                                // products
//                                .requestMatchers(HttpMethod.GET, "/products/{id}").hasAnyRole(USER, ADMIN)
//                                .requestMatchers(HttpMethod.POST, "/products").hasRole(ADMIN)
//                                .requestMatchers(HttpMethod.PUT, "/products/{id}").hasRole(ADMIN)
//                                .requestMatchers(HttpMethod.DELETE, "/products/{id}").hasRole(ADMIN)
                                        .anyRequest()
                                        .authenticated()
                )
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(crs -> crs.configurationSource(corsConfigurationSource()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("Origins: {}", Arrays.toString(origins.toArray()));
        CorsConfiguration conf = new CorsConfiguration();
        conf.setAllowedOrigins(origins);
        conf.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        conf.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", conf);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsFilterBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        corsFilterBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsFilterBean;
    }
}

