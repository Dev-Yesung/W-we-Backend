package wave.domain.account.presentation;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wave.domain.account.application.AccountService;
import wave.domain.account.dto.request.CertificationRequest;
import wave.domain.account.dto.request.CertificationVerifyRequest;
import wave.domain.account.dto.request.SignupRequest;
import wave.domain.account.dto.response.AccountResponse;
import wave.domain.account.dto.response.CertificationResponse;
import wave.global.utils.UriUtils;

@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@RestController
public class AccountController {

	private final AccountService accountService;

	@GetMapping("/signup/email/duplicate")
	public ResponseEntity<Void> checkDuplicateEmail(
		@RequestParam String email
	) {
		accountService.checkDuplicateEmail(email);

		return ResponseEntity
			.status(HttpStatus.OK)
			.build();
	}

	@PostMapping("/certification")
	public ResponseEntity<CertificationResponse> requestCertification(
		@RequestBody CertificationRequest request
	) {
		CertificationResponse response = accountService.requestCertificationCode(request);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/certification/verification")
	public ResponseEntity<Void> verifyCertification(
		@RequestBody CertificationVerifyRequest request
	) {
		accountService.verifyCertificationCode(request);

		return ResponseEntity
			.status(HttpStatus.OK)
			.build();
	}

	@PostMapping("/signup")
	public ResponseEntity<AccountResponse> signup(
		@RequestBody SignupRequest request
	) {
		AccountResponse response = accountService.signup(request);
		URI uri = UriUtils.createUri("http", "localhost:8080", "/api/accounts");

		return ResponseEntity
			.created(uri)
			.body(response);
	}
}
