package aoc.y2023;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day07 {
    static final String EXAMPLE_INPUT = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483""";
    static final String ACTUAL_INPUT = getString();

    @SneakyThrows
    @SuppressWarnings("ALL")
    private static String getString() {
        return new String(Day07.class.getResourceAsStream("/p7.txt").readAllBytes(), StandardCharsets.UTF_8);
    }

    static Type typeOf(String cards) {
        for (Type value : Type.VALUES) {
            if (value.test(cards))
                return value;
        }
        throw new IllegalStateException();
    }

    @ParameterizedTest
    @CsvSource({
            "AAAAA,FIVE_OF_A_KIND",
            "AA8AA,FOUR_OF_A_KIND",
            "23332,FULL_HOUSE",
            "TTT98,THREE_OF_A_KIND",
            "23432,TWO_PAIR",
            "A23A4,ONE_PAIR",
            "23456,HIGH_CARD",
    })
    void test_types(String cards, Type expected) {
        assertEquals(expected, typeOf(cards));
    }

    List<Player> parse(String input) {
        return Arrays.stream(input.split("\r?\n"))
                .map(s -> s.split(" "))
                .map(Player::new)
                .toList();
    }

    private Player getPlayer(String[] split) {
        return new Player(split[0], Integer.parseInt(split[1]), typeOf(split[0]));
    }

    int partOne(String input) {
        List<Player> players = parse(input).stream().sorted(Player.COMP).toList();
        int result = 0;
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            result += (i + 1) * player.bet();
        }
        return result;
    }

    @Test
    void test_partOneRank() {
        var sorted = parse(EXAMPLE_INPUT).stream().sorted(Player.COMP).map(Player::bet).toList();
        // 765 * 1 + 220 * 2 + 28 * 3 + 684 * 4 + 483 * 5
        assertEquals(List.of(765, 220, 28, 684, 483), sorted);
    }

    /**
     * So, 33332 and 2AAAA are both four of a kind hands, but 33332 is stronger because its first card is stronger.
     * Similarly, 77888 and 77788 are both a full house, but 77888 is stronger because its third card is stronger (and
     * both hands have the same first and second card).
     */
    @ParameterizedTest
    @CsvSource({
            "'2AAAA,33332'",
            "'77788,77888'",
    })
    void test_things(String asc) {
        var hands = List.of(asc.split(","));
        var sorted = hands.stream().map(Player::betNothing).sorted(Player.COMP).map(Player::cards).toList();
        assertEquals(hands, sorted);
    }

    @Test
    void test_partOne() {
        assertEquals(6440, partOne(EXAMPLE_INPUT));
    }

    @Test
    void submit_partOne() {
        // 254498603 >
        // 254408323 >
        // 253997137 >
        assertEquals(253866470, partOne(ACTUAL_INPUT));
    }

    enum Type {
        FIVE_OF_A_KIND {
            @Override
            boolean test(String cards) {
                return cards.chars().allMatch(i -> i == cards.charAt(0));
            }
        },
        FOUR_OF_A_KIND {
            @Override
            boolean test(String cards) {
                return cards.chars().boxed().collect(Collectors.groupingBy(Function.identity())).values().stream().anyMatch(l -> l.size() == 4);
            }
        },
        FULL_HOUSE {
            @Override
            boolean test(String cards) {
                return cards.chars().boxed().collect(Collectors.groupingBy(Function.identity())).values().stream()
                        .map(List::size)
                        .collect(Collectors.toSet())
                        // very important to be set here, as order doesn't matter
                        .equals(Set.of(2, 3));
            }
        },
        THREE_OF_A_KIND {
            @Override
            boolean test(String cards) {
                return cards.chars().boxed().collect(Collectors.groupingBy(Function.identity())).values().stream().anyMatch(l -> l.size() == 3);
            }
        },
        TWO_PAIR {
            @Override
            boolean test(String cards) {
                return cards.chars().boxed().collect(Collectors.groupingBy(Function.identity())).values().stream().filter(l -> l.size() == 2).count() == 2;
            }
        },
        ONE_PAIR {
            @Override
            boolean test(String cards) {
                return cards.chars().boxed().collect(Collectors.groupingBy(Function.identity())).values().stream().anyMatch(l -> l.size() == 2);
            }
        },
        HIGH_CARD {
            @Override
            boolean test(String cards) {
                return true;
            }
        },
        ;
        static final List<Type> VALUES = Arrays.asList(values());

        abstract boolean test(String cards);
    }

    record Player(String cards, int bet, Type type) {
        private static final int[] SPECIAL = new int[256];
        static final Comparator<Player> COMP = Comparator.comparing(Player::scoreRank)
                .thenComparing(p -> normalize(p.cards.charAt(0)))
                .thenComparing(p -> normalize(p.cards.charAt(1)))
                .thenComparing(p -> normalize(p.cards.charAt(2)))
                .thenComparing(p -> normalize(p.cards.charAt(3)))
                .thenComparing(p -> normalize(p.cards.charAt(4)));

        static {
            for (int i = '2'; i <= '9'; i++) {
                SPECIAL[i] = i - '0';
            }
            SPECIAL['A'] = 14;
            SPECIAL['K'] = 13;
            SPECIAL['Q'] = 12;
            SPECIAL['J'] = 11;
            SPECIAL['T'] = 10;
        }

        public Player(String[] split) {
            this(split[0], Integer.parseInt(split[1]), typeOf(split[0]));
        }

        static Player betNothing(String s) {
            return new Player(new String[]{s, "0"});
        }

        private static int normalize(char c) {
            int lookup = SPECIAL[c];
            if (lookup != 0) return lookup;
            throw new UnsupportedOperationException("failed to look up " + c);
        }

        int scoreRank() {
            return -type.ordinal();
        }
    }
}
