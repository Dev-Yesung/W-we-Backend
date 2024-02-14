package wave.global.utils;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RandomCodeUtils {

	private static final int RANDOM_CODE_LENGTH = 6;
	private static final int LOWER_BOUND_UPPER_CASE = 'A';
	private static final int UPPER_BOUND_UPPER_CASE = 'Z' + 1;
	private static final int LOWER_BOUND_LOWER_CASE = 'a';
	private static final int UPPER_BOUND_LOWER_CASE = 'z' + 1;
	private static final int LOWER_BOUND_NUMBER = '0';
	private static final int UPPER_BOUND_NUMBER = '9' + 1;

	public static String createRandomCode() {
		return Stream.generate(() -> {
				int randomType = new Random().nextInt(3);
				return switch (randomType) {
					case 0 -> (char)(new Random().nextInt(UPPER_BOUND_UPPER_CASE - LOWER_BOUND_UPPER_CASE)
									 + LOWER_BOUND_UPPER_CASE);
					case 1 -> (char)(new Random().nextInt(UPPER_BOUND_LOWER_CASE - LOWER_BOUND_LOWER_CASE)
									 + LOWER_BOUND_LOWER_CASE);
					case 2 -> (char)(new Random().nextInt(UPPER_BOUND_NUMBER - LOWER_BOUND_NUMBER)
									 + LOWER_BOUND_NUMBER);
					default -> ' ';
				};
			})
			.limit(RANDOM_CODE_LENGTH)
			.map(String::valueOf)
			.collect(Collectors.joining());
	}

}
