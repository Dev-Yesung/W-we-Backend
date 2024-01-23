package wave.domain.post.domain.port.out;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import wave.domain.post.domain.entity.Post;

public interface LoadPostPort {

	Slice<Post> getAllPostsByCreatedDateDesc(Pageable pageable);

	Slice<Post> getAllPostsByEmailAndCreatedDateDesc(String email, Pageable pageable);
}
