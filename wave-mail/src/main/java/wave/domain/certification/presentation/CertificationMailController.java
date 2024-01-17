package wave.domain.certification.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wave.domain.certification.application.CertificationMailService;

@RequiredArgsConstructor
@RequestMapping("/api/certification")
@RestController
public class CertificationMailController {
	private final CertificationMailService certificationService;


}
