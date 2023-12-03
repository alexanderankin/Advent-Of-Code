package aoc.y2023;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        return read(matrix, i + 1, j) == -2 ||
                read(matrix, i - 1, j) == -2 ||
                read(matrix, i, j + 1) == -2 ||
                read(matrix, i, j - 1) == -2 ||
                read(matrix, i + 1, j + 1) == -2 ||
                read(matrix, i + 1, j - 1) == -2 ||
                read(matrix, i - 1, j + 1) == -2 ||
                read(matrix, i - 1, j - 1) == -2;
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
                } else {
                    result[i][j] = -2;
                }
            }
        }
        return result;
    }

    @Test
    void test() {
        assertEquals(4361, thing(EXAMPLE_INPUT));
    }

    @SneakyThrows
    private String readValue() {
        return new String(getClass().getResourceAsStream("/p3.txt").readAllBytes());
    }

    @Test
    void submit() {
        // 535391 if counting adjacent to after # end
        System.out.println(thing(readValue()));
    }

    @Test
    void test1() {
        String input = """
                311...672...
                ............
                ....411.....
                ........*328
                .....144....""";

        int expected = /*672 + 411 +*/ 328 + 144;
        assertEquals(expected, thing(input));
    }

}
