package wave.domain.post.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.account.infra.AccountJpaRepository;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.media.dto.response.MediaUploadResponse;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.port.out.LoadPostPort;
import wave.domain.post.domain.port.out.UpdatePostPort;
import wave.domain.post.infra.CommentJpaRepository;
import wave.domain.post.infra.PostQueryRepository;
import wave.domain.post.infra.PostJpaRepository;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@PersistenceAdapter
public class PostPersistenceAdapter
	implements UpdatePostPort, LoadPostPort {

	private final PostJpaRepository postJpaRepository;
	private final PostQueryRepository postQueryRepository;
	private final CommentJpaRepository commentJpaRepository;
	private final AccountJpaRepository accountJpaRepository;

	@Override
	public Post saveNewPost(Post post) {
		return postJpaRepository.save(post);
	}

	@Override
	public void updateMusicUploadData(MediaUploadResponse response) {
		Long postId = response.postId();
		Post post = postJpaRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));

		MediaUrl mediaUrl = MediaUploadResponse.of(response);
		post.updateMediaUrl(mediaUrl);
	}
}
