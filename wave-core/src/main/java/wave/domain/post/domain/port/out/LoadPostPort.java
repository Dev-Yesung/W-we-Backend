package wave.domain.post.domain.port.out;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import wave.domain.post.domain.entity.Post;
import wave.domain.post.dto.response.PostResponse;

public interface LoadPostPort {

	Slice<Post> findAllByCreatedDateOrderByDesc(Pageable pageable);

	Slice<Post> findAllByEmailOrderByCreatedDateDesc(String email, Pageable pageable);

	Slice<PostResponse>  findAllByArtistNameOrderByLikeSizeDesc(String artistName, Pageable pageable);

	Slice<PostResponse> findAllBySongTitle(String title, Pageable pageable);

}
