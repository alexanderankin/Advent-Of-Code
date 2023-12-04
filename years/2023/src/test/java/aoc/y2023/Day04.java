package aoc.y2023;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day04 {
    static final String EXAMPLE_INPUT = """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11""";


    int part1(String representation) {
        boolean[] matches = new boolean[100];

        var cards = representation.split("\r?\n");
        int result = 0;
        for (var card : cards) {
            Arrays.fill(matches, false);

            var values = card.split(":")[1];
            var parts = values.split("\\|");
            var winningNums = parts[0];
            var numsYouHave = parts[1];

            for (var win : winningNums.split("\\s")) {
                if (win.isBlank()) continue;
                matches[Integer.parseInt(win)] = true;
            }

            var power = -1;

            for (var have : numsYouHave.split("\\s")) {
                if (have.isBlank()) continue;
                if (matches[Integer.parseInt(have)])
                    power++;

            }

            result += (int) Math.pow(2, power);
        }

        return result;
    }

    @Test
    void test_part1() {
        assertEquals(13, part1(EXAMPLE_INPUT));
    }

    @SneakyThrows
    String readValue() {
        return new String(getClass()
                .getResourceAsStream("/p4.txt").readAllBytes(), StandardCharsets.UTF_8);
    }

    @Test
    void submit_part1() {
        System.out.println(part1(readValue()));
    }

    int part2(String representation) {
        boolean[] matches = new boolean[100];

        var cards = representation.split("\r?\n");

        int[] frequencies = new int[cards.length];
        for (int i = 0; i < cards.length; i++) {
            var card = cards[i];
            Arrays.fill(matches, false);

            var values = card.split(":")[1];
            var parts = values.split("\\|");
            var winningNums = parts[0];
            var numsYouHave = parts[1];

            for (var win : winningNums.split("\\s")) {
                if (win.isBlank()) continue;
                matches[Integer.parseInt(win)] = true;
            }

            var power = -1;

            for (var have : numsYouHave.split("\\s")) {
                if (have.isBlank()) continue;
                if (matches[Integer.parseInt(have)])
                    power++;

            }
            power++;

            int myFreq = frequencies[i] + 1;
            for (int j = 0; j < power && j + 1 + i < frequencies.length; j++) {
                frequencies[j + 1 + i] += myFreq;
            }
        }

        return Arrays.stream(frequencies).sum() + frequencies.length;
    }

    @Test
    void test_part2() {
        assertEquals(30, part2(EXAMPLE_INPUT));
    }

    @Test
    void submit_part2() {
        assertEquals(5539496, part2(readValue()));
    }
}
