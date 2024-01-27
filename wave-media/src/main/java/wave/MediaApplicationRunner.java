package wave;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import wave.config.ImageConfig;
import wave.config.MusicConfig;
import wave.global.utils.FileUtils;

@RequiredArgsConstructor
@Component
public class MediaApplicationRunner implements ApplicationRunner {

	private final ImageConfig imageConfig;
	private final MusicConfig musicConfig;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		initializeSystem();
	}

	private void initializeSystem() {
		String imageDirectory = imageConfig.getRootPath();
		String musicDirectory = musicConfig.getRootPath();

		initializeMediaStorage(imageDirectory);
		initializeMediaStorage(musicDirectory);
	}

	private void initializeMediaStorage(String path) {
		String normalizedPath = FileUtils.getNormalizedPath(path);
		FileUtils.createDirectoryIfNotExist(normalizedPath);
	}
}
