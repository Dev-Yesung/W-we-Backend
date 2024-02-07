package wave.domain.post.domain.port.out;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.support.SendResult;

import wave.domain.media.dto.MediaFileUploadMessage;

public interface PostEventBroker {

	CompletableFuture<SendResult<String, Object>> publishMusicUploadEvent(MediaFileUploadMessage mediaFileUploadMessage);
}
