package wave.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
public class CacheConfig {

	private final TimeUnit timeUnitInSeconds = TimeUnit.SECONDS;

	@Value("${spring.data.redis.key-separator}")
	private String keySeparator;
}
