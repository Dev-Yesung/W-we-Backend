package wave.domain.post.domain.port.out;

import wave.domain.media.dto.response.MediaUploadResponse;
import wave.domain.post.domain.entity.Post;

public interface UpdatePostPort {

	Post saveNewPost(Post post);

	void updateMusicUploadData(MediaUploadResponse response);
}
