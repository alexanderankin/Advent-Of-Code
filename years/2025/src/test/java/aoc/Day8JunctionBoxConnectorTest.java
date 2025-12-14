package aoc;

import org.junit.jupiter.api.Test;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.*;

class Day8JunctionBoxConnectorTest {

    static final String TEST_INPUT = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689""";

    static final String INPUT = read("day08.txt").trim();

    Day8JunctionBoxConnector day8 = new Day8JunctionBoxConnector();

    @Test
    void test() {
        assertEquals(new Day8JunctionBoxConnector.JunctionBoxConfig(40, 25272), day8.connect(TEST_INPUT, Day8JunctionBoxConnector.Settings.TEST));
    }

    @Test
    void input() {
        assertEquals(new Day8JunctionBoxConnector.JunctionBoxConfig(123930, 27338688), day8.connect(INPUT, Day8JunctionBoxConnector.Settings.INPUT));
    }

}
