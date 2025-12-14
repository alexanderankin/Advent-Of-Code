package aoc;

import java.util.*;

public class Day7ManifoldAnalyzer {
    int countSplits(String manifoldDiagram) {
        var lines = manifoldDiagram.lines()
                .dropWhile(s -> !s.contains("S"))
                .toList();

        int splits = 0;
        Set<Integer> indexes = new HashSet<>(List.of(lines.getFirst().indexOf("S")));
        for (String line : lines) {
            List<Integer> bad = new ArrayList<>();
            for (Integer index : indexes) {
                if (line.charAt(index) == '^') {
                    bad.add(index);
                }
            }

            for (Integer i : bad) {
                indexes.remove(i);
                indexes.add(i + 1);
                indexes.add(i - 1);
                splits += 1;
            }
        }
        return splits;
    }

    long countPossibilities(String manifoldDiagram) {
        List<String> lines = manifoldDiagram.lines().dropWhile(s -> !s.contains("S")).toList();
        return countPossibilities(new HashMap<>(), lines, new ConcatList<>(List.of(lines.getFirst().indexOf("S"))), 1, lines.getFirst().indexOf("S"));
    }

    private long countPossibilities(Map<Map.Entry<Integer, Integer>, Long> cache, List<String> lines, ConcatList<Integer> choices, int depth, int s) {
        if (depth == lines.size() - 1) {
            // System.out.println(draw(lines, choices));
            return 1;
        }

        Map.Entry<Integer, Integer> cacheKey = Map.entry(depth, s);
        Long fromCache = cache.get(cacheKey);
        if (fromCache != null) {
            // System.out.println(draw(lines, choices));
            return fromCache;
        }

        long result;
        String line = lines.get(depth);
        if (line.charAt(s) == '^') {
            result = countPossibilities(cache, lines, choices.concat(s - 1), depth + 1, s - 1) +
                    countPossibilities(cache, lines, choices.concat(s + 1), depth + 1, s + 1);
        } else {
            result = countPossibilities(cache, lines, choices.concat(s), depth + 1, s);
        }
        cache.put(cacheKey, result);
        return result;
    }

    private String draw(List<String> lines, ConcatList<Integer> choices) {
        lines = new ArrayList<>(lines);
        for (int i = 0; i < choices.size(); i++) {
            var line = lines.get(i);
            var sb = new StringBuilder(line);
            sb.setCharAt(choices.get(i), '|');
            lines.set(i, sb.toString());
        }

        return "Path:\n" + String.join("\n", lines) + "\n";
    }

    static class ConcatList<T> extends ArrayList<T> {
        public ConcatList(Collection<T> ts) {
            super(ts);
        }

        @SafeVarargs
        public final ConcatList<T> concat(T... c) {
            return concat(Arrays.asList(c));
        }

        public ConcatList<T> concat(Collection<? extends T> c) {
            var newList = new ConcatList<>(this);
            newList.addAll(c);
            return newList;
        }
    }
}
