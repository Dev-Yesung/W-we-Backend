package wave.domain.like.dto;

import wave.domain.like.domain.entity.Like;

public record LikeUpdateInfo(
	Like like,
	boolean isRemoved
) {
}
