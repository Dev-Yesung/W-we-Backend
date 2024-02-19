package wave.domain.post.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.comment.dto.CommentAddDto;
import wave.domain.comment.dto.CommentDeleteDto;
import wave.domain.comment.dto.response.CommentAddResponse;
import wave.domain.comment.dto.response.CommentsSliceResponse;
import wave.domain.comment.entity.Comment;
import wave.domain.like.dto.request.LikeUpdateRequest;
import wave.domain.like.dto.response.LikeUpdateResponse;
import wave.domain.like.entity.Like;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.port.out.LoadPostPort;
import wave.domain.post.domain.port.out.PublishPostEventPort;
import wave.domain.post.domain.port.out.UpdatePostPort;
import wave.domain.post.dto.MyMusicPostDto;
import wave.domain.post.dto.PostDeleteDto;
import wave.domain.post.dto.SharedMusicPostDto;
import wave.domain.post.dto.response.PostCreateResponse;
import wave.domain.post.dto.response.PostDeleteResponse;
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

	public PostCreateResponse createSharedMusicPost(SharedMusicPostDto sharedMusicPostDto) {
		Post post = SharedMusicPostDto.toEntity(sharedMusicPostDto);
		Post savedPost = updatePostPort.saveNewPost(post);
		publishPostEventPort.publishNewSharedMusicPostEvent(savedPost);

		return PostCreateResponse.of(savedPost);
	}

	@Transactional(readOnly = true)
	public PostsResponse getAllPostsByCreatedDateAndOrderByDesc(Pageable pageable) {
		Slice<Post> allPosts = loadPostPort.getAllPostsByCreatedDateAndOrderByDesc(pageable);

		return PostsResponse.of(allPosts);
	}

	@Transactional(readOnly = true)
	public PostsResponse getPostsByUserEmail(Pageable pageable, String email) {
		Slice<Post> allPosts = loadPostPort.getAllPostsByEmailAndCreatedDateDesc(email, pageable);

		return PostsResponse.of(allPosts);
	}

	public PostDeleteResponse deletePost(PostDeleteDto request) {
		Post deletePost = updatePostPort.deletePost(request);
		publishPostEventPort.publishDeletePostEvent(deletePost);

		return PostDeleteResponse.of(deletePost);
	}

	public LikeUpdateResponse updateLikes(LikeUpdateRequest likeUpdateRequest) {
		Like updatedLike = updatePostPort.updateLikes(likeUpdateRequest);
		// todo: Like 반환하는거 반환 안하도록 바꾸기
		// todo: 알림 메시지 이벤트

		return null;
	}

	public CommentAddResponse addComment(CommentAddDto commentAddDto) {
		CommentAddResponse commentAddResponse = updatePostPort.addComment(commentAddDto);
		// todo: 알림 메시지 이벤트
		return commentAddResponse;
	}

	@Transactional(readOnly = true)
	public CommentsSliceResponse getAllCommentsByCreatedDateAndOrderByDesc(long postId, Pageable pageable) {
		Slice<Comment> comments = loadPostPort.getAllComments(postId, pageable);

		return CommentsSliceResponse.of(comments);
	}

	public void deleteComment(CommentDeleteDto request) {
		updatePostPort.deleteComment(request);
	}
}
