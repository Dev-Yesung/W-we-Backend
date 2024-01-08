package wave.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "configuration.file.music")
@Configuration
public class MusicFileConfiguration {
	private String host;
	private String rootPath;
	private String fileNameSeparator;
	private long maxFileSize;
	private List<String> fileExtensions;
}
