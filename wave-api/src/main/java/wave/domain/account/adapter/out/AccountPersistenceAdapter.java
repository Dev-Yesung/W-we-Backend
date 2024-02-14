package wave.domain.account.adapter.out;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.port.out.LoadAccountPort;
import wave.domain.account.domain.port.out.UpdateAccountPort;
import wave.global.common.PersistenceAdapter;
import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.Certification;
import wave.domain.account.domain.port.out.AccountCache;
import wave.domain.account.adapter.out.persistence.AccountJpaRepository;
import wave.domain.account.domain.vo.CertificationType;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@PersistenceAdapter
public class AccountPersistenceAdapter
	implements LoadAccountPort, UpdateAccountPort {

	private final AccountJpaRepository accountRepository;
	private final AccountCache accountCache;

	@Override
	public Optional<User> findAccountByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	public void checkDuplicateEmail(String email) {
		accountRepository.findByEmail(email)
			.ifPresent(user -> {
				throw new EntityException(ErrorCode.ALREADY_EXIST_USER_EMAIL);
			});
	}

	@Override
	public String getCertificationCode(Certification certification) {
		return accountCache.getCertificationCode(certification)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_CERTIFICATION_CODE));
	}

	@Override
	public void existCertificationCode(CertificationType certificationType, String email) {
		boolean isExist = accountCache.existCertificationCode(certificationType, email);
		if (!isExist) {
			throw new EntityException(ErrorCode.NOT_FOUND_CERTIFICATION_CODE);
		}
	}

	@Override
	public User findAccountById(Long userId) {
		return accountRepository.findById(userId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_USER));
	}

	@Override
	public User saveAccount(User user) {
		return accountRepository.save(user);
	}

	@Override
	public int cacheCertificationCode(Certification certification) {
		return accountCache.cacheCertificationCode(certification);
	}

	@Override
	public void removeCertificationCode(Certification certification) {
		accountCache.removeCertificationCode(certification)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_CERTIFICATION_CODE));
	}

}
