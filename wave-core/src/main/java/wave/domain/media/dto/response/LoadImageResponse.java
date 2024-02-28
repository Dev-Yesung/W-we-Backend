package wave.domain.media.dto.response;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

public record LoadImageResponse(
	MediaType mediaType,
	InputStreamResource resource
) {
}
