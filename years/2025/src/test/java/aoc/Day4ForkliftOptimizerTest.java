package aoc;

import org.junit.jupiter.api.Test;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4ForkliftOptimizerTest {

    static final String TEST_INPUT = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.""";

    static final String INPUT = read("day04.txt").trim();

    Day4ForkliftOptimizer day4 = new Day4ForkliftOptimizer();

    @Test
    void test() {
        assertEquals(13, day4.countAccessibleRolls(TEST_INPUT));
    }

    @Test
    void input() {
        assertEquals(1395, day4.countAccessibleRolls(INPUT));
    }

    @Test
    void testP2() {
        assertEquals(43, day4.countTotalAccessibleRolls(TEST_INPUT));
    }

    @Test
    void inputP2() {
        assertEquals(8451, day4.countTotalAccessibleRolls(INPUT));
    }
}
