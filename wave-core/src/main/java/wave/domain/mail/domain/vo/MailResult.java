package wave.domain.mail.domain.vo;

import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MailResult {
	private final String email;
	private final String eventName;
	private final boolean isSuccess;

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (MailResult)obj;
		return Objects.equals(this.email, that.email) &&
			   Objects.equals(this.eventName, that.eventName) &&
			   this.isSuccess == that.isSuccess;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, eventName, isSuccess);
	}
}
