package aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03DeliveryTrackerTest {

    static final String INPUT = read("day03.txt");

    Day03DeliveryTracker day03 = new Day03DeliveryTracker();

    @ParameterizedTest
    @CsvSource({
            "'>', 2",
            "'<<vv', 5",
            "'vv<<', 5",
            "'^>v<', 4",
            "'^v^v^v^v^v', 2",
            // "'>^^v^<>v<<<v<v^>>v^^^<v', 16",
    })
    void test(String directions, int expectedHouses) {
        assertEquals(expectedHouses, day03.housesNaive(directions));
    }

    @Test
    void input() {
        assertEquals(2592, day03.housesNaive(INPUT));
    }

    @ParameterizedTest
    @CsvSource({
            "'^v', 3",
            "'^>v<', 3",
            "'^v^v^v^v^v', 11",
    })
    void testP2(String directions, int expectedHouses) {
        assertEquals(expectedHouses, day03.roboHelper(directions));
    }

    @Test
    void inputP2() {
        assertEquals(2360, day03.roboHelper(INPUT));
    }
}
