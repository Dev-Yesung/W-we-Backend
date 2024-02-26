package wave.domain.search.adapter.in.web;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.account.dto.response.AccountSearchResponse;
import wave.domain.chart.dto.TrendChartDto;
import wave.domain.search.application.SearchService;
import wave.domain.search.dto.PostSearchResponse;
import wave.domain.search.dto.request.TrendChartRequest;
import wave.global.aop.AuthenticationUser;
import wave.global.common.WebAdapter;

@RequiredArgsConstructor
@RequestMapping("/api/search")
@RestController
@WebAdapter
public class SearchController {

	private final SearchService searchService;

	@GetMapping("/accounts")
	public ResponseEntity<AccountSearchResponse> findAccountByEmailAndNickname(
		@RequestParam String keyword,
		@PageableDefault(size = 5) Pageable pageable,
		@AuthenticationUser User user
	) {
		Long userId = user.getId();
		AccountSearchResponse response = searchService.findAccountByEmailAndNickname(keyword, userId, pageable);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/artists")
	public ResponseEntity<PostSearchResponse> findPostByArtistName(
		@RequestParam String name,
		@PageableDefault Pageable pageable
	) {
		PostSearchResponse response = searchService.findPostByArtistName(name, pageable);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/songs")
	public ResponseEntity<PostSearchResponse> findPostBySongName(
		@RequestParam String title,
		@PageableDefault Pageable pageable
	) {
		PostSearchResponse response = searchService.findPostBySongTitle(title, pageable);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/charts/trend")
	public ResponseEntity<TrendChartDto> findTrendChartByDayMonthYear(
		@RequestParam Integer year,
		@RequestParam Integer month,
		@RequestParam Integer day,
		@RequestParam String type
	) {
		TrendChartRequest request = TrendChartRequest.of(year, month, day, type);
		TrendChartDto response = searchService.findTrendChart(request);

		return ResponseEntity.ok(response);
	}

}
