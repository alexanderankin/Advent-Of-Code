package aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
