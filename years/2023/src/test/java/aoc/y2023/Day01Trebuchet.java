package aoc.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day01Trebuchet {
    /**
     * On each line, the calibration value can be found by
     * combining the first digit and the last digit (in that order)
     * to form a single two-digit number.
     *
     * @param line the line
     * @return the calibration value
     */
    int calibrationValue(String line) {
        var parts = line.split("\\D+");
        int i = 0;
        for (; i < parts.length; i++) {
            if (!parts[i].isEmpty()) break;
        }
        int tens = (parts[i].charAt(0) - '0') * 10;
        int ones = parts[parts.length - 1].charAt(parts[parts.length - 1].length() - 1) - '0';
        return tens + ones;
    }

    List<Integer> calibrationValues(String lines) {
        return Arrays.stream(lines.split("\\s")).filter(Predicate.not(String::isEmpty)).map(this::calibrationValue).toList();
    }

    @Test
    void test_calibrationValues() {
        String input = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
                """;
        List<Integer> integers = calibrationValues(input);
        assertEquals(List.of(12, 38, 15, 77), integers);
        assertEquals(142, integers.stream().mapToInt(Integer::intValue).sum());
    }
}
