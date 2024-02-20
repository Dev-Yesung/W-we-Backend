package wave.domain.like.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wave.domain.like.domain.entity.Like;

public interface LikeJpaRepository extends JpaRepository<Like, Long> {

	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM Like l WHERE l.postId = :postId")
	void deleteAllByPostId(@Param("likeId") Long postId);

}
