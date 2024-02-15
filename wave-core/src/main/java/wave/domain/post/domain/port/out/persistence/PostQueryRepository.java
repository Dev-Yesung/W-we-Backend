package wave.domain.post.domain.port.out.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import wave.domain.post.domain.entity.Post;

public interface PostQueryRepository {
	Slice<Post> getPostByCreatedDateDesc(Pageable pageable);

	Slice<Post> getAllPostsByEmailAndCreatedDateDesc(String email, Pageable pageable);
}
