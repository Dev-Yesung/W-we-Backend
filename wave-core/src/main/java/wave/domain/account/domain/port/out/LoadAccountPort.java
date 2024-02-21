package wave.domain.account.domain.port.out;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.Certification;
import wave.domain.account.domain.vo.CertificationType;
import wave.domain.account.dto.AccountInfo;

public interface LoadAccountPort {

	Optional<User> findAccountByEmail(String email);

	void checkDuplicateEmail(String email);

	String getCertificationCode(Certification certification);

	void existCertificationCode(CertificationType certificationType, String email);

	User findAccountById(Long userId);

	Slice<AccountInfo> findByEmailAndNicknameExceptSelf(String keyword, Long selfId, Pageable pageable);

}
