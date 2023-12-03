import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day08TreeHouse {
    static final String EXAMPLE_INPUT = """
            30373
            25512
            65332
            33549
            35390""";

    private static int[][] parse(String[] lines) {
        int height = lines.length;
        int width = lines[0].length();
        int[][] matrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            byte[] line = lines[i].getBytes(StandardCharsets.UTF_8);
            int[] ints = new int[width];
            matrix[i] = ints;
            for (int j = 0; j < width; j++) {
                ints[j] = line[j] - '0';
            }
        }
        return matrix;
    }

    int numberOfVisibleTrees(String representation) {
        return numberOfVisibleTrees(representation, null);
    }

    int numberOfVisibleTrees(String representation, PrintStream out) {
        var matrix = parse(representation.split("\r?\n"));

        // N-E-S-W
        int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        int counter = 0;
        for (int i = 1; i < matrix.length - 1; i++) {
            for (int j = 1; j < matrix[i].length - 1; j++) {
                int value = matrix[i][j];

                // A tree is visible if all trees between it and an edge
                // of the grid are shorter than it.
                boolean visible = false;
                for (int[] direction : directions) {
                    int x = i;
                    int y = j;
                    boolean allShorter = true;
                    boolean anyTrees = false;
                    while (x > 0 && y > 0) {
                        x -= direction[1];
                        y -= direction[0];
                        // time of check is immediately before time of use [x]
                        if (!(x < matrix.length && y < matrix[i].length)) break;
                        anyTrees = true;
                        int other = matrix[x][y];
                        if (other >= value) {
                            allShorter = false;
                            break;
                        }
                    }

                    if (allShorter && anyTrees) {
                        visible = true;
                        break;
                    }
                }

                if (out != null) {
                    String formatted = "value %s at %d/%d is %s".formatted(matrix[i][j], i, j, visible);
                    out.println(formatted);
                }
                if (visible)
                    counter++;
            }
        }
        int edges = matrix.length + matrix.length + matrix[0].length + matrix[0].length - 4;
        return counter + edges;
    }

    @Test
    void test_numberOfVisibleTrees() {
        var out = new ByteArrayOutputStream();
        assertEquals(21, numberOfVisibleTrees(EXAMPLE_INPUT, new PrintStream(out)));

        String output = out.toString(StandardCharsets.UTF_8);

        String expected = """
                value 5 at 1/1 is true
                value 5 at 1/2 is true
                value 1 at 1/3 is false
                value 5 at 2/1 is true
                value 3 at 2/2 is false
                value 3 at 2/3 is true
                value 3 at 3/1 is false
                value 5 at 3/2 is true
                value 4 at 3/3 is false
                """;
        assertEquals(expected, output);
    }

    @Test
    void submit_numberOfVisibleTrees() {
        assertEquals(1827, numberOfVisibleTrees(readValue()));
    }

    @SuppressWarnings("ALL")
    @SneakyThrows
    private String readValue() {
        return new String(getClass().getResourceAsStream("/p8.txt").readAllBytes());
    }

    void part2() {
    }
}
