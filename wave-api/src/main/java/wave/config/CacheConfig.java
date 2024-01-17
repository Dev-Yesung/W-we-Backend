package wave.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ConfigurationProperties("configuration.cache")
@Configuration
public class CacheConfig {
	private int signupLimitSeconds;
	private int loginLimitSeconds;
}
