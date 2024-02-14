package wave.domain.media.domain.vo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import wave.global.error.ErrorCode;
import wave.global.error.exception.FileException;

@RequiredArgsConstructor
public class MediaMultipartFile implements MultipartFile {

	private final Path path;

	@Override
	public String getName() {
		return path.getFileName()
			.toString();
	}

	@Override
	public String getOriginalFilename() {
		return getName();
	}

	@Override
	public String getContentType() {
		try {
			return Files.probeContentType(path);
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_GET_FILE_DATA, e);
		}
	}

	@Override
	public long getSize() {
		try {
			return Files.size(path);
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_GET_FILE_DATA, e);
		}
	}

	@Override
	public boolean isEmpty() {
		return getSize() == 0;
	}

	@Override
	public byte[] getBytes() {
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_GET_FILE_DATA, e);
		}
	}

	@Override
	public InputStream getInputStream() {
		try {
			return Files.newInputStream(path);
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_GET_FILE_DATA, e);
		}
	}

	@Override
	public Resource getResource() {
		return MultipartFile.super.getResource();
	}

	@Override
	public void transferTo(File dest) {
		transferTo(dest.toPath());
	}

	@Override
	public void transferTo(Path dest) {
		try {
			Files.copy(path, dest);
		} catch (IOException e) {
			throw new FileException(ErrorCode.UNABLE_TO_GET_FILE_DATA, e);
		}
	}

}
