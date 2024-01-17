package wave.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "configuration.jwt")
@Configuration
public class JwtConfig {
	private String issuer;
	private String algorithmType;
	private String tokenSecret;
	private Long accessTokenExpirySeconds;
	private Long refreshTokenExpirySeconds;
}
