package aoc.y2023;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day02Games {

    List<Integer> filterPossibleGames(String input, Map<String, Integer> inventory) {
        List<Integer> results = new ArrayList<>();
        String[] games = input.split("\\n");
        outer:
        for (String game : games) {
            var parts = game.split(": ");
            var index = Integer.parseInt(parts[0].substring("Game ".length()));

            for (String setUsage : parts[1].split("; ")) {
                for (String colorUsage : setUsage.split(", ")) {
                    String[] colorInfo = colorUsage.split(" ");
                    int usage = Integer.parseInt(colorInfo[0]);
                    var colorName = colorInfo[1];

                    if (usage > inventory.get(colorName))
                        continue outer;
                }
            }
            results.add(index);
        }
        return results;
    }

    @Test
    void test_filterPossibleGames() {
        String input = """
                Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
                """;
        Map<String, Integer> inventory = Map.of("red", 12, "green", 13, "blue", 14);
        List<Integer> possible = List.of(1, 2, 5);
        int expected = possible.stream().mapToInt(Integer::intValue).sum();
        
        List<Integer> actual = filterPossibleGames(input, inventory);
        assertEquals(possible, actual);
        assertEquals(expected, actual.stream().mapToInt(Integer::intValue).sum());
    }

    @SneakyThrows
    @Test
    void submit_filterPossibleGames() {
        var inventory = Map.of("red", 12, "green", 13, "blue", 14);
        String input = IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/p2.game-values.txt")), StandardCharsets.UTF_8);
        List<Integer> x = filterPossibleGames(input, inventory);
        System.out.println(x.stream().mapToInt(Integer::intValue).sum());
    }

}
