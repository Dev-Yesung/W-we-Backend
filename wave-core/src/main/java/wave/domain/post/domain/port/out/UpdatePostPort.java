package wave.domain.post.domain.port.out;

import wave.domain.comment.domain.entity.Comment;
import wave.domain.comment.dto.CommentAddDto;
import wave.domain.comment.dto.CommentDeleteDto;
import wave.domain.like.domain.entity.Like;
import wave.domain.like.dto.request.LikeUpdateRequest;
import wave.domain.media.dto.MediaUrlUpdateMessage;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.dto.PostDeleteDto;

public interface UpdatePostPort {

	Post saveNewPost(Post post);

	void updateMusicUploadUrl(MediaUrlUpdateMessage message);

	Post deletePost(PostDeleteDto request);

}
