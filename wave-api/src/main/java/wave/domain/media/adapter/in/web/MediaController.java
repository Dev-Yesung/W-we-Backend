package wave.domain.media.adapter.in.web;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.media.application.MediaService;
import wave.domain.media.dto.request.LoadPostImageRequest;
import wave.domain.media.dto.response.LoadImageResponse;
import wave.global.aop.AuthenticationUser;
import wave.global.common.WebAdapter;

@RequiredArgsConstructor
@RequestMapping("/api/media")
@RestController
@WebAdapter
public class MediaController {

	private final MediaService mediaService;

	@GetMapping("/images/posts/{postId}")
	public ResponseEntity<InputStreamResource> loadPostImageByPostIdAndUserId(
		@PathVariable Long postId,
		@AuthenticationUser User user
	) {
		LoadPostImageRequest request = new LoadPostImageRequest(postId, user);
		LoadImageResponse response = mediaService.loadPostImageFile(request);

		return ResponseEntity.ok()
			.contentType(response.mediaType())
			.body(response.resource());
	}

	@GetMapping("/images/users/{userId}/profile")
	public ResponseEntity<InputStreamResource> loadUserProfileImageByUserId(
		@PathVariable Long userId
	) {
		LoadImageResponse response = mediaService.loadUserProfileImageByUserId(userId);

		return ResponseEntity.ok()
			.contentType(response.mediaType())
			.body(response.resource());
	}
}
