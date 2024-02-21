package wave.domain.media.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "StreamingSessions")
@Entity
public class StreamingSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long postId;
	private Long userId;

	private Long startMilliSec;
	private Long endMilliSec;
	private String ipAddress;

	public StreamingSession(
		Long postId,
		Long userId,
		Long startMilliSec,
		Long endMilliSec,
		String ipAddress
	) {
		this.postId = postId;
		this.userId = userId;
		this.startMilliSec = startMilliSec;
		this.endMilliSec = endMilliSec;
		this.ipAddress = ipAddress;
	}

}
