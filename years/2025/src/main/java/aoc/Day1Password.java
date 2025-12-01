package aoc;

import java.util.List;

public class Day1Password {
    int start = 50;
    PasswordMethod method = PasswordMethod.POINT_TO_ZERO;

    public int password(String input) {
        return switch (method) {
            case POINT_TO_ZERO -> passwordPoints(input.lines().toList());
            case CLICKS_PAST_ZERO -> passwordClicks(input.lines().toList());
        };
    }

    private int passwordPoints(List<String> list) {
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

    /**
     * <pre>
     * dial, count1, count2 = 50, 0, 0
     * for sign, distance in rotations:
     *     count2 -= (sign == -1 and dial == 0)
     *     div, dial = divmod(dial+distance*sign, 100)
     *     count1 += dial == 0
     *     count2 += abs(div) + (sign == -1 and dial == 0)
     * print((count1, count2))
     * </pre>
     */
    private int passwordClicks(List<String> list) {
        int dial = 50, count1 = 0, count2 = 0;
        for (String s : list) {
            int sign = s.charAt(0) == 'L' ? -1 : 1;
            int distance = Integer.parseInt(s.substring(1));
            if (sign == -1 && dial == 0)
                count2 -= 1;

            int div = Math.floorDiv((dial + (distance * sign)), 100);
            dial = Math.floorMod((dial + (distance * sign)), 100);

            if (dial == 0)
                count1++;
            count2 += Math.abs(div) + (sign == -1 && dial == 0 ? 1 : 0);
        }

        return count2;
    }

    public enum PasswordMethod {
        POINT_TO_ZERO,
        CLICKS_PAST_ZERO,
    }
}
