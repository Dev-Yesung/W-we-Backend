package wave.domain.media.domain.vo;

import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileId {

	private final Long userId;
	private final Long postId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		FileId fileId = (FileId)o;
		return Objects.equals(userId, fileId.userId) && Objects.equals(postId, fileId.postId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, postId);
	}
}
