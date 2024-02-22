package wave.domain.like.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wave.domain.like.domain.entity.Like;

public interface LikeJpaRepository extends JpaRepository<Like, Long> {

	@Query("SELECT l FROM Like AS l WHERE l.post.id = :postId AND l.user.id = :authorId")
	Optional<Like> findByPostIdAndUserId(@Param("postId") long postId, @Param("authorId") long userId);

	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM Like l WHERE l.post.id = :postId")
	void deleteAllByPostId(@Param("postId") Long postId);

}
