import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("NewClassNamingConvention")
class Day02RockPaperScissors {
    // r p s -- actual order
    // r s p -- winner order
    static final int[][] PL_OPP_RPS_LOOKUP = {{0, -1, 1}, {1, 0, -1}, {-1, 1, 0}};
    static final int[][] OPP_OUT_RPS_LOOKUP = {
            // if the opponent plays R:
            {
                    // and I need to lose
                    2,
                    // and I need to tie:
                    0,
                    // and I need to win:
                    1,
            },
            // opponent plays P:
            {0, 1, 2},
            // opponent plays S:
            {1, 2, 0}
    };

    int round(String round) {
        char opponent = round.charAt(0);
        char you = round.charAt(2);
        int opponentChoice = opponent - 'A';
        int yourChoice = you - 'X';
        int intrinsicPoints = yourChoice + 1;
        int winner = winner(yourChoice, opponentChoice);
        int bonus = winner == 0 ? 3 : winner > 0 ? 6 : 0;
        return intrinsicPoints + bonus;
    }

    int winner(int aRps, int bRps) {
        return PL_OPP_RPS_LOOKUP[aRps][bRps];
    }

    // Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock.
    @ParameterizedTest
    @CsvSource({
            "R,S,1",
            "S,P,1",
            "P,R,1",

            "R,R,0",
            "P,P,0",
            "S,S,0",

            "R,R,0",
            "R,P,-1",
            "R,S,1",

            "P,R,1",
            "P,P,0",
            "P,S,-1",

            "S,R,-1",
            "S,P,1",
            "S,S,0",
    })
    void test(String aValue, String bValue, int expected) {
        var aRpsValue = Map.of("R", 1, "P", 2, "S", 3).get(aValue);
        var bRpsValue = Map.of("R", 1, "P", 2, "S", 3).get(bValue);

        assertEquals(expected, winner(aRpsValue - 1, bRpsValue - 1));
    }

    int sumRounds(List<String> lines) {
        return lines.stream().filter(Predicate.not(String::isEmpty)).mapToInt(this::round).sum();
    }

    @Test
    void test_sumRounds() {
        assertEquals(15, sumRounds(Arrays.asList("""
                A Y
                B X
                C Z
                """.split("\n"))));
    }


    @Test
    void test_sumRoundsInputCase() {
        assertTrue(131133883 > sumRounds(Arrays.asList(readValue().split("\n"))));
        assertTrue(12845 > sumRounds(Arrays.asList(readValue().split("\n"))));
        // assertTrue(12503 > sumRounds(Arrays.asList(readValue().split("\n"))));
    }

    @Test
    void submit_sumRounds() {
        // System.out.println(sumRounds(Arrays.asList(readValue().split("\n"))));
        assertEquals(11603, sumRounds(Arrays.asList(readValue().split("\n"))));
    }

    @SneakyThrows
    private String readValue() {
        return IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/p2.txt")), StandardCharsets.UTF_8);
    }

    /// part 2

    int part2(String round) {
        char opponent = round.charAt(0);
        char you = round.charAt(2);
        int opponentChoice = opponent - 'A';
        int yourChoice = OPP_OUT_RPS_LOOKUP[opponentChoice][you - 'X'];
        int intrinsicPoints = yourChoice + 1;
        int winner = winner(yourChoice, opponentChoice);
        int bonus = winner == 0 ? 3 : winner > 0 ? 6 : 0;
        return intrinsicPoints + bonus;
    }

    @ParameterizedTest
    @CsvSource({
            "R,0,S",
            "R,1,R",
            "R,2,P",
            "P,0,R",
            "P,1,P",
            "P,2,S",
            "S,0,P",
            "S,1,S",
            "S,2,R",
    })
    void test_part2Winner(String opponent, int outcome, String expectedMove) {
        int op = Map.of("R", 1, "P", 2, "S", 3).get(opponent) - 1;
        int ex = Map.of("R", 1, "P", 2, "S", 3).get(expectedMove) - 1;
        assertEquals(ex, OPP_OUT_RPS_LOOKUP[op][outcome]);
    }

    int part2(List<String> rounds) {
        return rounds.stream().mapToInt(this::part2).sum();
    }

    @Test
    void test_part2() {
        assertEquals(12, part2(Arrays.asList("""
                A Y
                B X
                C Z
                """.split("\n"))));
    }

    @Test
    @SneakyThrows
    void submit_part2() {
        // System.out.println(part2(Arrays.asList(readValue().split("\n"))));
        assertEquals(12725, part2(Arrays.asList(readValue().split("\n"))));
    }
}
