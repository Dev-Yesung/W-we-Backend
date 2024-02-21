package wave.domain.account.domain.port.out.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import wave.domain.account.domain.entity.User;

public interface AccountQueryRepository {

	Slice<User> findAccountByKeywordWhereEmailOrNicknameExceptSelf(
		String keyword, Long selfId, Pageable pageable);

}
