package wave.domain.post.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.post.domain.entity.Post;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
