package wave.domain.comment.infra;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wave.domain.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query("SELECT c FROM comments AS c WHERE c.postId = :postId")
	Slice<Comment> findAllByPostIdOrderByCreatedDateAtDesc(@Param("postId") long postId, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM comments AS c WHERE c.postId = :postId")
	void deleteAllCommentsByPostId(@Param("postId") Long postId);
}
