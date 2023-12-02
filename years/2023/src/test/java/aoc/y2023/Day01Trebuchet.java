package aoc.y2023;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    @SneakyThrows
    @Test
    void submit_calibrationValues() {
        String input = IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/p1.calibration-values.txt")), StandardCharsets.UTF_8);

        int sum = calibrationValues(input).stream().mapToInt(Integer::intValue).sum();
        assertEquals(54644, sum);
    }

    int calibrationValueSpelledOut(String line) {
        Pattern spelledOut = Pattern.compile("(one|two|three|four|five|six|seven|eight|nine|\\d)");
        var mapping = Map.of(
                "one", "1",
                "two", "2",
                "three", "3",
                "four", "4",
                "five", "5",
                "six", "6",
                "seven", "7",
                "eight", "8",
                "nine", "9"
        );
        
        Matcher matcher = spelledOut.matcher(line);
        if (!matcher.find()) throw new IllegalArgumentException(line);
        String first = mapping.getOrDefault(matcher.group(1), matcher.group(1));
        
        int from = line.length();
        String found = null;
        while (from-- > 0) {
            if (matcher.find(from)) {
                found = mapping.getOrDefault(matcher.group(1), matcher.group(1));
                break;
            }
        }

        String last = Objects.requireNonNullElse(found, first);
        return ((first.charAt(0) - '0') * 10) + (last.charAt(0) - '0');
    }

    // https://jactl.io/blog/2023/12/02/advent-of-code-2023-day1.html
    @ParameterizedTest
    @CsvSource({
            "xoney3zfour2z,12",
            "x3zatwoneyz,31",
            "threeight,38",
    })
    void test_calibrationValueSpelledOut(String input, int expected) {
        assertEquals(expected, calibrationValueSpelledOut(input));
    }
    
    List<Integer> calibrationValuesSpelledOut(String lines) {
        return Arrays.stream(lines.split("\\s"))
                .filter(Predicate.not(String::isEmpty))
                .map(this::calibrationValueSpelledOut).toList();
    }
    
    @Test
    void test_calibrationValuesSpelledOut() {
        String input = """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
                """;
        assertEquals(List.of(29, 83, 13, 24, 42, 14, 76), calibrationValuesSpelledOut(input));
        assertEquals(281, calibrationValuesSpelledOut(input).stream().mapToInt(Integer::intValue).sum());
    }

    @SneakyThrows
    @Test
    void submit_calibrationValuesSpelledOut() {
        String input = IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/p1.calibration-values.txt")), StandardCharsets.UTF_8);
        System.out.println(calibrationValuesSpelledOut(input));
        System.out.println(calibrationValuesSpelledOut(input).stream().mapToLong(Integer::longValue).sum());
    }
}
