package wave.domain.post.dto.response;

import java.util.List;

import org.springframework.data.domain.Slice;

import wave.domain.post.domain.entity.Post;

public record PostSliceResponse(
	List<PostResponse> posts,
	boolean hasNext
) {
	public static PostSliceResponse of(Slice<Post> posts) {
		List<PostResponse> postResponses = posts.stream()
			.map(PostResponse::of)
			.toList();
		boolean hasNext = posts.hasNext();

		return new PostSliceResponse(postResponses, hasNext);
	}
}
