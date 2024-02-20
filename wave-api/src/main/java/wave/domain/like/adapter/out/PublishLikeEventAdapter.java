package wave.domain.like.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.like.domain.entity.Like;
import wave.domain.like.domain.port.PublishLikeEventPort;
import wave.domain.like.domain.port.broker.LikeEventBroker;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class PublishLikeEventAdapter implements PublishLikeEventPort {

	private final LikeEventBroker likeEventBroker;

	@Override
	public void publishLikeUpdateEvent(Like like) {
		likeEventBroker.publishMessage("update_like_message", like);
	}

}
