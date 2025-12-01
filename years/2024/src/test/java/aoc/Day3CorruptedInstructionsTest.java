package aoc;

import org.junit.jupiter.api.Test;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3CorruptedInstructionsTest {

    static final String TEST_INPUT = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
    static final String INPUT = read("day03.txt");
    static final String TEST_INPUT_P2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

    Day3CorruptedInstructions day3 = new Day3CorruptedInstructions();

    @Test
    void test() {
        assertEquals(161, day3.sumValidMulInstructions(TEST_INPUT));
    }

    @Test
    void input() {
        assertEquals(181345830, day3.sumValidMulInstructions(INPUT));
    }

    @Test
    void testP2() {
        assertEquals(48, day3.sumWithEnabling(TEST_INPUT_P2));
    }

    @Test
    void inputP2() {
        // 98729041 = too high
        assertEquals(98729041, day3.sumWithEnabling(INPUT));
    }
}
