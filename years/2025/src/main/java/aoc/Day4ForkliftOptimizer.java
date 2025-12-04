package aoc;

public class Day4ForkliftOptimizer {
    int countAccessibleRolls(String diagram) {
        var grid = diagram.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        // System.out.println(Arrays.deepToString(grid).replaceAll("\\], \\[", "],\n["));

        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != '@')
                    continue;

                int adjacentRolls = 0;
                // @formatter:off
                try { if (grid[i][j + 1] == '@') adjacentRolls++; } catch (Exception ignored) {}
                try { if (grid[i + 1][j] == '@') adjacentRolls++; } catch (Exception ignored) {}
                try { if (grid[i + 1][j + 1] == '@') adjacentRolls++; } catch (Exception ignored) {}
                try { if (grid[i - 1][j + 1] == '@') adjacentRolls++; } catch (Exception ignored) {}
                try { if (grid[i][j - 1] == '@') adjacentRolls++; } catch (Exception ignored) {}
                try { if (grid[i - 1][j] == '@') adjacentRolls++; } catch (Exception ignored) {}
                try { if (grid[i - 1][j - 1] == '@') adjacentRolls++; } catch (Exception ignored) {}
                try { if (grid[i + 1][j - 1] == '@') adjacentRolls++; } catch (Exception ignored) {}
                // @formatter:on

                if (adjacentRolls < 4)
                    result += 1;
            }
        }

        return result;
    }
}
