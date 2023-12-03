package aoc.y2023;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day03 {
    static final String EXAMPLE_INPUT = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
            """;

    int thing(String input) {
        var matrix = parse(input);
        // System.out.println(Arrays.deepToString(matrix));

        int length = matrix.length;
        int width = matrix[0].length;

        List<Integer> numbers = new ArrayList<>();
        int number = 0;
        boolean inNumber = false;
        boolean adjacent = false;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                var value = matrix[i][j];
                if (inNumber) {
                    if (value >= 0) {
                        // must be only if number goes on, not after #'s stopped
                        adjacent |= anySymbolsAround(matrix, i, j);
                        number *= 10;
                        number += value;
                    } else {
                        inNumber = false;
                        // finish number
                        if (adjacent) {
                            numbers.add(number);
                        }
                        number = 0;
                        adjacent = false;
                    }
                } else {
                    if (value >= 0) {
                        inNumber = true;
                        number = value;
                        adjacent = anySymbolsAround(matrix, i, j);
                    }
                }
            }

            // if (adjacent && inNumber) {
            //     numbers.add(number);
            //     inNumber = false;
            // }
        }

        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    private boolean anySymbolsAround(int[][] matrix, int i, int j) {
        return read(matrix, i + 1, j) == -2 || read(matrix, i + 1, j) == -3 ||
                read(matrix, i - 1, j) == -2 || read(matrix, i - 1, j) == -3 ||
                read(matrix, i, j + 1) == -2 || read(matrix, i, j + 1) == -3 ||
                read(matrix, i, j - 1) == -2 || read(matrix, i, j - 1) == -3 ||
                read(matrix, i + 1, j + 1) == -2 || read(matrix, i + 1, j + 1) == -3 ||
                read(matrix, i + 1, j - 1) == -2 || read(matrix, i + 1, j - 1) == -3 ||
                read(matrix, i - 1, j + 1) == -2 || read(matrix, i - 1, j + 1) == -3 ||
                read(matrix, i - 1, j - 1) == -2 || read(matrix, i - 1, j - 1) == -3;
    }

    private int[][] countNumbersAround(int[][] matrix, int i, int j) {
        int[][] result = new int[8][2];
        int resultPointer = 0;
        if (read(matrix, i + 1, j) > 0) result[resultPointer++] = new int[]{i + 1, j};
        if (read(matrix, i - 1, j) > 0) result[resultPointer++] = new int[]{i - 1, j};
        if (read(matrix, i, j + 1) > 0) result[resultPointer++] = new int[]{i, j + 1};
        if (read(matrix, i, j - 1) > 0) result[resultPointer++] = new int[]{i, j - 1};
        if (read(matrix, i + 1, j + 1) > 0) result[resultPointer++] = new int[]{i + 1, j + 1};
        if (read(matrix, i + 1, j - 1) > 0) result[resultPointer++] = new int[]{i + 1, j - 1};
        if (read(matrix, i - 1, j + 1) > 0) result[resultPointer++] = new int[]{i - 1, j + 1};
        if (read(matrix, i - 1, j - 1) > 0) result[resultPointer++] = new int[]{i - 1, j - 1};
        return Arrays.copyOf(result, resultPointer);
    }

    List<Integer> allNumbersAround(int[][] matrix, int i, int j) {
        List<NumberAt> numbers = new ArrayList<>();

        if (read(matrix, i + 1, j) > 0) numbers.add(numberAt(matrix, i + 1, j));
        if (read(matrix, i - 1, j) > 0) numbers.add(numberAt(matrix, i - 1, j));
        if (read(matrix, i, j + 1) > 0) numbers.add(numberAt(matrix, i, j + 1));
        if (read(matrix, i, j - 1) > 0) numbers.add(numberAt(matrix, i, j - 1));
        if (read(matrix, i + 1, j + 1) > 0) numbers.add(numberAt(matrix, i + 1, j + 1));
        if (read(matrix, i + 1, j - 1) > 0) numbers.add(numberAt(matrix, i + 1, j - 1));
        if (read(matrix, i - 1, j + 1) > 0) numbers.add(numberAt(matrix, i - 1, j + 1));
        if (read(matrix, i - 1, j - 1) > 0) numbers.add(numberAt(matrix, i - 1, j - 1));

        return new HashSet<>(numbers).stream().map(NumberAt::number).toList();
    }

    @ParameterizedTest
    @CsvSource({
            "2,38,828:511",
            "7,58,613:899",
            "13,18,745:209",
            "17,36,347:524",
            "19,84,856",
            "16,86,628:343",
    })
    void test_allNumbersAround(int row, int col, String results) {
        var expected = Arrays.stream(results.split(":")).map(Integer::parseInt).collect(Collectors.toSet());
        var input = parse(readValue());

        assertEquals(expected, new HashSet<>(allNumbersAround(input, row - 1, col - 1)));
    }

    private NumberAt numberAt(int[][] matrix, int i, int j) {
        int[] row = matrix[i];
        int jStart = j;
        int jEnd = j;
        while (jStart >= 0 && row[jStart] >= 0) jStart--;
        jStart++;
        while (jEnd < row.length && row[jEnd] >= 0) jEnd++;
        jEnd--;

        int result = 0;
        for (int k = jStart; k <= jEnd; k++) {
            result *= 10;
            result += row[k];
        }
        return new NumberAt(result, i, jStart);
    }

    int read(int[][] matrix, int i, int j) {
        try {
            return matrix[i][j];
        } catch (Exception e) {
            return -10;
        }
    }

    private int[][] parse(String input) {
        var lines = input.split("\n");
        int length = lines.length;
        int width = lines[0].length();

        int[][] result = new int[length][width];

        for (int i = 0; i < length; i++) {
            String line = lines[i];
            result[i] = new int[width];
            char[] chars = line.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char aChar = chars[j];
                if (aChar >= '0' && aChar <= '9') {
                    result[i][j] = aChar - '0';
                } else if (aChar == '.') {
                    result[i][j] = -1;
                } else if (aChar == '*') {
                    result[i][j] = -3;
                } else {
                    result[i][j] = -2;
                }
            }
        }
        return result;
    }

    @Test
    void testPart1() {
        assertEquals(4361, thing(EXAMPLE_INPUT));
    }

    @SneakyThrows
    private String readValue() {
        return new String(getClass().getResourceAsStream("/p3.txt").readAllBytes());
    }

    @Test
    void submitPart1() {
        // 535391 if counting adjacent to after # end
        assertEquals(532445, thing(readValue()));
    }

    @Test
    void debugPart1() {
        String input = """
                311...672...
                ............
                ....411.....
                ........*328
                .....144....""";

        int expected = /*672 + 411 +*/ 328 + 144;
        assertEquals(expected, thing(input));
    }

    int part2(String s) {
        return part2(parse(s));
    }

    int part2(int[][] matrix) {
        // Set<Map.Entry<Integer, Integer>> seen = new HashSet<>();
        int length = matrix.length;
        int width = matrix[0].length;
        int result = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                var value = matrix[i][j];
                if (value == -3) {
                    // int[][] numbersAdjacent = countNumbersAround(matrix, i, j);
                    // int[][] filtered = filterOutExpandedToSameNumber(matrix, numbersAdjacent);
                    var filtered = allNumbersAround(matrix, i, j).toArray(Integer[]::new);
                    if (filtered.length == 2) {
                        var num0 = filtered[0];
                        var num1 = filtered[1];

                        // int num0Value = discoverNumber(matrix, num0);
                        // int num1Value = discoverNumber(matrix, num1);

                        // int ratio = num0Value * num1Value;
                        int ratio = num0 * num1;
                        result += ratio;
                    }
                }
            }
        }
        return result;
    }

    int[][] filterOutExpandedToSameNumber(int[][] matrix, int[][] numbersAdjacent) {
        Set<Range> set = new HashSet<>();
        int[][] expanded = new int[numbersAdjacent.length][];
        int i = 0;
        for (int[] ints : numbersAdjacent) {
            var e = expandCoords(matrix, ints);
            if (set.add(new Range(ints[0], e[0], e[1]))) {
                expanded[i++] = ints;
            }
        }
        return Arrays.copyOf(expanded, i);
    }

    int[] expandCoords(int[][] matrix, int[] coords) {
        int start = coords[1];
        int end = start;
        // while (start > 0 && matrix[coords[0]][start - 1] >= 0) start--;
        // while (end < matrix[0].length && matrix[coords[0]][end] >= 0) end++;
        while (read(matrix, coords[0], start - 1) >= 0) start--;
        while (read(matrix, coords[0], end) >= 0) end++;

        return new int[]{start, end};
    }

    int discoverNumber(int[][] matrix, int[] coords) {
        int start = coords[1];
        int end = start;
        // while (matrix[coords[0]][start - 1] >= 0) start--;
        // while (matrix[coords[0]][end] >= 0) end++;
        while (read(matrix, coords[0], start - 1) >= 0) start--;
        while (read(matrix, coords[0], end) >= 0) end++;

        int result = 0;
        for (int i = start; i < end; i++) {
            int next = matrix[coords[0]][i];
            result *= 10;
            result += next;
        }
        return result;
    }

    @Test
    void test_part2() {
        assertEquals(467835, part2(EXAMPLE_INPUT));
    }

    @Test
    void submit_part2() {
        // 61329094 too low
        // 77069067 too low
        // System.out.println(part2(readValue()));
        assertEquals(79841031, part2(readValue()));
    }

    @Test
    void debug_part2() {
        assertEquals(672 * 411 + 328 * 144,
                part2("""
                        311...672...
                        .......*....
                        ....411.....
                        ........*328
                        .....144...."""));

        assertEquals(672 * 411/* + 328 * 144*/,
                part2("""
                        311...672...
                        .......*....
                        ....411.....
                        .......*328.
                        .....144...."""));

        assertEquals(311 * 12 + 12 * 12 + 123 * 123,
                part2("""
                        311.......12
                        *......*...*
                        12........12
                        .*11........
                        1..123*123.."""));

        assertEquals(144 * 3,
                part2("""
                        ...........*
                        ..12*12...12
                        ......*.....
                        .....12*12..
                        ............"""));
    }

    record NumberAt(int number, int i, int j) {
    }

    record Range(int x1, int x2, int y) {
    }
}
