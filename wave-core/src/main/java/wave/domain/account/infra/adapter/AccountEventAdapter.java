package wave.domain.account.infra.adapter;

import lombok.RequiredArgsConstructor;
import wave.global.common.EventAdapter;
import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.port.out.PublishAccountEventPort;
import wave.domain.account.domain.port.out.SubscribeAccountEventPort;
import wave.domain.account.domain.vo.Certification;
import wave.domain.account.infra.event.AccountEventBroker;

@RequiredArgsConstructor
@EventAdapter
public class AccountEventAdapter
	implements PublishAccountEventPort, SubscribeAccountEventPort {

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
