package wave.domain.post.domain.port.out;

import wave.domain.post.domain.entity.Post;
import wave.domain.post.dto.MyMusicPostDto;

public interface PublishPostEventPort {

	void publishMediaUploadEvent(Post savedPost, MyMusicPostDto myMusicPostDto);

}
