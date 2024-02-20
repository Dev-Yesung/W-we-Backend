package wave.domain.post.adapter.out;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Image;
import wave.domain.media.domain.vo.Music;
import wave.domain.media.dto.MediaFileUploadMessage;
import wave.domain.notification.dto.CommonNotificationMessage;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.port.out.PublishPostEventPort;
import wave.domain.post.domain.port.out.broker.PostEventBroker;
import wave.domain.post.dto.MyMusicPostDto;
import wave.domain.post.dto.response.PostDeleteResponse;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class PostEventAdapter implements PublishPostEventPort {

	private final PostEventBroker postEventBroker;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void publishMediaUploadEvent(Post post, MyMusicPostDto myMusicPostDto) {
		FileId fileId = post.getFileId();
		Image image = MyMusicPostDto.toImage(myMusicPostDto);
		Music music = MyMusicPostDto.toMusic(myMusicPostDto);
		MediaFileUploadMessage mediaFileUploadMessage = new MediaFileUploadMessage(fileId, image, music);

		postEventBroker.publishMessage("media_file_upload", mediaFileUploadMessage);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void publishNewSharedMusicPostEvent(Post post) {
		Long userId = post.getUser().getId();
		Long postId = post.getId();
		CommonNotificationMessage message = new CommonNotificationMessage(userId, postId);
		postEventBroker.publishMessage("new_post_message", message);
	}

	@Override
	public void publishDeletePostEvent(Post post) {
		PostDeleteResponse postDeleteResponse = PostDeleteResponse.of(post);
		applicationEventPublisher.publishEvent(postDeleteResponse);

		Long userId = post.getUser().getId();
		Long postId = post.getId();
		CommonNotificationMessage message = new CommonNotificationMessage(userId, postId);
		postEventBroker.publishMessage("delete_post_message", message);
	}

}
