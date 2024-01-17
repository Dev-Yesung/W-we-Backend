package wave.domain.account.domain;

public interface AccountLoadPort {
	void checkDuplicateEmail(String email);
}
