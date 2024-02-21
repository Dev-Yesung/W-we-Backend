package wave.domain.post.adapter.out;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.media.dto.MediaUrlUpdateMessage;
import wave.domain.post.adapter.out.persistence.PostJpaRepository;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.port.out.LoadPostPort;
import wave.domain.post.domain.port.out.UpdatePostPort;
import wave.domain.post.domain.port.out.persistence.PostQueryRepository;
import wave.domain.post.dto.PostDeleteDto;
import wave.domain.post.dto.response.PostResponse;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@PersistenceAdapter
public class PostPersistenceAdapter implements UpdatePostPort, LoadPostPort {

	private final PostJpaRepository postJpaRepository;
	private final PostQueryRepository postQueryRepository;

	@Override
	public Post saveNewPost(final Post post) {
		return postJpaRepository.save(post);
	}

	@Override
	public void updateMusicUploadUrl(final MediaUrlUpdateMessage message) {
		Long postId = message.fileId().getPostId();
		Post post = postJpaRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));

		MediaUrl mediaUrl = message.mediaUrl();
		post.updateMediaUrl(mediaUrl);
	}

	@Override
	public Post deletePost(final PostDeleteDto request) {
		Long postId = request.postId();
		Post post = postJpaRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));

		User user = request.user();
		post.isAuthor(user);

		postJpaRepository.delete(post);

		return post;
	}

	@Override
	public Slice<Post> findAllByCreatedDateOrderByDesc(Pageable pageable) {
		return postQueryRepository.findAllOrderByCreatedDateDesc(pageable);
	}

	@Override
	public Slice<Post> findAllByEmailOrderByCreatedDateDesc(String email, Pageable pageable) {
		return postQueryRepository.findAllByEmailAndCreatedDateDesc(email, pageable);
	}

	@Override
	public Slice<PostResponse> findAllByArtistNameOrderByLikeSizeDesc(String artistName, Pageable pageable) {
		Slice<Post> posts = postQueryRepository
			.findAllByArtistNameOrderByLikeSizeDesc(artistName, pageable);
		List<PostResponse> postResponses = posts.stream()
			.map(PostResponse::of)
			.toList();

		return new SliceImpl<>(postResponses, posts.getPageable(), posts.hasNext());
	}

	@Override
	public Slice<PostResponse> findAllBySongTitle(String title, Pageable pageable) {
		Slice<Post> posts = postQueryRepository.findAllBySongTitle(title, pageable);
		List<PostResponse> postResponses = posts.stream()
			.map(PostResponse::of)
			.toList();

		return new SliceImpl<>(postResponses, posts.getPageable(), posts.hasNext());
	}

}
