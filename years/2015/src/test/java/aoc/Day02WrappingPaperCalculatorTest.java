package aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02WrappingPaperCalculatorTest {

    static final String INPUT = read("day02.txt");

    Day02WrappingPaperCalculator day02 = new Day02WrappingPaperCalculator();

    @ParameterizedTest
    @CsvSource({
            "'2x3x4', 58",
            "'1x1x10', 43",
    })
    void test(String input, int expected) {
        assertEquals(expected, day02.wrappingPaperFor(input));
    }

    @Test
    void input() {
        assertEquals(1588178, day02.wrappingPaperFor(INPUT));
    }

    @ParameterizedTest
    @CsvSource({
            "'2x3x4', 34",
            "'1x1x10', 14",
    })
    void testP2(String input, int expected) {
        assertEquals(expected, day02.ribbonFor(input));
    }

    @Test
    void inputP2() {
        assertEquals(3783758, day02.ribbonFor(INPUT));
    }
}
