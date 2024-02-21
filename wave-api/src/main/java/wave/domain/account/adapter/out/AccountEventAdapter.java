package wave.domain.account.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.port.out.PublishAccountEventPort;
import wave.domain.account.domain.port.out.broker.AccountEventBroker;
import wave.domain.account.domain.vo.Certification;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class AccountEventAdapter implements PublishAccountEventPort {

	private final AccountEventBroker accountEventBroker;

	@Override
	public void publishCertificationEvent(Certification certification) {
		accountEventBroker.publishCertificationEvent(certification);
	}

	@Override
	public void publishNewAccountEvent(User user) {
		accountEventBroker.publishNewAccountEvent(user);
	}
}
