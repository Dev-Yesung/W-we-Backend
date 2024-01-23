package wave.domain.account.application.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.port.out.LoadAccountPort;
import wave.domain.account.domain.vo.CustomUserDetails;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final LoadAccountPort loadAccountPort;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Long userId = Long.valueOf(username);
		User user = loadAccountPort.findAccountById(userId);

		return new CustomUserDetails(user);
	}
}
