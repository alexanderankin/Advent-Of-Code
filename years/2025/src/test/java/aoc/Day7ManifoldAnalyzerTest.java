package aoc;

import org.junit.jupiter.api.Test;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7ManifoldAnalyzerTest {

    static final String TEST_INPUT = """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............""";

    static final String INPUT = read("day07.txt");

    Day7ManifoldAnalyzer day7 = new Day7ManifoldAnalyzer();

    @Test
    void test() {
        assertEquals(21, day7.countSplits(TEST_INPUT));
    }

    @Test
    void input() {
        assertEquals(1533, day7.countSplits(INPUT));
    }

    @Test
    void testP2() {
        assertEquals(40, day7.countPossibilities(TEST_INPUT));
    }

    @Test
    void inputP2() {
        // 405881186 - too low
        long result = day7.countPossibilities(INPUT);
        assertEquals(405881186, (int) result);
        assertEquals(10733529153890L, result);
    }
}
