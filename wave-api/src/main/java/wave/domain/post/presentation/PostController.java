package wave.domain.post.presentation;

import static org.springframework.data.domain.Sort.Direction.*;
import static org.springframework.http.MediaType.*;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import wave.domain.post.application.PostService;
import wave.domain.post.dto.CommentAddDto;
import wave.domain.post.dto.OtherMusicDto;
import wave.domain.post.dto.OwnMusicDto;
import wave.domain.post.dto.PostDeleteDto;
import wave.domain.post.dto.request.CommentAddRequest;
import wave.domain.post.dto.CommentDeleteDto;
import wave.domain.post.dto.request.GetPostsByEmailRequest;
import wave.domain.post.dto.request.LikeUpdateRequest;
import wave.domain.post.dto.request.OtherMusicPostCreateRequest;
import wave.domain.post.dto.request.OwnMusicPostCreateRequest;
import wave.domain.post.dto.response.CommentAddResponse;
import wave.domain.post.dto.response.CommentsSliceResponse;
import wave.domain.post.dto.response.GetPostsByEmailResponse;
import wave.domain.post.dto.response.LikeUpdateResponse;
import wave.domain.post.dto.response.OtherMusicPostCreateResponse;
import wave.domain.post.dto.response.OwnMusicPostCreateResponse;
import wave.domain.post.dto.response.PostSliceResponse;
import wave.domain.user.domain.User;
import wave.global.aop.AuthenticationUser;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {
	private final PostService postService;

	@GetMapping
	public ResponseEntity<PostSliceResponse> getPostByCreatedDateDesc(
		@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable
	) {
		PostSliceResponse response = postService.getPostByCreatedDateDesc(pageable);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{email}")
	public ResponseEntity<GetPostsByEmailResponse> getPostsByUserEmail(
		@PathVariable String email
	) {
		GetPostsByEmailRequest request = new GetPostsByEmailRequest(email);
		GetPostsByEmailResponse response = postService.getPostsByUserEmail(request);

		return ResponseEntity.ok(response);
	}

	@PostMapping(path = "/own-music", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<OwnMusicPostCreateResponse> createOwnMusicPost(
		@RequestPart @Validated OwnMusicPostCreateRequest request,
		@RequestPart MultipartFile ownMusic,
		@AuthenticationUser User user
	) {
		OwnMusicDto ownMusicDto = OwnMusicDto.of(request, ownMusic, user);
		OwnMusicPostCreateResponse response = postService.createOwnMusicPost(ownMusicDto);

		return ResponseEntity
			.created(URI.create("/own-music" + "/" + response.postId()))
			.body(response);
	}

	@PostMapping("/other-music")
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

	@DeleteMapping("/{postId}")
	public ResponseEntity<PostDeleteDto> deletePost(
		@PathVariable long postId,
		@AuthenticationUser User user
	) {
		PostDeleteDto request = PostDeleteDto.of(postId, user);
		PostDeleteDto response = postService.deletePost(request);

		return ResponseEntity
			.ok(response);
	}

	@PutMapping("/{postId}/likes")
	public ResponseEntity<LikeUpdateResponse> updateLikes(
		@PathVariable long postId,
		@AuthenticationUser User user
	) {
		LikeUpdateRequest likeUpdateRequest = new LikeUpdateRequest(postId, user);
		LikeUpdateResponse response = postService.updateLikes(likeUpdateRequest);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/{postId}/comments")
	public ResponseEntity<CommentAddResponse> addComment(
		@PathVariable long postId,
		@RequestBody CommentAddRequest addRequest,
		@AuthenticationUser User user
	) {
		CommentAddDto commentAddDto = CommentAddDto.of(postId, addRequest, user);
		CommentAddResponse response = postService.addComment(commentAddDto);

		return ResponseEntity
			.created(URI.create("/" + postId + "/comments" + "/" + response.commentId()))
			.body(response);
	}

	@GetMapping("/{postId}/comments")
	public ResponseEntity<CommentsSliceResponse> getCommentsByCreatedDateDesc(
		@PathVariable long postId,
		@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable
	) {
		CommentsSliceResponse response = postService.getCommentsByCreatedDateDesc(postId, pageable);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDeleteDto> deleteComment(
		@PathVariable long postId,
		@PathVariable long commentId,
		@AuthenticationUser User user
	) {
		CommentDeleteDto request = CommentDeleteDto.of(postId, commentId, user);
		CommentDeleteDto response = postService.deleteComment(request);

		return ResponseEntity.ok(response);
	}
}
