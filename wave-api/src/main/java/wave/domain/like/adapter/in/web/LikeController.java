package wave.domain.like.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.like.application.LikeService;
import wave.domain.like.dto.request.LikeUpdateRequest;
import wave.domain.like.dto.response.LikeUpdateResponse;
import wave.global.aop.AuthenticationUser;
import wave.global.common.WebAdapter;

@RequiredArgsConstructor
@RequestMapping("/api/likes")
@RestController
@WebAdapter
public class LikeController {

	private final LikeService likeService;

	@PutMapping("/{likeId}")
	public ResponseEntity<LikeUpdateResponse> updateLikes(
		@PathVariable long likeId,
		@AuthenticationUser User user
	) {
		LikeUpdateRequest likeUpdateRequest = new LikeUpdateRequest(likeId, user);
		LikeUpdateResponse response = likeService.updateLikes(likeUpdateRequest);

		return ResponseEntity.ok(response);
	}
}
