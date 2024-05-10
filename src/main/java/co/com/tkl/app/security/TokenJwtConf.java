package co.com.tkl.app.security;

import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKey;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenJwtConf {

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String AUTHORITIES = "authorities";
    public static final String USERNAME = "username";
    public static final String TOKEN = "token";
    public static final String MESSAGE = "message";
}
