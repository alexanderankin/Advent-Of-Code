package aoc.y2023;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day02Games {

    String example = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
            """;

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
        Map<String, Integer> inventory = Map.of("red", 12, "green", 13, "blue", 14);
        List<Integer> possible = List.of(1, 2, 5);
        int expected = possible.stream().mapToInt(Integer::intValue).sum();

        List<Integer> actual = filterPossibleGames(example, inventory);
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

    int powerOfGame(String input) {
        int result = 0;
        int[] values = new int['z'];
        
        String[] games = input.split("\\n");
        for (String game : games) {
            Arrays.fill(values, 0);
            var parts = game.split(": ");

            for (String setUsage : parts[1].split("; ")) {
                for (String colorUsage : setUsage.split(", ")) {
                    String[] colorInfo = colorUsage.split(" ");
                    int usage = Integer.parseInt(colorInfo[0]);
                    var colorName = colorInfo[1];

                    if (usage > values[colorName.charAt(0)])
                        values[colorName.charAt(0)] = usage;
                }
            }
            
            result += values['r'] * values['g'] * values['b'];
        }
        
        return result;
    }
    
    @Test
    void test_powerOfGame() {
        assertEquals(2286, powerOfGame(example));
    }
    
    @SneakyThrows
    @Test
    void submit_powerOfGame() {
        System.out.println(powerOfGame(IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/p2.game-values.txt")), StandardCharsets.UTF_8)));
    }

}
