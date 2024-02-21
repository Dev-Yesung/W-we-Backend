package wave.domain.media.domain.port.out.persistence;

import java.util.Optional;

public interface StreamingCacheRepository {

	void setValueAndTimeout(String key, String value);

	Optional<String> getAndDelete(String key);
}
