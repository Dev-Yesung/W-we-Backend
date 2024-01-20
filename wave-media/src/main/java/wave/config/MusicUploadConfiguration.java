package wave.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "configuration.upload.music")
@Configuration
public class MusicUploadConfiguration {
	private String host;
	private String rootPath;
	private String fileNameSeparator;
	private long maxFileSize;
	private List<String> fileExtensions;
}
