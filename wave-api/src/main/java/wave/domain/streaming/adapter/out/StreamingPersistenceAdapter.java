package wave.domain.streaming.adapter.out;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wave.domain.account.domain.entity.User;
import wave.domain.media.domain.port.out.UpdateStreamingPort;
import wave.domain.media.domain.port.out.persistence.StreamingCacheRepository;
import wave.global.common.PersistenceAdapter;

@Slf4j
@RequiredArgsConstructor
@PersistenceAdapter
public class StreamingPersistenceAdapter implements UpdateStreamingPort {

	private final StreamingCacheRepository streamingCacheRepository;

	@Override
	public void cacheStreamingStartValue(Long postId, User user, String ipAddress) {
		String key = "STREAMING" + ":" + ipAddress;
		String value = postId + ":" + user.getEmail() + ":" + System.currentTimeMillis();
		log.info("Streaming Start - key:{}, value:{}", key, value);
		streamingCacheRepository.setValueAndTimeout(key, value);
	}

	@Override
	public Optional<String> loadStreamingCacheValue(String ipAddress) {
		String key = "STREAMING" + ":" + ipAddress;

		return streamingCacheRepository.getAndDelete(key);
	}

}
