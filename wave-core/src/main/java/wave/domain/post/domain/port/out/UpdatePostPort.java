package wave.domain.post.domain.port.out;

import wave.domain.comment.dto.CommentAddDto;
import wave.domain.comment.dto.CommentDeleteDto;
import wave.domain.comment.dto.response.CommentAddResponse;
import wave.domain.like.dto.request.LikeUpdateRequest;
import wave.domain.like.dto.response.LikeUpdateResponse;
import wave.domain.like.entity.Like;
import wave.domain.media.dto.MediaUrlUpdateMessage;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.dto.PostDeleteDto;

public interface UpdatePostPort {

	Post saveNewPost(Post post);

	void updateMusicUploadUrl(MediaUrlUpdateMessage message);

	Like updateLikes(LikeUpdateRequest likeUpdateRequest);

	Post deletePost(PostDeleteDto request);

	CommentAddResponse addComment(CommentAddDto commentAddDto);

	void deleteComment(CommentDeleteDto request);

}
