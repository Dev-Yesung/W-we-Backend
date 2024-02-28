package wave.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "configuration.media.image.file")
@Configuration
public class ImageConfig {

	private String rootPath;
	private String urlPath;
	private String fileNameSeparator;
	private long maxFileSize;
	private List<String> permittedFileExtensions;

}
