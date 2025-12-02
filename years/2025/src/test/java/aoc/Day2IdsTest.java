package aoc;

import org.junit.jupiter.api.Test;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.*;

class Day2IdsTest {
    static final String TEST_INPUT = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
            "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
            "824824821-824824827,2121212118-2121212124";

    static final String INPUT = read("day02.txt").trim();

    Day2Ids day2Ids = new Day2Ids();

    @Test
    void test() {
        assertEquals(1227775554L, day2Ids.sumInvalidIds(TEST_INPUT, false));
    }

    @Test
    void input() {
        assertEquals(19386344315L, day2Ids.sumInvalidIds(INPUT, false));
    }

    @Test
    void testP2() {
        assertEquals(4174379265L, day2Ids.sumInvalidIds(TEST_INPUT, true));
    }

    @Test
    void inputP2() {
        assertEquals(34421651192L, day2Ids.sumInvalidIds(INPUT, true));
    }
}
