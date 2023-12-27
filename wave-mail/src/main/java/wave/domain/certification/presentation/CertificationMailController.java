package wave.domain.certification.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wave.domain.certification.application.CertificationMailService;
import wave.domain.certification.dto.CertificationRequest;
import wave.domain.certification.dto.CertificationResponse;

@RequiredArgsConstructor
@RequestMapping("/api/certification")
@RestController
public class CertificationMailController {
	private final CertificationMailService certificationService;

	@PostMapping
	public ResponseEntity<CertificationResponse> sendCertificationMail(
		@RequestBody @Validated CertificationRequest request
	) {
		CertificationResponse response = certificationService.sendCertificationMailByCertificationType(request);

		return ResponseEntity.ok(response);
	}
}
