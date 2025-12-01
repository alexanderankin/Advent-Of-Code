package aoc;

import org.junit.jupiter.api.Test;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1ListDistanceTest {

    static final String TEST_INPUT = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3""";

    static final String INPUT = read("day01.txt");

    Day1ListDistance day1 = new Day1ListDistance();

    @Test
    void test() {
        assertEquals(11, day1.findDistance(TEST_INPUT));
    }

    @Test
    void input() {
        assertEquals(1879048, day1.findDistance(INPUT));
    }

    @Test
    void testP2() {
        assertEquals(11, day1.similarity(TEST_INPUT));
    }

    @Test
    void inputP2() {
        assertEquals(1879048, day1.similarity(INPUT));
    }

}
