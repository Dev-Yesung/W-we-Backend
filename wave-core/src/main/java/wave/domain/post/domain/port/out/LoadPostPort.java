package wave.domain.post.domain.port.out;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import wave.domain.post.domain.entity.Post;

public interface LoadPostPort {

	Slice<Post> getAllPosts(Pageable pageable);

	Slice<Post> getAllPostsByEmail(String email, Pageable pageable);
}
