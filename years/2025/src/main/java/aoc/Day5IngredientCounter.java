package aoc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day5IngredientCounter {
    public long count(String testInput) {
        List<long[]> ranges = new ArrayList<>();
        List<Long> ingredients = new ArrayList<>();
        boolean firstHalf = true;
        for (var iter = testInput.lines().iterator(); iter.hasNext(); ) {
            var line = iter.next();
            if (line.isEmpty()) {
                firstHalf = false;
            } else {
                if (firstHalf) {
                    var parts = line.split("-");
                    ranges.add(new long[]{Long.parseLong(parts[0]), Long.parseLong(parts[1])});
                } else {
                    ingredients.add(Long.parseLong(line));
                }
            }
        }

        return ingredients.stream()
                .filter(ingredient -> ranges.stream().anyMatch(range -> contains(range, ingredient)))
                .count();
    }

    private boolean contains(long[] range, Long ingredient) {
        return range[0] <= ingredient && ingredient <= range[1];
    }

    public long countRangeResult(String testInput) {
        List<long[]> ranges = new ArrayList<>();
        for (var iter = testInput.lines().iterator(); iter.hasNext(); ) {
            var line = iter.next();
            if (line.isEmpty()) {
                break;
            } else {
                var parts = line.split("-");
                ranges.add(new long[]{Long.parseLong(parts[0]), Long.parseLong(parts[1])});
            }
        }

        ranges.sort(Comparator.<long[], Long>comparing(a -> a[0]).thenComparing(a -> a[1]));

        var result = new ArrayList<>(List.of(ranges.getFirst()));

        for (long[] range : ranges) {
            long[] last = result.getLast();
            if (contains(last, range[0]) || contains(last, range[1])) {
                last[0] = Math.min(last[0], range[0]);
                last[1] = Math.max(last[1], range[1]);
            } else {
                result.add(range);
            }
        }

        return result.stream().mapToLong(l -> l[1] - l[0] + 1).sum();
    }
}
