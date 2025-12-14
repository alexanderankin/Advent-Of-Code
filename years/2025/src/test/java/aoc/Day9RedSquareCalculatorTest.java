package aoc;

import org.junit.jupiter.api.Test;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9RedSquareCalculatorTest {

    static final String TEST_INPUT = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3""";

    static final String INPUT = read("day09.txt");

    Day9RedSquareCalculator day9 = new Day9RedSquareCalculator();

    @Test
    void test() {
        assertEquals(50, day9.areaOfLargestRedRectangle(TEST_INPUT));
    }

    @Test
    void input() {
        assertEquals(4777816465L, day9.areaOfLargestRedRectangle(INPUT));
    }

    @Test
    void testP2() {
        assertEquals(24, day9.areaOfLargestInteriorRectangle(TEST_INPUT));
    }

    @Test
    void inputP2() {
        assertEquals(1410501884, day9.areaOfLargestInteriorRectangle(INPUT));
    }
}
