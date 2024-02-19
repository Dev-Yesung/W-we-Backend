package wave.domain.post.domain.port.out;

import wave.domain.post.domain.entity.Post;
import wave.domain.post.dto.MyMusicPostDto;

public interface PublishPostEventPort {

	void publishMediaUploadEvent(Post post, MyMusicPostDto myMusicPostDto);

	void publishNewSharedMusicPostEvent(Post post);

	void publishDeletePostEvent(Post post);

}
