package wave.domain.post.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.media.dto.MediaFileUploadMessage;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.port.out.PublishPostEventPort;
import wave.domain.post.dto.MyMusicPostDto;
import wave.domain.post.infra.PostEventBroker;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class PostEventAdapter implements PublishPostEventPort {

	private final PostEventBroker postEventBroker;

	@Override
	public void publishMusicUploadEvent(Post post, MyMusicPostDto myMusicPostDto) {
		MediaFileUploadMessage mediaFileUploadMessage = MediaFileUploadMessage.of(post, myMusicPostDto);
		postEventBroker.publishMusicUploadEvent(mediaFileUploadMessage);
	}
}
