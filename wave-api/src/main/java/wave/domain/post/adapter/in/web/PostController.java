package wave.domain.post.adapter.in.web;

import static org.springframework.data.domain.Sort.Direction.*;
import static org.springframework.http.MediaType.*;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.post.application.PostService;
import wave.domain.post.dto.MyMusicPostDto;
import wave.domain.post.dto.PostDeleteDto;
import wave.domain.post.dto.SharedMusicPostDto;
import wave.domain.post.dto.request.MyMusicPostCreateRequest;
import wave.domain.post.dto.request.SharedMusicPostCreateRequest;
import wave.domain.post.dto.response.PostCreateResponse;
import wave.domain.post.dto.response.PostDeleteResponse;
import wave.domain.post.dto.response.PostsResponse;
import wave.global.aop.AuthenticationUser;
import wave.global.common.WebAdapter;
import wave.global.utils.WebUtils;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
@WebAdapter
public class PostController {

	private final PostService postService;

	@PostMapping(path = "/my-music",
		consumes = MULTIPART_FORM_DATA_VALUE,
		produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<PostCreateResponse> postNewMyMusic(
		@RequestPart MyMusicPostCreateRequest request,
		@RequestPart MultipartFile imageFile,
		@RequestPart MultipartFile musicFile,
		@AuthenticationUser User user
	) {
		MyMusicPostDto myMusicPostDto = MyMusicPostDto.of(request, imageFile, musicFile, user);
		PostCreateResponse response = postService.createMyMusicPost(myMusicPostDto);
		URI uri = getCreatedPostPath(response.postId());

		return ResponseEntity
			.created(uri)
			.body(response);
	}

	@PostMapping("/shared-music")
	public ResponseEntity<PostCreateResponse> createOwnMusicPost(
		@RequestBody SharedMusicPostCreateRequest request,
		@AuthenticationUser User user
	) {
		SharedMusicPostDto sharedMusicPostDto = SharedMusicPostDto.from(request, user);
		PostCreateResponse response = postService.createSharedMusicPost(sharedMusicPostDto);
		URI uri = getCreatedPostPath(response.postId());

		return ResponseEntity
			.created(uri)
			.body(response);
	}

	@GetMapping
	public ResponseEntity<PostsResponse> getAllPostsByCreatedDateDesc(
		@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable
	) {
		PostsResponse response = postService.getAllPostsByCreatedDateAndOrderByDesc(pageable);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{email}")
	public ResponseEntity<PostsResponse> getAllPostsByUser(
		@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable,
		@PathVariable String email
	) {
		PostsResponse response = postService.getPostsByUserEmail(pageable, email);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<PostDeleteResponse> deletePost(
		@PathVariable long postId,
		@AuthenticationUser User user
	) {
		PostDeleteDto request = new PostDeleteDto(postId, user);
		PostDeleteResponse postDeleteResponse = postService.deletePost(request);

		return ResponseEntity.ok(postDeleteResponse);
	}

	private URI getCreatedPostPath(long postId) {
		return WebUtils.createUri("http",
			"localhost:8080",
			"/api/posts/" + postId);
	}
}
