package wave.domain.post.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import wave.domain.music.application.MusicUploadService;
import wave.domain.music.dto.response.UploadMusicResponse;
import wave.domain.post.domain.OtherMusicPost;
import wave.domain.post.domain.OwnMusicPost;
import wave.domain.post.dto.OtherMusicDto;
import wave.domain.post.dto.OwnMusicDto;
import wave.domain.music.dto.UploadMusicDto;
import wave.domain.post.dto.response.OtherMusicPostCreateResponse;
import wave.domain.post.dto.response.OwnMusicPostCreateResponse;
import wave.domain.post.infra.PostRepository;
import wave.domain.user.domain.User;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
	private final MusicUploadService musicUploadService;
	private final PostRepository postRepository;

	public OwnMusicPostCreateResponse createOwnMusicPost(OwnMusicDto ownMusicDto) {
		OwnMusicPost newOwnMusicPost = ownMusicDto.toEntity();
		OwnMusicPost savedOwnMusicPost = postRepository.save(newOwnMusicPost);

		UploadMusicDto uploadMusicDto = getUploadMusicDto(ownMusicDto, savedOwnMusicPost);
		UploadMusicResponse response = musicUploadService.uploadMusic(uploadMusicDto);
		String path = response.path();
		savedOwnMusicPost.updateFilePath(path);

		return OwnMusicPostCreateResponse.of(savedOwnMusicPost, response);
	}

	public OtherMusicPostCreateResponse createOtherMusicPost(OtherMusicDto otherMusicDto) {
		OtherMusicPost newOtherMusicPost = otherMusicDto.toEntity();
		OtherMusicPost savedOtherMusicPost = postRepository.save(newOtherMusicPost);

		return OtherMusicPostCreateResponse.of(savedOtherMusicPost);
	}

	private UploadMusicDto getUploadMusicDto(OwnMusicDto ownMusicDto, OwnMusicPost savedOwnMusicPost) {
		User user = ownMusicDto.user();
		Long userId = user.getId();
		Long postId = savedOwnMusicPost.getId();
		MultipartFile ownMusicFile = ownMusicDto.ownMusic();

		return new UploadMusicDto(userId, postId, ownMusicFile);
	}
}
