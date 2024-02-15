package wave.domain.notification.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.post.domain.entity.Post;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

}
