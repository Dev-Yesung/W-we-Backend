package wave.domain.post.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
