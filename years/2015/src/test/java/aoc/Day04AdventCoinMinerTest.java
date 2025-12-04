package aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day04AdventCoinMinerTest {
    Day04AdventCoinMiner day04 = new Day04AdventCoinMiner();

    @ParameterizedTest
    @CsvSource({
            "abcdef, 609043",
            "pqrstuv, 1048970",
    })
    void test(String key, int seed) {
        assertEquals(seed, day04.seedOf(key, 5));
    }

    @Test
    void input() {
        assertEquals(346386, day04.seedOf("iwrupvqb", 5));
    }

    @Test
    void inputP2() {
        assertEquals(9958218, day04.seedOf("iwrupvqb", 6));
    }
}
