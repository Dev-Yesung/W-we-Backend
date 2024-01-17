package wave.domain.account.application.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.vo.CustomUserDetails;
import wave.domain.account.domain.entity.User;
import wave.domain.user.infra.UserRepository;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Long userId = Long.valueOf(username);
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_USER));

		return new CustomUserDetails(user);
	}
}
