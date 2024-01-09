package wave.domain.comment.infra;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wave.domain.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query("select c from comments as c where c.postId = :postId")
	Slice<Comment> findAllByPostIdByCreatedDateAtDesc(@Param("postId") long postId, Pageable pageable);
}
