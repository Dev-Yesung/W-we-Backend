package wave.domain.post.application;

import static org.springframework.data.domain.Sort.Direction.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.media.dto.response.MediaUploadResponse;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.port.out.LoadPostPort;
import wave.domain.post.domain.port.out.PublishPostEventPort;
import wave.domain.post.domain.port.out.UpdatePostPort;
import wave.domain.post.dto.MyMusicPostDto;
import wave.domain.post.dto.SharedMusicDto;
import wave.domain.post.dto.response.PostCreateResponse;
import wave.domain.post.dto.response.PostsResponse;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class PostService {

	private final UpdatePostPort updatePostPort;
	private final LoadPostPort loadPostPort;
	private final PublishPostEventPort publishPostEventPort;

	public PostCreateResponse createMyMusicPost(MyMusicPostDto myMusicPostDto) {
		Post newPost = MyMusicPostDto.toPost(myMusicPostDto);
		Post savedPost = updatePostPort.saveNewPost(newPost);
		publishPostEventPort.publishMediaUploadEvent(savedPost, myMusicPostDto);

		return PostCreateResponse.of(savedPost);
	}

	@KafkaListener(topics = "media_event_result",
		groupId = "group_media_event_result",
		containerFactory = "kafkaListenerContainerFactory")
	public MediaUploadResponse subscribeMediaFileUploadMessage(Object message) {
		// 업로드 완료 메시지를 전달해야 하기 때문에, SSE 사용 필요할 듯?
		MediaUploadResponse response = (MediaUploadResponse)message;
		updatePostPort.updateMusicUploadUrl(response);

		return response;
	}

	public PostCreateResponse createOtherMusicPost(SharedMusicDto sharedMusicDto) {
		Post post = SharedMusicDto.toEntity(sharedMusicDto);
		Post savedPost = updatePostPort.saveNewPost(post);

		return PostCreateResponse.of(savedPost);
	}

	@Transactional(readOnly = true)
	public PostsResponse getAllPostsByCreatedDateDesc() {
		Pageable pageable = getDefaultSlicePageable();
		Slice<Post> allPosts = loadPostPort.getAllPostsByCreatedDateDesc(pageable);

		return PostsResponse.of(allPosts);
	}

	@Transactional(readOnly = true)
	public PostsResponse getPostsByUserEmail(String email) {
		Pageable pageable = getDefaultSlicePageable();
		Slice<Post> allPosts = loadPostPort.getAllPostsByEmailAndCreatedDateDesc(email, pageable);

		return PostsResponse.of(allPosts);
	}

	private Pageable getDefaultSlicePageable() {
		return PageRequest.of(0, 10, Sort.by(DESC, "createdAt"));
	}

	//
	// public PostDeleteDto deletePost(PostDeleteDto request) {
	// 	Long postId = request.postId();
	// 	Long userId = request.userId();
	//
	// 	Post post = postRepository.findById(postId)
	// 		.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));
	// 	User user = userRepository.findById(userId)
	// 		.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_USER));
	// 	post.validateAuthority(user);
	//
	// 	if(post.isOwnMusic()) {
	// 		MusicDeleteDto musicDeleteDto = new MusicDeleteDto(userId, postId);
	// 		musicUploadService.deleteMusic(musicDeleteDto);
	// 	}
	// 	commentRepository.deleteAllCommentsByPostId(postId);
	// 	postRepository.delete(post);
	//
	// 	return new PostDeleteDto(postId, userId);
	// }
	//
	// public LikeUpdateResponse updateLikes(LikeUpdateRequest request) {
	// 	long postId = request.postId();
	// 	Post post = postRepository.findById(postId)
	// 		.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));
	// 	User user = request.user();
	// 	post.updateLikes(user);
	//
	// 	String email = user.getEmail();
	// 	String nickname = user.getNickname();
	//
	// 	return new LikeUpdateResponse(postId, email, nickname);
	// }
	//
	// public CommentAddResponse addComment(CommentAddDto request) {
	// 	long postId = request.postId();
	// 	postRepository.findById(postId)
	// 		.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));
	// 	String comment = request.comment();
	// 	Long userId = request.userId();
	// 	String email = request.email();
	// 	String nickname = request.nickname();
	//
	// 	Comment newComment = new Comment(postId, userId, comment, email, nickname);
	// 	Comment savedComment = commentRepository.save(newComment);
	// 	Long commentId = savedComment.getId();
	//
	// 	return new CommentAddResponse(commentId, comment, userId, email, nickname);
	// }
	//
	// @Transactional(readOnly = true)
	// public CommentsSliceResponse getCommentsByCreatedDateDesc(long postId, Pageable pageable) {
	// 	Slice<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedDateAtDesc(postId, pageable);
	//
	// 	return CommentsSliceResponse.of(comments);
	// }
	//
	// public CommentDeleteDto deleteComment(CommentDeleteDto request) {
	// 	long commentId = request.commentId();
	// 	Comment comment = commentRepository.findById(commentId)
	// 		.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_COMMENT));
	//
	// 	long postId = request.postId();
	// 	Long userId = request.userId();
	// 	comment.validateAuthority(postId, userId);
	//
	// 	commentRepository.delete(comment);
	//
	// 	return new CommentDeleteDto(commentId, postId, userId);
	// }
	//
	// private UploadMusicDto getUploadMusicDto(MyMusicDto myMusicDto, Post post) {
	// 	User user = myMusicDto.user();
	// 	Long userId = user.getId();
	// 	Long postId = post.getId();
	// 	MultipartFile ownMusicFile = myMusicDto.musicFile();
	//
	// 	return new UploadMusicDto(userId, postId, ownMusicFile);
	// }
	//
	// private void updatePostUrl(UploadMusicResponse response, Post post) {
	// 	String musicUrl = response.musicUrl();
	// 	post.updateUrl(musicUrl);
	// }
}
