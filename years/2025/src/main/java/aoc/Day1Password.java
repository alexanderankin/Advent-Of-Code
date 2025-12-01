package aoc;

import java.util.List;

public class Day1Password {
    int start = 50;

    public int password(String input) {
        return password(input.lines().toList());
    }

    private int password(List<String> list) {
        int value = start;
        int result = 0;
        for (String rotation : list) {
            value = switch (rotation.charAt(0)) {
                case 'L' -> {
                    var move = value - Integer.parseInt(rotation.substring(1));
                    while (move < 0) move += 100;
                    yield move;
                }
                case 'R' -> {
                    var move = value + Integer.parseInt(rotation.substring(1));
                    while (move >= 100) move -= 100;
                    yield move;
                }
                default -> throw new UnsupportedOperationException();
            };

            if (value == 0)
                result += 1;
        }
        return result;
    }
}
