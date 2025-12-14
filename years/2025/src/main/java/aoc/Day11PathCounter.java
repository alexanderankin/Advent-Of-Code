package aoc;

import java.util.*;
import java.util.stream.Collectors;

public class Day11PathCounter {
    public int countRoutesFromYouToOut(Graph graph) {
        return graph.numPaths("you", "out");
    }

    public long countRoutesFromSvrToOutWithDacAndFft(Graph graph) {
        // proof both code paths work
        return graph.data.size() > 100
                ? graph.numPathsThroughDacAndFftAi(new ArrayList<>(), "svr", "out")
                : graph.numPathsThroughDacAndFft(new ArrayList<>(), "svr", "out");
    }

    static class Graph {
        static final List<String> DAC_FFT = List.of("dac", "fft");
        static final String DAC = "dac";
        static final String FFT = "fft";
        static final int DAC_BIT = 1;
        static final int FFT_BIT = 2;
        static final int BOTH_MASK = DAC_BIT | FFT_BIT;

        final Map<String, Set<String>> data;

        Graph(Map<String, Set<String>> data) {
            this.data = data;
        }

        static Graph parse(String input) {
            return new Graph(input.lines()
                    .map(line -> line.split(": "))
                    .map(parts -> Map.entry(parts[0], new HashSet<>(Arrays.asList(parts[1].split(" ")))))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        }

        int numPaths(String from, String to) {
            var paths = data.get(from);

            var result = 0;
            for (String path : paths) {
                if (path.equals(to))
                    return 1;

                result += numPaths(path, to);
            }

            return result;
        }

        int numPathsThroughDacAndFft(List<String> taken, String from, String to) {
            var paths = data.get(from);
            if (paths == null)
                return 0;

            var result = 0;
            for (String path : paths) {
                // noinspection SlowListContainsAll
                if (path.equals(to) && taken.containsAll(DAC_FFT))
                    return 1;

                taken.add(path);
                result += numPathsThroughDacAndFft(taken, path, to);
                taken.removeLast();
            }

            return result;
        }

        // ai code below here
        long numPathsThroughDacAndFftAi(List<String> ignoredTaken, String from, String to) {
            // ignoredTaken kept only to preserve original signature
            Map<String, long[]> memo = new HashMap<>();
            return numPathsThroughDacAndFftMemo(from, to, maskFor(from), memo);
        }

        private long numPathsThroughDacAndFftMemo(String node, String to, int mask, Map<String, long[]> memo) {
            long[] cache = memo.computeIfAbsent(node, k -> new long[]{-1, -1, -1, -1});
            if (cache[mask] != -1) {
                return cache[mask];
            }

            Set<String> paths = data.get(node);
            if (paths == null || paths.isEmpty()) {
                cache[mask] = 0;
                return 0;
            }

            long result = 0;
            for (String next : paths) {
                int nextMask = mask | maskFor(next);

                if (next.equals(to)) {
                    if (nextMask == BOTH_MASK) {
                        result++;
                    }
                } else {
                    result += numPathsThroughDacAndFftMemo(next, to, nextMask, memo);
                }
            }

            cache[mask] = result;
            return result;
        }

        private int maskFor(String node) {
            if (DAC.equals(node)) return DAC_BIT;
            if (FFT.equals(node)) return FFT_BIT;
            return 0;
        }
    }
}
