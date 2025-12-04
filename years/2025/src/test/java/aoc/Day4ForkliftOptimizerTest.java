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
        assertEquals(0, day4.countAccessibleRolls(INPUT));
    }

}
