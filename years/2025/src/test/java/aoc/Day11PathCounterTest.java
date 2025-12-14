package aoc;

import org.junit.jupiter.api.Test;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11PathCounterTest {

    static final String TEST_INPUT = """
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out""";

    static final String TEST_INPUT2 = """
            svr: aaa bbb
            aaa: fft
            fft: ccc
            bbb: tty
            tty: ccc
            ccc: ddd eee
            ddd: hub
            hub: fff
            eee: dac
            dac: fff
            fff: ggg hhh
            ggg: out
            hhh: out""";

    static final String INPUT = read("day11.txt");

    Day11PathCounter day11 = new Day11PathCounter();

    @Test
    void test_graphParse() {
        Day11PathCounter.Graph parse = Day11PathCounter.Graph.parse(TEST_INPUT);
        System.out.println(parse);
    }

    @Test
    void test() {
        assertEquals(5, day11.countRoutesFromYouToOut(Day11PathCounter.Graph.parse(TEST_INPUT)));
    }

    @Test
    void input() {
        assertEquals(634, day11.countRoutesFromYouToOut(Day11PathCounter.Graph.parse(INPUT)));
    }

    @Test
    void testP2() {
        assertEquals(2, day11.countRoutesFromSvrToOutWithDacAndFft(Day11PathCounter.Graph.parse(TEST_INPUT2)));
    }

    @Test
    void inputP2() {
        assertEquals(377452269415704L, day11.countRoutesFromSvrToOutWithDacAndFft(Day11PathCounter.Graph.parse(INPUT)));
    }

}
