import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WordDistanceSearchingTests {

	@Test
	void min_and_max_distance_equal_when_one_word1_and_word2() {
		final String text = "Today is very good a sunny day";
		final String word1 = "day";
		final String word2 = "Today";

		final Integer packageCount = 2;
		Pair<Integer, Integer> distances = WordDistanceSearcher.foundDistanceBetween(
			word1, 
			word2, 
			packageCount,
			text.length() / packageCount
		);
		
		final Integer expectedMin = 5;
		final Integer expectedMax = 5;
		assertEquals(expectedMin, distances.getFirst());
		assertEquals(expectedMax, distances.getSecond());
	}

	@Test
	void min_and_max_distance_difference_when_one_word1_and_some_word2() {
		final String text = "Today is very good a sunny day and tomorrow will be very good day too.";
		final String word1 = "day";
		final String word2 = "Today";

		final Integer packageCount = 2;
		Pair<Integer, Integer> distances = WordDistanceSearcher.foundDistanceBetween(
			word1, 
			word2, 
			packageCount,
			text.length() / packageCount
		);
		
		final Integer expectedMin = 5;
		final Integer expectedMax = 12;
		assertEquals(expectedMin, distances.getFirst());
		assertEquals(expectedMax, distances.getSecond());
	}
	
	@Test
	void test_example() {
		fail("Not yet implemented");
	}
}
