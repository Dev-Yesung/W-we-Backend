package wave.domain.post.adapter.web;

import static org.springframework.http.MediaType.*;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import wave.domain.post.application.PostService;
import wave.domain.post.dto.MyMusicPostDto;
import wave.domain.post.dto.request.MyMusicPostCreateRequest;
import wave.domain.post.dto.response.PostCreateResponse;
import wave.domain.account.domain.entity.User;
import wave.global.aop.AuthenticationUser;
import wave.global.common.WebAdapter;
import wave.global.utils.UriUtils;

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
		URI uri = UriUtils.createUri("http",
			"localhost:8080",
			"/api/posts/" + response.postId());

		return ResponseEntity
			.created(uri)
			.body(response);
	}

	// @GetMapping
	// public ResponseEntity<PostSliceResponse> getPostByCreatedDateDesc(
	// 	@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable
	// ) {
	// 	PostSliceResponse response = postService.getPostByCreatedDateDesc(pageable);
	//
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @GetMapping("/{email}")
	// public ResponseEntity<GetPostsByEmailResponse> getPostsByUserEmail(
	// 	@PathVariable String email
	// ) {
	// 	GetPostsByEmailRequest request = new GetPostsByEmailRequest(email);
	// 	GetPostsByEmailResponse response = postService.getPostsByUserEmail(request);
	//
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @PostMapping("/other-music")
	// public ResponseEntity<OtherMusicPostCreateResponse> createOwnMusicPost(
	// 	@RequestBody @Validated OtherMusicPostCreateRequest request,
	// 	@AuthenticationUser User user
	// ) {
	// 	OtherMusicDto otherMusicDto = OtherMusicDto.of(request, user);
	// 	OtherMusicPostCreateResponse response = postService.createOtherMusicPost(otherMusicDto);
	//
	// 	return ResponseEntity
	// 		.created(URI.create("/other-music" + "/" + response.postId()))
	// 		.body(response);
	// }
	//
	// @DeleteMapping("/{postId}")
	// public ResponseEntity<PostDeleteDto> deletePost(
	// 	@PathVariable long postId,
	// 	@AuthenticationUser User user
	// ) {
	// 	PostDeleteDto request = PostDeleteDto.of(postId, user);
	// 	PostDeleteDto response = postService.deletePost(request);
	//
	// 	return ResponseEntity
	// 		.ok(response);
	// }
	//
	// @PutMapping("/{postId}/likes")
	// public ResponseEntity<LikeUpdateResponse> updateLikes(
	// 	@PathVariable long postId,
	// 	@AuthenticationUser User user
	// ) {
	// 	LikeUpdateRequest likeUpdateRequest = new LikeUpdateRequest(postId, user);
	// 	LikeUpdateResponse response = postService.updateLikes(likeUpdateRequest);
	//
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @PostMapping("/{postId}/comments")
	// public ResponseEntity<CommentAddResponse> addComment(
	// 	@PathVariable long postId,
	// 	@RequestBody CommentAddRequest addRequest,
	// 	@AuthenticationUser User user
	// ) {
	// 	CommentAddDto commentAddDto = CommentAddDto.of(postId, addRequest, user);
	// 	CommentAddResponse response = postService.addComment(commentAddDto);
	//
	// 	return ResponseEntity
	// 		.created(URI.create("/" + postId + "/comments" + "/" + response.commentId()))
	// 		.body(response);
	// }
	//
	// @GetMapping("/{postId}/comments")
	// public ResponseEntity<CommentsSliceResponse> getCommentsByCreatedDateDesc(
	// 	@PathVariable long postId,
	// 	@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable
	// ) {
	// 	CommentsSliceResponse response = postService.getCommentsByCreatedDateDesc(postId, pageable);
	//
	// 	return ResponseEntity.ok(response);
	// }
	//
	// @DeleteMapping("/{postId}/comments/{commentId}")
	// public ResponseEntity<CommentDeleteDto> deleteComment(
	// 	@PathVariable long postId,
	// 	@PathVariable long commentId,
	// 	@AuthenticationUser User user
	// ) {
	// 	CommentDeleteDto request = CommentDeleteDto.of(postId, commentId, user);
	// 	CommentDeleteDto response = postService.deleteComment(request);
	//
	// 	return ResponseEntity.ok(response);
	// }
}
