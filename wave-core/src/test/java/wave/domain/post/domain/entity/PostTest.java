package wave.domain.post.domain.entity;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.Profile;
import wave.domain.account.domain.vo.Role;
import wave.domain.media.domain.vo.MediaUploadStatus;
import wave.domain.media.domain.vo.MediaUrl;
import wave.domain.post.domain.vo.PostContent;
import wave.global.error.exception.BusinessException;

class PostTest {

	@DisplayName("포스트를 작성한 유저가 아닌 경우 예외가 발생한다.")
	@Test
	void throwBusinessExceptionIfUserIsNotAuthorOfPost()
		throws NoSuchFieldException, IllegalAccessException {
		// given
		Profile profile = new Profile("",
			"http://localhost:8080/images/users/1/profile");
		User user = new User("test@test.com", "User1", profile, Role.USER);
		injectUserIdForTest(user, 1L);

		PostContent postContent = new PostContent("빈지노",
			"Dali, Van, Picasso",
			"빈지노의 명곡 달리 반 피카소!",
			"빈지노의 불후의 명곡이죠. 들어보세요~");
		MediaUrl mediaUrl = new MediaUrl("",
			"https://www.youtube.com/watch?v=TcbC9nJBlLc",
			MediaUploadStatus.COMPLETED);
		Post post = new Post(postContent, mediaUrl, user);

		Profile anotherProfile = new Profile("",
			"http://localhost:8080/images/users/2/profile");
		User anotherUser = new User("test2@test.com", "User2", anotherProfile, Role.USER);
		injectUserIdForTest(user, 2L);

		//  when -> then
		assertThatThrownBy(() -> post.isAuthor(anotherUser))
			.isInstanceOf(BusinessException.class);
	}

	private void injectUserIdForTest(User user, Long idValue)
		throws NoSuchFieldException, IllegalAccessException {
		Field id = User.class.getDeclaredField("id");
		id.setAccessible(true);
		id.set(user, idValue);
	}

}
