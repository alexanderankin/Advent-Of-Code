package aoc;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day3CorruptedInstructions {

    static final Pattern PATTERN = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
    static final Pattern PATTERN_WITH_ENABLING = Pattern.compile("(do\\(\\)|don't\\(\\)|mul\\((\\d+),(\\d+)\\))");

    int sumValidMulInstructions(String data) {
        // xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))

        return PATTERN.matcher(data).results()
                .mapToInt(m -> Integer.parseInt(m.group(1)) * Integer.parseInt(m.group(2)))
                .sum();
    }

    int sumWithEnabling(String data) {
        // xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))

        int total = 0;
        boolean enabled = true;
        for (MatchResult matchResult : PATTERN_WITH_ENABLING.matcher(data).results().toList()) {
            if (matchResult.group(1).equals("do()")) {
                enabled = true;
            } else if (matchResult.group(1).equals("don't()")) {
                enabled = false;
            } else if (enabled) {
                total += Integer.parseInt(matchResult.group(2)) * Integer.parseInt(matchResult.group(3));
            }
        }
        return total;
    }
}
