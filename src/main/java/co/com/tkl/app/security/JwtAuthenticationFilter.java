package co.com.tkl.app.security;

import co.com.tkl.app.entities.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static co.com.tkl.app.security.TokenJwtConf.AUTHORITIES;
import static co.com.tkl.app.security.TokenJwtConf.MESSAGE;
import static co.com.tkl.app.security.TokenJwtConf.PREFIX_TOKEN;
import static co.com.tkl.app.security.TokenJwtConf.SECRET_KEY;
import static co.com.tkl.app.security.TokenJwtConf.TOKEN;
import static co.com.tkl.app.security.TokenJwtConf.USERNAME;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user;
        String username;
        String password;
        try {
            user = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String username = principal.getUsername();

        Claims claims = Jwts
                .claims()
                .add(AUTHORITIES, new ObjectMapper().writeValueAsString(principal.getAuthorities()))
                .add(USERNAME, username)
                .build();
        String token = Jwts
                .builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .claims(claims)
                .signWith(SECRET_KEY)
                .compact();

        response.addHeader(HttpHeaders.AUTHORIZATION, PREFIX_TOKEN.concat(token));

        Map<String, String> body = new HashMap<>();
        body.put(TOKEN, token);
        body.put(USERNAME, username);
        body.put(MESSAGE, String.format("Hi %s, you have successfully logged in.", username));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        Map<String, String> body = new HashMap<>();
        body.put(MESSAGE, "Error in the authentication username or password are incorrect");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

}
