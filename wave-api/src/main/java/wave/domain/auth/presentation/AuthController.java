package wave.domain.auth.presentation;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import wave.domain.auth.application.AuthService;
import wave.domain.auth.dto.request.CheckCertificationCodeRequest;
import wave.domain.auth.dto.request.SignupRequest;
import wave.domain.auth.dto.response.SignupResponse;

@RequiredArgsConstructor
@Validated
@RequestMapping("/api/auth")
@RestController
public class AuthController {
	private final AuthService authService;

	@GetMapping("/duplicate")
	public ResponseEntity<Void> checkDuplicateEmail(
		@RequestParam @Email String email
	) {
		authService.checkDuplicateEmail(email);

		return ResponseEntity
			.status(HttpStatus.OK)
			.build();
	}

	@PostMapping("/code-check")
	public ResponseEntity<Void> checkCertificationCodeByType(
		@RequestBody CheckCertificationCodeRequest request
	) {
		authService.checkCertificationCode(request);

		return ResponseEntity
			.status(HttpStatus.OK)
			.build();
	}

	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signup(
		@RequestBody SignupRequest request
	) {
		SignupResponse response = authService.signup(request);

		return ResponseEntity
			.created(URI.create("/api/auth/signup"))
			.body(response);
	}
}
