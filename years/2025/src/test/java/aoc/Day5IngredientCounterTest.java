package aoc;

import org.junit.jupiter.api.Test;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5IngredientCounterTest {

    static final String TEST_INPUT = """
            3-5
            10-14
            16-20
            12-18
            
            1
            5
            8
            11
            17
            32""";

    static final String INPUT = read("day05.txt").trim();

    Day5IngredientCounter day5 = new Day5IngredientCounter();

    @Test
    void test() {
        assertEquals(3, day5.count(TEST_INPUT));
    }

    @Test
    void input() {
        assertEquals(789, day5.count(INPUT));
    }

    @Test
    void testP2() {
        assertEquals(14, day5.countRangeResult(TEST_INPUT));
    }

    @Test
    void inputP2() {
        // too high
        // assertEquals(363767745322851L, day5.countRangeResult(INPUT));
        // assertTrue(363767745322851L > day5.countRangeResult(INPUT));
        // 329355884638118 too low
        // assertEquals(329355884638118L, day5.countRangeResult(INPUT));
        assertEquals(343329651880509L, day5.countRangeResult(INPUT));
    }
}
