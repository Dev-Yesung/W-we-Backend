package wave.domain.media.domain.port.out;

import java.util.Optional;

import wave.domain.account.domain.entity.User;

public interface UpdateStreamingPort {

	void cacheStreamingStartValue(Long postId, User user, String ipAddress);

	Optional<String> loadStreamingCacheValue(String ipAddress);

}
