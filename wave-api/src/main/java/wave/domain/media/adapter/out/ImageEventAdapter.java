package wave.domain.media.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.media.domain.port.out.broker.ImageEventBroker;
import wave.domain.media.domain.port.out.PublishImageEventPort;
import wave.domain.media.domain.vo.Image;
import wave.domain.media.dto.request.LoadImageRequest;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class ImageEventAdapter implements PublishImageEventPort {

	private final ImageEventBroker imageEventBroker;

	@Override
	public Image publishLoadImageFileEvent(LoadImageRequest request) {
		return (Image)imageEventBroker.publishAndReplyImageEvent(
			"load_image_requests", request, "load_Image_replies");
	}
}
