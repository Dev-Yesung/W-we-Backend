package wave.domain.post.infra;

import static wave.domain.post.domain.entity.QPost.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import wave.domain.post.domain.entity.Post;

@Repository
public class QueryDslPostQueryRepository implements PostQueryRepository {

	private final JPAQueryFactory queryFactory;

	public QueryDslPostQueryRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Slice<Post> getPostByCreatedDateDesc(Pageable pageable) {
		long offset = pageable.getOffset();
		int pageSize = pageable.getPageSize();

		List<Post> posts = queryFactory
			.selectFrom(post)
			.orderBy(post.createdAt.desc())
			.offset(offset)
			.limit(pageSize + 1)
			.fetch();
		boolean hasNextPage = hasNextPage(posts, pageSize);

		return new SliceImpl<>(posts, pageable, hasNextPage);
	}

	@Override
	public Slice<Post> getAllPostsByEmailAndCreatedDateDesc(String email, Pageable pageable) {
		long offset = pageable.getOffset();
		int pageSize = pageable.getPageSize();

		List<Post> posts = queryFactory
			.selectFrom(post)
			.where(post.user.email.eq(email))
			.orderBy(post.createdAt.desc())
			.offset(offset)
			.limit(pageSize + 1)
			.fetch();
		boolean hasNextPage = hasNextPage(posts, pageSize);

		return new SliceImpl<>(posts, pageable, hasNextPage);
	}

	private boolean hasNextPage(List<Post> posts, int pageSize) {
		boolean hasNextPage = posts.size() > pageSize;
		if (hasNextPage) {
			posts.remove(pageSize);
		}

		return hasNextPage;
	}
}
