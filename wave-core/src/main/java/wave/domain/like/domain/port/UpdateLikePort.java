package wave.domain.like.domain.port;

import wave.domain.like.domain.entity.Like;
import wave.domain.like.dto.request.LikeUpdateRequest;
import wave.domain.post.dto.response.PostDeleteResponse;

public interface UpdateLikePort {

	Like updateLikes(LikeUpdateRequest likeUpdateRequest);

	void deleteAllByPostId(PostDeleteResponse message);

}
