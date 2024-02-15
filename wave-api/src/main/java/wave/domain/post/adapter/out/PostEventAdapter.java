package wave.domain.post.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.vo.FileId;
import wave.domain.media.domain.vo.Image;
import wave.domain.media.domain.vo.Music;
import wave.domain.media.dto.MediaFileUploadMessage;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.port.out.broker.PostEventBroker;
import wave.domain.post.domain.port.out.PublishPostEventPort;
import wave.domain.post.dto.MyMusicPostDto;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class PostEventAdapter implements PublishPostEventPort {

	private final PostEventBroker postEventBroker;

	@Override
	public void publishMediaUploadEvent(Post post, MyMusicPostDto myMusicPostDto) {
		FileId fileId = post.getFileId();
		Image image = MyMusicPostDto.toImage(myMusicPostDto);
		Music music = MyMusicPostDto.toMusic(myMusicPostDto);
		MediaFileUploadMessage mediaFileUploadMessage = new MediaFileUploadMessage("media_file_upload", fileId, image, music);

		postEventBroker.publishMusicUploadEvent(mediaFileUploadMessage);
	}

}
