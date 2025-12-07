package aoc;

import org.junit.jupiter.api.Test;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6GrandTotalFinderTest {

    static final String TEST_INPUT = """
            123 328  51 64\s
             45 64  387 23\s
              6 98  215 314
            *   +   *   + \s""";

    static final String INPUT = read("day06.txt");

    Day6GrandTotalFinder day6 = new Day6GrandTotalFinder();

    @Test
    void test() {
        assertEquals(4277556L, day6.findGrandTotal(TEST_INPUT));
    }

    @Test
    void input() {
        assertEquals(6343365546996L, day6.findGrandTotal(INPUT));
    }

    @Test
    void testP2() {
        assertEquals(3263827, day6.findGrandTotalVertical(TEST_INPUT));
    }

    @Test
    void inputP2() {
        assertEquals(11136895955912L, day6.findGrandTotalVertical(INPUT));
    }
}
