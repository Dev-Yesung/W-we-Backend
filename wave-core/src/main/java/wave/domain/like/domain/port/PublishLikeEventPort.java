package wave.domain.like.domain.port;

import wave.domain.like.domain.entity.Like;

public interface PublishLikeEventPort {

	void publishLikeUpdateEvent(Like like);

}
