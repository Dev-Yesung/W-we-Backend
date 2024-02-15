package wave.domain.post.domain.port.out;

import wave.domain.media.dto.MediaUrlUpdateMessage;
import wave.domain.post.domain.entity.Post;

public interface UpdatePostPort {

	Post saveNewPost(Post post);

	void updateMusicUploadUrl(MediaUrlUpdateMessage message);

}
