package wave.config;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import wave.domain.auth.application.AjaxAuthenticationProvider;
import wave.domain.auth.application.JwtAuthenticationProvider;
import wave.domain.auth.controller.RestAuthenticationEntryPoint;
import wave.domain.auth.application.JwtExtractor;
import wave.domain.auth.application.SkipPathRequestMatcher;
import wave.global.filter.AjaxLoginProcessingFilter;
import wave.global.filter.JwtAuthenticationProcessingFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
	private static final String AUTHENTICATION_URL = "/api/auth/login";
	private static final String REFRESH_TOKEN_URL = "/api/auth/token";
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
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		List<String> permitAllEndpoints = getPermitAllEndpoints();
		AuthenticationManager authenticationManager = getAuthenticationManager(http);
		AjaxLoginProcessingFilter ajaxLoginProcessingFilter =
			buildAjaxLoginProcessingFilter(authenticationManager);
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
			.authorizeHttpRequests(request -> {
				request.requestMatchers(AUTHENTICATION_URL).permitAll()
					.anyRequest().authenticated();
			})
			.cors(configurer -> configurer.configurationSource(corsConfigurationSource))
			.addFilterBefore(ajaxLoginProcessingFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	private List<String> getPermitAllEndpoints() {
		return Arrays.asList(
			AUTHENTICATION_URL
		);
	}

	private AuthenticationManager getAuthenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
			.authenticationProvider(ajaxAuthenticationProvider)
			.authenticationProvider(jwtAuthenticationProvider)
			.build();
	}

	private AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter(AuthenticationManager authenticationManager) {
		AjaxLoginProcessingFilter ajaxLoginProcessingFilter =
			new AjaxLoginProcessingFilter(AUTHENTICATION_URL, authenticationSuccessHandler,
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
