package wave.domain.post.adapter.out.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wave.domain.comment.entity.Comment;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

	@Query("SELECT c FROM Comment AS c WHERE c.postId = :postId")
	Slice<Comment> findAllByPostIdOrderBy(@Param("postId") long postId, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM Comment AS c WHERE c.postId = :postId")
	void deleteAllByPostId(@Param("postId") Long postId);

}
