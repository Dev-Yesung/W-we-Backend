package wave.config;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import wave.domain.account.application.authentication.AjaxAuthenticationProvider;
import wave.domain.account.application.jwt.JwtAuthenticationProvider;
import wave.domain.account.application.jwt.JwtExtractor;
import wave.domain.account.application.authentication.SkipPathRequestMatcher;
import wave.domain.account.adapter.web.RestAuthenticationEntryPoint;
import wave.global.filter.AjaxLoginProcessingFilter;
import wave.global.filter.JwtAuthenticationProcessingFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class ApiSecurityConfig {
	private static final String SIGN_UP_URL = "/api/accounts/signup";
	private static final String LOG_IN_URL = "/api/accounts/login";
	private static final String REFRESH_TOKEN_URL = "/api/accounts/token";
	private static final String API_ROOT_URL = "/api/**";

	private final CorsConfigurationSource corsConfigurationSource;
	private final RestAuthenticationEntryPoint authenticationEntryPoint;
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final AuthenticationFailureHandler authenticationFailureHandler;
	private final AjaxAuthenticationProvider ajaxAuthenticationProvider;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final JwtExtractor jwtExtractor;
	private final ObjectMapper objectMapper;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/api/accounts/**")
			.requestMatchers("/h2-console/**");
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager
			= new ProviderManager(ajaxAuthenticationProvider, jwtAuthenticationProvider);
		AjaxLoginProcessingFilter ajaxLoginProcessingFilter =
			buildAjaxLoginProcessingFilter(authenticationManager);
		List<String> permitAllEndpoints = getPermitAllEndpoints();
		JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter =
			buildJwtAuthenticationProcessingFilter(permitAllEndpoints, authenticationManager);

		return http
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.rememberMe(AbstractHttpConfigurer::disable)
			.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
			.exceptionHandling(configurer -> configurer.authenticationEntryPoint(authenticationEntryPoint))
			.authorizeHttpRequests(request -> request
				.requestMatchers("/api/post/**", SIGN_UP_URL, LOG_IN_URL).permitAll()
				.anyRequest().authenticated()
			)
			.cors(configurer -> configurer.configurationSource(corsConfigurationSource))
			.addFilterBefore(ajaxLoginProcessingFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
			.authenticationManager(authenticationManager)
			.build();
	}

	private List<String> getPermitAllEndpoints() {
		return Arrays.asList(
			SIGN_UP_URL, LOG_IN_URL
		);
	}

	private AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter(AuthenticationManager authenticationManager) {
		AjaxLoginProcessingFilter ajaxLoginProcessingFilter =
			new AjaxLoginProcessingFilter(LOG_IN_URL, authenticationSuccessHandler,
				authenticationFailureHandler, objectMapper);
		ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManager);

		return ajaxLoginProcessingFilter;
	}

	private JwtAuthenticationProcessingFilter buildJwtAuthenticationProcessingFilter(
		List<String> permitAllEndpoints,
		AuthenticationManager authenticationManager
	) {
		SkipPathRequestMatcher skipPathRequestMatcher =
			new SkipPathRequestMatcher(permitAllEndpoints, API_ROOT_URL);
		JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter =
			new JwtAuthenticationProcessingFilter(authenticationFailureHandler, jwtExtractor, skipPathRequestMatcher);
		jwtAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);

		return jwtAuthenticationProcessingFilter;
	}
}
