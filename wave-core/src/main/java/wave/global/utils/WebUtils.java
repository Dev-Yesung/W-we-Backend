package wave.global.utils;

import java.net.URI;

import org.springframework.web.util.UriComponentsBuilder;

public final class WebUtils {

	public static URI createUri(String scheme, String host, String path) {
		return UriComponentsBuilder.newInstance()
			.scheme(scheme)
			.host(host)
			.path(path)
			.build()
			.encode()
			.toUri();
	}

}
