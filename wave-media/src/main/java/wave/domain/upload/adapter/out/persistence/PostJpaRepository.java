package wave.domain.upload.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.post.domain.entity.Post;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

}
