package wave.domain.auth.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import jakarta.servlet.http.HttpServletRequest;

public class SkipPathRequestMatcher implements RequestMatcher {
	private final OrRequestMatcher matchers;
	private final RequestMatcher processingMatcher;

	public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
		Assert.notNull(pathsToSkip, "pathsToSkip은 null일 수 없습니다.");

		List<RequestMatcher> requestMatchers = pathsToSkip.stream()
			.map(AntPathRequestMatcher::new)
			.collect(Collectors.toUnmodifiableList());
		matchers = new OrRequestMatcher(requestMatchers);
		processingMatcher = new AntPathRequestMatcher(processingPath);
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		if (matchers.matches(request)) {
			return false;
		}
		return processingMatcher.matches(request);
	}
}
