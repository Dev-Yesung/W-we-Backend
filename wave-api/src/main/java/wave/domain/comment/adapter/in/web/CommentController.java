package wave.domain.comment.adapter.in.web;

import static org.springframework.data.domain.Sort.Direction.*;

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
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.comment.application.CommentService;
import wave.domain.comment.dto.CommentAddDto;
import wave.domain.comment.dto.CommentDeleteDto;
import wave.domain.comment.dto.request.CommentAddRequest;
import wave.domain.comment.dto.response.CommentAddResponse;
import wave.domain.comment.dto.response.CommentDeleteResponse;
import wave.domain.comment.dto.response.CommentsSliceResponse;
import wave.global.aop.AuthenticationUser;
import wave.global.common.WebAdapter;
import wave.global.utils.WebUtils;

@RequiredArgsConstructor
@RequestMapping("/api/comments")
@RestController
@WebAdapter
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/{postId}/comments")
	public ResponseEntity<CommentAddResponse> addComment(
		@PathVariable long postId,
		@RequestBody CommentAddRequest addRequest,
		@AuthenticationUser User user
	) {
		CommentAddDto commentAddDto = CommentAddDto.of(postId, addRequest, user);
		CommentAddResponse response = commentService.addComment(commentAddDto);
		URI uri = WebUtils.createUri("http",
			"localhost:8080",
			"/api/posts/" + postId + "/comments/" + response.commentId());

		return ResponseEntity
			.created(uri)
			.body(response);
	}

	@GetMapping("/{postId}/comments")
	public ResponseEntity<CommentsSliceResponse> getAllComments(
		@PathVariable long postId,
		@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable
	) {
		CommentsSliceResponse response = commentService.getAllCommentsByCreatedDateAndOrderByDesc(postId, pageable);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<CommentDeleteResponse> deleteComment(
		@PathVariable long commentId,
		@AuthenticationUser User user
	) {
		CommentDeleteDto request = new CommentDeleteDto(commentId, user);
		CommentDeleteResponse response = commentService.deleteComment(request);

		return ResponseEntity.ok(response);
	}

}
