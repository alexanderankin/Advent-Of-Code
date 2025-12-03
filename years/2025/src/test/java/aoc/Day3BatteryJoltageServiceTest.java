package aoc;

import org.junit.jupiter.api.Test;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3BatteryJoltageServiceTest {
    static final String TEST_INPUT = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111""";

    static final String INPUT = read("day03.txt");

    Day3BatteryJoltageService day3 = new Day3BatteryJoltageService();

    @Test
    void test() {
        assertEquals(357, day3.sumMaxJoltage(TEST_INPUT));
    }

    @Test
    void input() {
        assertEquals(17443, day3.sumMaxJoltage(INPUT));
        var i = 3121910778619L;
    }

    @Test
    void testP2() {
        assertEquals(3121910778619L, day3.sumMaxJoltage12(TEST_INPUT));
    }

    @Test
    void inputP2() {
        assertEquals(172167155440541L, day3.sumMaxJoltage12(INPUT));
    }
}
