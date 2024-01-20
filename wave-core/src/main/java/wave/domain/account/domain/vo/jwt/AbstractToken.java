package wave.domain.account.domain.vo.jwt;

import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractToken {

	private final String rawToken;
	private final Map<String, String> claims;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AbstractToken that = (AbstractToken)o;
		return Objects.equals(rawToken, that.rawToken) && Objects.equals(claims, that.claims);
	}

	@Override
	public int hashCode() {
		return Objects.hash(rawToken, claims);
	}
}
