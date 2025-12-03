package aoc;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02WrappingPaperCalculator {
    private final static Pattern present =
            Pattern.compile("(\\d+)x(\\d+)x(\\d+)");

    int wrappingPaperFor(String presents) {
        return presents.lines()
                .mapToInt(line -> {
                    Matcher matcher = present.matcher(line);
                    if (!matcher.find())
                        throw new IllegalArgumentException();
                    return paperForPresent(
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3))
                    );
                })
                .sum();
    }

    int paperForPresent(int h, int l, int w) {
        var wrap = (2 * l * w) + (2 * w * h) + (2 * h * l);
        var slack = Collections.min(List.of(h * l, l * w, w * h));
        return wrap + slack;
    }

    int ribbonFor(String presents) {
        return presents.lines()
                .mapToInt(line -> {
                    Matcher matcher = present.matcher(line);
                    if (!matcher.find())
                        throw new IllegalArgumentException();
                    return ribbonForPresent(
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3))
                    );
                })
                .sum();
    }

    int ribbonForPresent(int h, int l, int w) {
        var bow = h * l * w;
        var perimeter = 2 * Collections.min(List.of(h + l, l + w, w + h));
        return bow + perimeter;
    }
}
