package wave.domain.post.adapter.out.persistence;

import static wave.domain.like.domain.entity.QLike.*;
import static wave.domain.post.domain.entity.QPost.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.domain.port.out.persistence.PostQueryRepository;

@Repository
public class PostQueryDslRepository implements PostQueryRepository {

	private final JPAQueryFactory queryFactory;

	public PostQueryDslRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Slice<Post> findAllOrderByCreatedDateDesc(Pageable pageable) {
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
	public Slice<Post> findAllByEmailAndCreatedDateDesc(String email, Pageable pageable) {
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

	@Override
	public Slice<Post> findAllByArtistNameOrderByLikeSizeDesc(String artistName, Pageable pageable) {
		long offset = pageable.getOffset();
		int pageSize = pageable.getPageSize();

		List<Post> posts = queryFactory.selectFrom(post)
			.leftJoin(post.likes, like)
			.fetchJoin()
			.where(post.postContent.artistName.like("%" + artistName + "%"))
			.orderBy(post.likes.size().desc())
			.offset(offset)
			.limit(pageSize + 1)
			.fetch();
		boolean hasNextPage = hasNextPage(posts, pageSize);

		return new SliceImpl<>(posts, pageable, hasNextPage);
	}

	@Override
	public Slice<Post> findAllBySongTitle(String title, Pageable pageable) {
		long offset = pageable.getOffset();
		int pageSize = pageable.getPageSize();

		List<Post> posts = queryFactory.selectFrom(post)
			.leftJoin(post.likes, like)
			.fetchJoin()
			.where(post.postContent.title.like("%" + title + "%"))
			.orderBy(post.postContent.title.length().asc(),
				post.likes.size().desc())
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
