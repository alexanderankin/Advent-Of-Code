package aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01FloorsTest {

    static final String INPUT = read("day01.txt");

    Day01Floors day1 = new Day01Floors();

    @ParameterizedTest
    @CsvSource({
            "'(())', 0", "'()()', 0",
            "'(((', 3", "'(()(()(', 3",
            "'))(((((', 3",
            "'())', -1", "'))(', -1",
            "')))', -3", "')())())', -3",
    })
    void test(String directions, int expected) {
        assertEquals(expected, day1.whatFloor(directions));
    }

    @Test
    void input() {
        assertEquals(280, day1.whatFloor(INPUT));
    }

    @ParameterizedTest
    @CsvSource({
            "')', 1",
            "'()())', 5",
    })
    void testP2(String directions, int expected) {
        assertEquals(expected, day1.firstBasementDirection(directions));
    }

    @Test
    void inputP2() {
        assertEquals(1797, day1.firstBasementDirection(INPUT));
    }
}
