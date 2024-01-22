package wave.domain.post.infra;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import wave.domain.post.domain.entity.Post;

public interface PostQueryRepository {
	Slice<Post> getPostByCreatedDateDesc(Pageable pageable);
}
