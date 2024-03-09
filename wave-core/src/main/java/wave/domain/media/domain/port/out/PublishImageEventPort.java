package wave.domain.media.domain.port.out;

import wave.domain.media.domain.vo.Image;
import wave.domain.media.dto.request.LoadPostImageRequest;

public interface PublishImageEventPort {

	Image publishLoadImageFileEvent(LoadPostImageRequest request);

}
