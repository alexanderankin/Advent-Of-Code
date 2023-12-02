import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day01CalorieCounting {
    String example = """
            1000
            2000
            3000
                        
            4000
                        
            5000
            6000
                        
            7000
            8000
            9000
                        
            10000
            """;

    int elfMostFood(List<List<Integer>> elfFood) {
        // int withMost = 0;
        int most = 0;
        for (List<Integer> foods : elfFood) {
            int current = foods.stream().mapToInt(Integer::intValue).sum();
            if (current > most) {
                // withMost = i;
                most = current;
            }
        }
        return most;
    }

    @Test
    void test_elfMostFood() {
        assertEquals(24000, elfMostFood(parseList(example)));
    }

    private List<List<Integer>> parseList(String example) {
        return Arrays.stream(example.split("\n\n"))
                .map(e -> Arrays.stream(e.split("\n"))
                        .map(Integer::parseInt).toList())
                .toList();
    }

    @SneakyThrows
    String readValue() {
        return IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/p1.elf-foods.txt")), StandardCharsets.UTF_8);
    }

    @Test
    void submit_elfMostFood() {
        System.out.println(elfMostFood(parseList(readValue())));
    }

    @Test
    void test_boundedPq() {
        BoundedPq<Integer> p = new BoundedPq<>(0, BoundedPq.DEFAULT_SIZE);
        assertEquals(List.of(0, 0, 0), p.toList());
        p.prioritize(5);
        assertEquals(List.of(5, 0, 0), p.toList());
        p.prioritize(3);
        assertEquals(List.of(5, 3, 0), p.toList());
        p.prioritize(4);
        assertEquals(List.of(5, 4, 3), p.toList());
        p.prioritize(1);
        assertEquals(List.of(5, 4, 3), p.toList());
        p.prioritize(6);
        assertEquals(List.of(6, 5, 4), p.toList());
        p.prioritize(5);
        assertEquals(List.of(6, 5, 5), p.toList());
    }

    List<Integer> topFoodElves(List<List<Integer>> elfFood) {
        // sorted hi to lo
        BoundedPq<Integer> top = new BoundedPq<>(0);
        for (List<Integer> foods : elfFood) {
            int current = foods.stream().mapToInt(Integer::intValue).sum();
            top.prioritize(current);
        }
        return top.toList();
    }

    @Test
    void test_topFoodElves() {
        assertEquals(45000, topFoodElves(parseList(example)).stream().mapToInt(Integer::intValue).sum());
    }

    @Test
    void submit_topFoodElves() {
        System.out.println(topFoodElves(parseList(readValue())).stream().mapToInt(Integer::intValue).sum());
    }

    static class BoundedPq<T extends Comparable<T>> {
        private static final int DEFAULT_SIZE = 3;
        private final List<T> list;

        BoundedPq(T defaultValue) {
            this(defaultValue, 3);
        }

        BoundedPq(T defaultValue, int fixedSize) {
            list = new ArrayList<>(fixedSize);
            list.addAll(Collections.nCopies(fixedSize, defaultValue));
        }

        public void prioritize(T next) {
            for (int i = 0; i < list.size(); i++) {
                T ith = list.get(i);
                if (next.compareTo(ith) > 0) {
                    T other = next;
                    for (int j = i; j < list.size(); j++) {
                        other = list.set(j, other);
                    }
                    list.set(i, next);
                    return;
                }
            }
        }

        public List<T> toList() {
            return list;
        }
    }
}
