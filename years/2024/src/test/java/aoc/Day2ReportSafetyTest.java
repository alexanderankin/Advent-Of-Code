package aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2ReportSafetyTest {

    static final String TEST_INPUT = """
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9""";

    static final String INPUT = read("day02.txt");

    Day2ReportSafety dr = new Day2ReportSafety();

    @Test
    void countSafeReports() {
        assertEquals(2, dr.countSafeReports(TEST_INPUT));
    }

    @Test
    void input() {
        assertEquals(257, dr.countSafeReports(INPUT));
    }

    @Test
    void countSafeReportsP2() {
        assertEquals(4, dr.countSafeReports(TEST_INPUT, true));
    }

    @Test
    void inputP2() {
        assertEquals(328, dr.countSafeReports(INPUT, true));
    }

}
