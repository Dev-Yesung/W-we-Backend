package wave.domain.post.adapter.out;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.comment.dto.CommentAddDto;
import wave.domain.comment.dto.CommentDeleteDto;
import wave.domain.comment.dto.response.CommentAddResponse;
import wave.domain.comment.entity.Comment;
import wave.domain.like.dto.request.LikeUpdateRequest;
import wave.domain.like.dto.response.LikeUpdateResponse;
import wave.domain.like.entity.Like;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.media.dto.MediaUrlUpdateMessage;
import wave.domain.notification.adapter.out.persistence.NotificationJpaRepository;
import wave.domain.notification.entity.Notification;
import wave.domain.post.adapter.out.persistence.CommentJpaRepository;
import wave.domain.post.adapter.out.persistence.LikeJpaRepository;
import wave.domain.post.adapter.out.persistence.PostJpaRepository;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.port.out.LoadPostPort;
import wave.domain.post.domain.port.out.UpdatePostPort;
import wave.domain.post.domain.port.out.persistence.PostQueryRepository;
import wave.domain.post.domain.vo.PostContent;
import wave.domain.post.dto.PostDeleteDto;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@PersistenceAdapter
public class PostPersistenceAdapter implements UpdatePostPort, LoadPostPort {

	private final PostJpaRepository postJpaRepository;
	private final PostQueryRepository postQueryRepository;
	private final CommentJpaRepository commentJpaRepository;
	private final LikeJpaRepository likeJpaRepository;
	private final NotificationJpaRepository notificationJpaRepository;

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
	public Like updateLikes(final LikeUpdateRequest likeUpdateRequest) {
		final long postId = likeUpdateRequest.postId();
		final User user = likeUpdateRequest.user();

		Optional<Like> optionalLike = likeJpaRepository.findByPostIdAndUserId(postId, user.getId());
		optionalLike.ifPresent(likeJpaRepository::delete);

		return optionalLike.orElseGet(() -> likeJpaRepository.save(new Like(postId, user)));
	}

	@Override
	public Post deletePost(final PostDeleteDto request) {
		Long postId = request.postId();
		Post post = postJpaRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));

		User user = request.user();
		post.isAuthor(user);

		likeJpaRepository.deleteAllByPostId(postId);
		commentJpaRepository.deleteAllByPostId(postId);
		postJpaRepository.delete(post);

		return post;
	}

	@Override
	public CommentAddResponse addComment(final CommentAddDto commentAddDto) {
		final long postId = commentAddDto.postId();
		if (!postJpaRepository.existsById(postId)) {
			throw new EntityException(ErrorCode.NOT_FOUND_POST);
		}

		User user = commentAddDto.user();
		String comment = commentAddDto.comment();
		String email = commentAddDto.email();
		String nickname = commentAddDto.nickname();
		Comment newComment = new Comment(postId, user, comment, email, nickname);
		Comment savedComment = commentJpaRepository.save(newComment);
		Long commentId = savedComment.getId();

		return new CommentAddResponse(commentId, user.getId());
	}

	@Override
	public Slice<Comment> getAllComments(long postId, Pageable pageable) {
		return commentJpaRepository.findAllByPostIdOrderBy(postId, pageable);
	}

	@Override
	public void deleteComment(CommentDeleteDto request) {
		long commentId = request.commentId();
		Comment comment = commentJpaRepository.findById(commentId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_COMMENT));
		User user = request.user();
		comment.isSameWriter(user);
		commentJpaRepository.delete(comment);
	}

	@Override
	public Slice<Post> getAllPostsByCreatedDateAndOrderByDesc(Pageable pageable) {
		return postQueryRepository.getPostByCreatedDateDesc(pageable);
	}

	@Override
	public Slice<Post> getAllPostsByEmailAndCreatedDateDesc(String email, Pageable pageable) {
		return postQueryRepository.getAllPostsByEmailAndCreatedDateDesc(email, pageable);
	}

	private Notification saveNewPostNotification(Post savedPost) {
		Long postId = savedPost.getId();
		Long userId = savedPost.getUser().getId();
		PostContent postContent = savedPost.getPostContent();
		String title = postContent.getTitle();
		String descriptions = postContent.getDescriptions();
		Notification notification = new Notification(userId, postId, title, descriptions);

		return notificationJpaRepository.save(notification);
	}

}
