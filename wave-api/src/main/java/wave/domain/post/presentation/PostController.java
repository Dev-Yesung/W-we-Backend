package wave.domain.post.presentation;

import static org.springframework.http.MediaType.*;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import wave.domain.post.application.PostService;
import wave.domain.post.dto.OtherMusicDto;
import wave.domain.post.dto.OwnMusicDto;
import wave.domain.post.dto.request.OtherMusicPostCreateRequest;
import wave.domain.post.dto.request.OwnMusicPostCreateRequest;
import wave.domain.post.dto.response.OtherMusicPostCreateResponse;
import wave.domain.post.dto.response.OwnMusicPostCreateResponse;
import wave.domain.user.domain.User;
import wave.global.aop.AuthenticationUser;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {
	private final PostService postService;

	@PostMapping(value = "/own-music", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<OwnMusicPostCreateResponse> createOwnMusicPost(
		@RequestBody @Validated OwnMusicPostCreateRequest request,
		@RequestPart MultipartFile ownMusic,
		@AuthenticationUser User user
	) {
		OwnMusicDto ownMusicDto = OwnMusicDto.of(request, ownMusic, user);
		OwnMusicPostCreateResponse response = postService.createOwnMusicPost(ownMusicDto);

		return ResponseEntity
			.created(URI.create("/own-music" + "/" + response.postId()))
			.body(response);
	}

	@PostMapping(value = "/other-music", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<OtherMusicPostCreateResponse> createOwnMusicPost(
		@RequestBody @Validated OtherMusicPostCreateRequest request,
		@AuthenticationUser User user
	) {
		OtherMusicDto otherMusicDto = OtherMusicDto.of(request, user);
		OtherMusicPostCreateResponse response = postService.createOtherMusicPost(otherMusicDto);

		return ResponseEntity
			.created(URI.create("/other-music" + "/" + response.postId()))
			.body(response);
	}
}
