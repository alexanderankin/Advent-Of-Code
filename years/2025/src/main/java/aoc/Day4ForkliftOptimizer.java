package aoc;

import java.util.AbstractList;

public class Day4ForkliftOptimizer {
    int countAccessibleRolls(String diagram) {
        var grid = diagram.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        return countAccessibleRolls(grid);
    }

    int countAccessibleRolls(char[][] grid) {
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

    int countTotalAccessibleRolls(String diagram) {
        // find removable
        // iterate through removable
        // - remove one - recurse
        // - return result
        // - if result > max; max = result
        // return max
        var grid = diagram.lines()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        return max(grid, 0);
    }

    int max(char[][] grid, int depth) {
        int result = 0;
        var removable = removable(grid);
        if (removable.isEmpty())
            return depth;
        int max = 0;
        for (int[] coords : removable) {
            remove(coords, grid);
            int withRemoved = max(grid, depth + 1);
            if (withRemoved > max) {
                max = withRemoved;
            }
            replace(coords, grid);

            // tried to skip full back tracking and it worked. not sure why!
            // i guess it doesn't matter which order you do it in
            if (2 > 1) {
                result += max;
                return result;
            }
        }
        result += max;
        return result;
    }

    private void replace(int[] coords, char[][] grid) {
        grid[coords[0]][coords[1]] = '.';
    }

    private void remove(int[] coords, char[][] grid) {
        grid[coords[0]][coords[1]] = '.';
    }

    Coords removable(char[][] grid) {
        int numAccessibleRolls = countAccessibleRolls(grid);
        var r = new Coords(new int[numAccessibleRolls * 2]);
        var c = 0;
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

                if (adjacentRolls < 4) {
                    r.data[c++] = i;
                    r.data[c++] = j;
                }
            }
        }
        return r;
    }

    static class Coords extends AbstractList<int[]> {
        final int[] data;

        Coords(int[] data) {
            this.data = data;
        }

        @Override
        public int[] get(int index) {
            return new int[]{
                    data[index * 2],
                    data[index * 2 + 1],
            };
        }

        @Override
        public int size() {
            return data.length / 2;
        }
    }
}
