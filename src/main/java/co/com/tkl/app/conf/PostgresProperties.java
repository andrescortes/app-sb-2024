package co.com.tkl.app.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ConfigurationProperties(prefix = "app.db")
@Configuration
public class PostgresProperties {
    private String url;
    private String username;
    private String password;
}
