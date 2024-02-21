package wave.domain.search.adapter.out.persistence;

import static wave.domain.account.domain.entity.QUser.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.port.out.persistence.AccountQueryRepository;

@Repository
public class AccountQueryDslRepository implements AccountQueryRepository {

	private final JPAQueryFactory queryFactory;

	public AccountQueryDslRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Slice<User> findAccountByKeywordWhereEmailOrNicknameExceptSelf(
		String keyword, Long selfId, Pageable pageable) {

		long offset = pageable.getOffset();
		int pageSize = pageable.getPageSize();

		List<User> accounts = queryFactory
			.selectFrom(user)
			.where(user.email.like("%" + keyword + "%")
				.or(user.nickname.like("%" + keyword + "%"))
				.and(user.id.ne(selfId)))
			.orderBy(user.email.asc(),
				user.nickname.asc())
			.offset(offset)
			.limit(pageSize + 1)
			.fetch();
		boolean hasNextPage = hasNextPage(accounts, pageSize);

		return new SliceImpl<>(accounts, pageable, hasNextPage);
	}

	private boolean hasNextPage(List<User> accounts, int pageSize) {
		boolean hasNextPage = accounts.size() > pageSize;
		if (hasNextPage) {
			accounts.remove(pageSize);
		}

		return hasNextPage;
	}
}
