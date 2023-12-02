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
    String exampleInput = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
            """;

    private static int charToItemValue(int c) {
        return c > 'Z' ? c - 'a' : (c - 'A' + 26);
    }

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
            int seenIndex = charToItemValue(c);
            seen[seenIndex] = 1;
        }

        for (int i = half; i < chars.length; i++) {
            var c = chars[i];
            int seenIndex = charToItemValue(c);
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
        assertEquals(List.of(16, 38, 42, 22, 20, 19), Arrays.stream(exampleInput.split("\\s")).map(this::findMatchingItemValue).toList());
        assertEquals(157, sumMatchingItems(exampleInput));
    }

    @Test
    void submit_part1() {
        assertEquals(7848, sumMatchingItems(readValue()));
    }

    int part2(String exampleInput) {
        int result = 0;
        String[] contentsList = exampleInput.split("\\s");
        int[] seenList = new int[26 * 2];
        for (int i = 0; i < contentsList.length; i += 3) {
            // each group
            Arrays.fill(seenList, 0);
            int thisGroup = -1;
            for (int j = 0; j < 3; j++) {
                char[] chars = contentsList[j + i].toCharArray();

                for (char aChar : chars) {
                    seenList[charToItemValue(aChar)] |= (1 << j);

                    if (j == 2 && (seenList[charToItemValue(aChar)] == 0b111)) {
                        thisGroup = aChar;
                        break;
                    }
                }
            }

            // System.out.println(((char) thisGroup) + " for this group: " + Arrays.asList(Arrays.copyOfRange(contentsList, i, i + 3)));
            result += charToItemValue(thisGroup) + 1;
        }

        return result;
    }

    @Test
    void test_part2() {
        // System.out.println(part2());
        assertEquals(70, part2(exampleInput));
    }

    @Test
    void submit_part2() {
        // System.out.println(part2(readValue()));
        assertEquals(2616, part2(readValue()));
    }
}
