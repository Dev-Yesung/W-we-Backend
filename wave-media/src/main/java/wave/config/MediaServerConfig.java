package wave.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "configuration")
@Configuration
public class MediaServerConfig {

	private String host;
	private String port;

}
