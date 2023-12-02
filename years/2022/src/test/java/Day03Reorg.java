import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day03Reorg {
    @SneakyThrows
    private String readValue() {
        return IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/p3.txt")), StandardCharsets.UTF_8);
    }

    /**
     * @param contents letters describing set of items
     * @return alphabetical ordinal of item which is in both halves (should be only one)
     */
    int findMatchingItemValue(String contents) {
        var chars = contents.toCharArray();
        var seen = new int[26 * 2];
        Arrays.fill(seen, 0);

        int half = chars.length / 2;
        for (int i = 0; i < half; i++) {
            var c = chars[i];
            int seenIndex = c > 'Z' ? c - 'a' : (c - 'A' + 26);
            seen[seenIndex] = 1;
        }

        for (int i = half; i < chars.length; i++) {
            var c = chars[i];
            boolean lower = c > 'Z';
            int seenIndex = lower ? c - 'a' : (c - 'A' + 26);
            if (seen[seenIndex] == 1)
                return seenIndex + 1;
        }

        throw new IllegalArgumentException("should be unreachable");
    }

    int sumMatchingItems(String contentsList) {
        return Arrays.stream(contentsList.split("\\s")).mapToInt(this::findMatchingItemValue).sum();
    }

    @Test
    void test_sumMatchingItems() {
        String input = """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw
                """;

        assertEquals(List.of(16, 38, 42, 22, 20, 19), Arrays.stream(input.split("\\s")).map(this::findMatchingItemValue).toList());
        assertEquals(157, sumMatchingItems(input));
    }

    @Test
    void submit_part1() {
        System.out.println(sumMatchingItems(readValue()));
    }

}
