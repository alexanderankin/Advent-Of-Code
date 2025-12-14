package aoc;

import aoc.Day10MachineButtonCalculator.LightDiagram;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static aoc.TestUtils.read;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10MachineButtonCalculatorTest {

    static final String TEST_INPUT = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}""";
    static final String INPUT = read("day10.txt");

    Day10MachineButtonCalculator day10 = new Day10MachineButtonCalculator();

    @Test
    void test_parsing() {
        assertEquals(
                new LightDiagram(
                        List.of(false, true, true, false),
                        List.of(List.of(3), List.of(1, 3), List.of(2), List.of(2, 3), List.of(0, 2), List.of(0, 1)),
                        List.of(3, 5, 4, 7)),
                LightDiagram.parse(TEST_INPUT.lines().findFirst().orElseThrow()));
    }

    @Test
    void testSingleMachine() {
        assertEquals(2, day10.minPresses(LightDiagram.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}")));
    }

    @Test
    void test() {
        assertEquals(7, day10.minPresses(TEST_INPUT));
    }

    @Test
    void input() {
        assertEquals(535, day10.minPresses(INPUT));
    }

    @ParameterizedTest
    @CsvSource({
            "'[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}', 10",
            "'[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}', 12",
            "'[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}', 11",
    })
    void testSingleAccurateMachine(String diagram, int expected) {
        assertEquals(expected, day10.minPressesAccurate(LightDiagram.parse(diagram)));
    }

    @Test
    void inputP2() {
        assertEquals(21021, day10.minPressesAccurate(INPUT));
    }

    @Test
    void test_inputP2() {
        assertEquals(88, day10.minPressesAccurate(INPUT.lines().findFirst().orElseThrow()));
    }
}
