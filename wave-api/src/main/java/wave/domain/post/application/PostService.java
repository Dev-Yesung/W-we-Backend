package wave.domain.post.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import wave.domain.comment.domain.Comment;
import wave.domain.comment.infra.CommentRepository;
import wave.domain.music.application.MusicUploadService;
import wave.domain.music.dto.UploadMusicDto;
import wave.domain.music.dto.response.UploadMusicResponse;
import wave.domain.post.domain.Post;
import wave.domain.post.dto.CommentAddDto;
import wave.domain.post.dto.CommentDeleteDto;
import wave.domain.post.dto.MusicDeleteDto;
import wave.domain.post.dto.OtherMusicDto;
import wave.domain.post.dto.OwnMusicDto;
import wave.domain.post.dto.PostDeleteDto;
import wave.domain.post.dto.request.GetPostsByEmailRequest;
import wave.domain.post.dto.request.LikeUpdateRequest;
import wave.domain.post.dto.response.CommentAddResponse;
import wave.domain.post.dto.response.CommentsSliceResponse;
import wave.domain.post.dto.response.GetPostsByEmailResponse;
import wave.domain.post.dto.response.LikeUpdateResponse;
import wave.domain.post.dto.response.OtherMusicPostCreateResponse;
import wave.domain.post.dto.response.OwnMusicPostCreateResponse;
import wave.domain.post.dto.response.PostSliceResponse;
import wave.domain.post.infra.PostQueryRepository;
import wave.domain.post.infra.PostRepository;
import wave.domain.user.domain.User;
import wave.domain.user.infra.UserRepository;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
	private final MusicUploadService musicUploadService;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final PostQueryRepository postQueryRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public PostSliceResponse getPostByCreatedDateDesc(Pageable pageable) {
		Slice<Post> posts = postQueryRepository.getPostByCreatedDateDesc(pageable);

		return PostSliceResponse.of(posts);
	}

	@Transactional(readOnly = true)
	public GetPostsByEmailResponse getPostsByUserEmail(GetPostsByEmailRequest request) {
		return null;
	}

	public OwnMusicPostCreateResponse createOwnMusicPost(OwnMusicDto ownMusicDto) {
		Post newPost = ownMusicDto.toEntity();
		Post savedPost = postRepository.save(newPost);

		UploadMusicDto uploadMusicDto = getUploadMusicDto(ownMusicDto, savedPost);
		UploadMusicResponse response = musicUploadService.uploadMusic(uploadMusicDto);
		updatePostUrl(response, savedPost);

		return OwnMusicPostCreateResponse.of(savedPost);
	}

	public OtherMusicPostCreateResponse createOtherMusicPost(OtherMusicDto otherMusicDto) {
		Post newPost = otherMusicDto.toEntity();
		Post savedPost = postRepository.save(newPost);

		return OtherMusicPostCreateResponse.of(savedPost);
	}

	public PostDeleteDto deletePost(PostDeleteDto request) {
		Long postId = request.postId();
		Long userId = request.userId();

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_USER));
		post.validateAuthority(user);

		if(post.isOwnMusic()) {
			MusicDeleteDto musicDeleteDto = new MusicDeleteDto(userId, postId);
			musicUploadService.deleteMusic(musicDeleteDto);
		}

		postRepository.delete(post);

		return new PostDeleteDto(postId, userId);
	}

	public LikeUpdateResponse updateLikes(LikeUpdateRequest request) {
		long postId = request.postId();
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));
		User user = request.user();
		post.updateLikes(user);

		String email = user.getEmail();
		String nickname = user.getNickname();

		return new LikeUpdateResponse(postId, email, nickname);
	}

	public CommentAddResponse addComment(CommentAddDto request) {
		long postId = request.postId();
		postRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));
		String comment = request.comment();
		Long userId = request.userId();
		String email = request.email();
		String nickname = request.nickname();

		Comment newComment = new Comment(postId, userId, comment, email, nickname);
		Comment savedComment = commentRepository.save(newComment);
		Long commentId = savedComment.getId();

		return new CommentAddResponse(commentId, comment, userId, email, nickname);
	}

	@Transactional(readOnly = true)
	public CommentsSliceResponse getCommentsByCreatedDateDesc(long postId, Pageable pageable) {
		Slice<Comment> comments = commentRepository.findAllByPostIdByCreatedDateAtDesc(postId, pageable);

		return CommentsSliceResponse.of(comments);
	}

	public CommentDeleteDto deleteComment(CommentDeleteDto request) {
		long commentId = request.commentId();
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_COMMENT));

		long postId = request.postId();
		Long userId = request.userId();
		comment.validateAuthority(postId, userId);

		commentRepository.delete(comment);

		return new CommentDeleteDto(commentId, postId, userId);
	}

	private UploadMusicDto getUploadMusicDto(OwnMusicDto ownMusicDto, Post post) {
		User user = ownMusicDto.user();
		Long userId = user.getId();
		Long postId = post.getId();
		MultipartFile ownMusicFile = ownMusicDto.ownMusic();

		return new UploadMusicDto(userId, postId, ownMusicFile);
	}

	private void updatePostUrl(UploadMusicResponse response, Post post) {
		String url = response.url();
		post.updateUrl(url);
	}
}
