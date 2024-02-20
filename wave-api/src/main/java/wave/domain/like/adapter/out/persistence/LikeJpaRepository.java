package wave.domain.like.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wave.domain.like.domain.entity.Like;

public interface LikeJpaRepository extends JpaRepository<Like, Long> {

	@Query("SELECT l FROM Like AS l WHERE l.postId = :postId AND l.user.id = :userId")
	Optional<Like> findByPostIdAndUserId(@Param("postId") long postId, @Param("userId") long userId);

	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM Like l WHERE l.postId = :postId")
	void deleteAllByPostId(@Param("postId") Long postId);

}
