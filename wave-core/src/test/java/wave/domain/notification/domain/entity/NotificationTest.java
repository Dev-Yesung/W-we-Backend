package wave.domain.notification.domain.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wave.global.error.exception.BusinessException;

class NotificationTest {

	@DisplayName("알림 메시지를 읽으면 isRead가 true로 된다.")
	@Test
	void isReadIsTrueIfUserReadNotification() {
		// given
		Notification notification = new Notification(1L,
			1L,
			"빈지노의 명곡 달리, 반, 피카소입니다.",
			"포스트 등록을 완료했습니다.");

		// when
		notification.readNotification(1L);

		// then
		assertThat(notification.isRead())
			.isTrue();
	}

	@DisplayName("권한 없는 유저가 알림 메시지를 읽음처리 할 수 없다.")
	@Test
	void test() {
		// given
		Notification notification = new Notification(1L,
			1L,
			"빈지노의 명곡 달리, 반, 피카소입니다.",
			"포스트 등록을 완료했습니다.");

		// when -> then
		assertThatThrownBy(() -> notification.readNotification(2L))
			.isInstanceOf(BusinessException.class);
	}

}
