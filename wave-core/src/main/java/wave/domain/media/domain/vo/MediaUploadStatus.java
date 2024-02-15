package wave.domain.media.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MediaUploadStatus {
	SHARED("포스트가 공유되었습니다."),
	PROGRESSED("파일 업로드가 진행중 입니다..."),
	COMPLETED("파일 업로드가 완료되었습니다.");

	private final String message;

}
