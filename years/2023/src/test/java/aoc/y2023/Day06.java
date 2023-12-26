package aoc.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("NewClassNamingConvention")
class Day06 {
    static final String EXAMPLE_INPUT = """
            Time:      7  15   30
            Distance:  9  40  200""";

    static final String ACTUAL_INPUT = """
            Time:        53     83     72     88
            Distance:   333   1635   1289   1532""";

    @Test
    void test_parsing() {
        System.out.println(Race.parse(EXAMPLE_INPUT));
    }

    // equations
    // Tw (time waiting) + Tm (time moving) = Tt (time total)
    // D (distance) = Tm * (Tw)
    // D = tm * (tm - tt)
    // tw = tm / d
    // there should be a better way!!l

    boolean won(Race race, int wait) {
        return (race.time - wait) * wait > race.distance;
    }

    @ParameterizedTest
    @CsvSource({
            "0,false",
            "1,false",
            "2,true",
            "3,true",
            "4,true",
            "5,true",
            "6,false",
    })
    void test_won(int wait, boolean expected) {
        assertEquals(expected, won(Race.parse(EXAMPLE_INPUT).getFirst(), wait));
    }

    List<Integer> cutOffs(Race race) {
        throw new UnsupportedOperationException("maybe a way to do this without counting");
    }

    @ParameterizedTest
    @CsvSource(value = {"0,''"})
    void test_cutOffs(int race, String expected) {
        assertThrows(UnsupportedOperationException.class, () ->
                assertEquals(Arrays.stream(expected.split(",")).filter(Predicate.not(String::isEmpty)).map(Integer::parseInt).toList(),
                        cutOffs(Race.parse(EXAMPLE_INPUT).get(race)))
        );
    }

    int[] ways(Race race) {
        int start = 0;
        while (start < race.distance && !won(race, start)) {
            start++;
        }
        int end = start;
        while (end < race.distance && won(race, end)) {
            end++;
        }
        return new int[]{start, end};
    }

    int waysSum(Race race) {
        int[] ways = ways(race);
        return ways[1] - ways[0];
    }

    @ParameterizedTest
    @CsvSource({
            "0,4,2,5",
            "1,8,4,11",
            "2,9,11,19",
    })
    void test_ways(int race, int expected, int min, int max) {
        int[] ways = ways(Race.parse(EXAMPLE_INPUT).get(race));
        int minActual = ways[0];
        int maxActual = ways[1];
        assertEquals(List.of(min, max), List.of(minActual, maxActual - 1));
        assertEquals(expected, maxActual - minActual);
    }

    int partOne(List<Race> races) {
        return races.stream().mapToInt(this::waysSum).reduce((a, b) -> a * b).orElseThrow();
    }

    @Test
    void test_partOne() {
        assertEquals(288, partOne(Race.parse(EXAMPLE_INPUT)));
    }

    @Test
    void submit_partOne() {
        System.out.println(partOne(Race.parse(ACTUAL_INPUT)));
    }

    int part2(Race race) {
        return waysSum(race);
    }

    @Test
    void test_part2() {
        assertEquals(71503, part2(Race.parseFixingKerning(EXAMPLE_INPUT)));
    }

    @Test
    void submit_part2() {
        System.out.println(part2(Race.parseFixingKerning(ACTUAL_INPUT)));
    }

    record Race(long time, long distance) {
        private static final Map<String, List<Race>> cache = new HashMap<>();

        static List<Race> parse(String input) {
            return cache.computeIfAbsent(input, Race::doParse);
        }

        private static List<Race> doParse(String input) {
            var lines = input.split("\n");
            var times = lines[0].split("\\s+");
            var distances = lines[1].split("\\s+");

            List<Race> result = new ArrayList<>();
            for (int i = 1; i < times.length; i++) {
                result.add(new Race(
                        Integer.parseInt(times[i].strip()),
                        Integer.parseInt(distances[i].strip())
                ));
            }
            return result;
        }

        static Race parseFixingKerning(String input) {
            var lines = input.split("\n");
            long time = Long.parseLong(lines[0].split(":")[1].replaceAll("\\s+", ""));
            long distance = Long.parseLong(lines[1].split(":")[1].replaceAll("\\s+", ""));
            return new Race(time, distance);
        }
    }
}
