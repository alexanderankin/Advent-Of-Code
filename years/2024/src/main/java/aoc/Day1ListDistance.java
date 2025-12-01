package aoc;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Day1ListDistance {

    int findDistance(String testInput) {
        var totalList = testInput.lines()
                .map(s -> s.split("\\s+"))
                .map(parts -> Map.entry(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])))
                .toList();

        var list1 = totalList.stream().map(Map.Entry::getKey).sorted().toList();
        var list2 = totalList.stream().map(Map.Entry::getValue).sorted().toList();

        int total = 0;
        for (int i = 0; i < list1.size(); i++) {
            total += Math.abs(list1.get(i) - list2.get(i));
        }
        return total;
    }

    int similarity(String testInput) {
        var totalList = testInput.lines()
                .map(s -> s.split("\\s+"))
                .map(parts -> Map.entry(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])))
                .toList();

        var list1 = totalList.stream().map(Map.Entry::getKey).toList();
        var list2 = totalList.stream().map(Map.Entry::getValue).toList();

        var scores = new HashMap<Integer, Integer>();

        int total = 0;
        for (Integer i : list1) {
            total += i * scores.computeIfAbsent(i, k -> (int) list2.stream().filter(Predicate.isEqual(k)).count());
        }
        return total;
    }
}
