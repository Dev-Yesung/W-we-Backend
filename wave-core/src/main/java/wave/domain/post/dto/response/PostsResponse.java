package wave.domain.post.dto.response;

import java.util.List;

import org.springframework.data.domain.Slice;

import wave.domain.post.domain.entity.Post;

public record PostsResponse(
	List<PostResponse> posts,
	boolean hasNext
) {
	public static PostsResponse of(Slice<Post> posts) {
		List<PostResponse> postResponses = posts.stream()
			.map(PostResponse::from)
			.toList();
		boolean hasNext = posts.hasNext();

		return new PostsResponse(postResponses, hasNext);
	}
}
