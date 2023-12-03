package aoc.y2023;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

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
    static final int[][] DIRECTIONS = new int[][]{
            {1, 0},
            {1, 1},
            {0, 1},
            {-1, 1},
            {-1, 0},
            {-1, -1},
            {0, -1},
            {1, -1}
    };

    @SneakyThrows
    private String readValue() {
        return new String(getClass().getResourceAsStream("/p3.txt").readAllBytes());
    }

    int part1(int[][] matrix) {
        Set<NumberAt> numbers = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int value = matrix[i][j];
                boolean isNum = value >= 0 && value <= 9;

                if (!isNum && value != '.') {
                    Set<NumberAt> adjacentNumbers = adjacentNumbers(matrix, i, j);
                    numbers.addAll(adjacentNumbers);
                }
            }
        }
        return numbers.stream().mapToInt(NumberAt::value).sum();
    }

    private Set<NumberAt> adjacentNumbers(int[][] matrix, int i, int j) {
        Set<NumberAt> result = new HashSet<>();
        for (var dir : DIRECTIONS) {
            int y = i - dir[0];
            int x = j - dir[1];
            if (y < 0 || y >= matrix.length || x < 0 || x >= matrix[0].length) continue;
            var other = matrix[y][x];
            if (other >= 0 && other <= 9) {
                var row = matrix[y];
                int min = x;
                while (min >= 0 && row[min] >= 0 && row[min] <= 9) min--;
                min++;
                int max = x;
                while (max < row.length && row[max] >= 0 && row[max] <= 9) max++;
                max--;
                int value = 0;
                for (int k = min; k <= max; k++) {
                    value *= 10;
                    value += row[k];
                }
                result.add(new NumberAt(y, min, value));
            }
        }
        return result;
    }

    @Test
    void test_adjacentNumbers() {
        assertEquals(Set.of(),
                adjacentNumbers(parse("""
                        ....
                        ....
                        """), 1, 1));
        assertEquals(Set.of(
                        new NumberAt(0, 0, 12),
                        new NumberAt(1, 2, 12)),
                adjacentNumbers(parse("""
                        12..
                        ..12
                        """), 1, 1));
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
                    // } else if (aChar == '.') {
                    //     result[i][j] = -1;
                    // } else if (aChar == '*') {
                    //     result[i][j] = -3;
                } else {
                    result[i][j] = aChar;
                }
            }
        }
        return result;
    }

    @Test
    void test_part1() {
        assertEquals(4361, part1(parse(EXAMPLE_INPUT)));
    }

    @Test
    void submit_part1() {
        assertEquals(532445, part1(parse(readValue())));
    }

    int part2(int[][] matrix) {
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int value = matrix[i][j];
                boolean isNum = value >= 0 && value <= 9;

                if (!isNum && value != '.') {
                    Set<NumberAt> adjacentNumbers = adjacentNumbers(matrix, i, j);
                    if (adjacentNumbers.size() == 2)
                        result += adjacentNumbers.stream()
                                .mapToInt(NumberAt::value)
                                .reduce(1, (a, b) -> a * b);
                }
            }
        }
        return result;
    }

    @Test
    void test_part2() {
        assertEquals(467835, part2(parse(EXAMPLE_INPUT)));
    }

    @Test
    void submit_part2() {
        assertEquals(79842967, part2(parse(readValue())));
    }

    record NumberAt(int i, int j, int value) {
    }
}
