package wave.domain.post.domain.port.out.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import wave.domain.post.domain.entity.Post;

public interface PostQueryRepository {

	Slice<Post> findAllOrderByCreatedDateDesc(Pageable pageable);

	Slice<Post> findAllByEmailAndCreatedDateDesc(String email, Pageable pageable);

	Slice<Post> findAllByArtistNameOrderByLikeSizeDesc(String artistName, Pageable pageable);

	Slice<Post> findAllBySongTitle(String title, Pageable pageable);

}
