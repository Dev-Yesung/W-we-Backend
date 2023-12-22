package wave.domain.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Scope {
	ACCESS_TOKEN("ACCESS_TOKEN"),
	REFRESH_TOKEN("REFRESH_TOKEN");

	private final String name;
}
