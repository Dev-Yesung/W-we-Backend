package wave.domain.search.dto;

import java.util.List;

import org.springframework.data.domain.Slice;

import wave.domain.post.dto.response.PostResponse;

public record PostSearchResponse(
	List<PostResponse> postResponses,
	boolean hasNext
) {

	public static PostSearchResponse of(Slice<PostResponse> posts) {
		List<PostResponse> postResponses = posts.getContent();
		boolean hasNext = posts.hasNext();

		return new PostSearchResponse(postResponses, hasNext);
	}

}
