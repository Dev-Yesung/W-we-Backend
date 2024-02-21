package wave.domain.like.domain.port;

import wave.domain.like.dto.LikeUpdateInfo;
import wave.domain.like.dto.request.LikeUpdateRequest;
import wave.domain.post.dto.response.PostDeleteResponse;

public interface UpdateLikePort {

	LikeUpdateInfo updateLikes(LikeUpdateRequest likeUpdateRequest);

	void deleteAllByPostId(PostDeleteResponse message);

}
