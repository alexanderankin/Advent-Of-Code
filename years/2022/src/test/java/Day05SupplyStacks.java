import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day05SupplyStacks {
    static final String EXAMPLE_INPUT = """
                [D]   \s
            [N] [C]   \s
            [Z] [M] [P]
             1   2   3\s
                        
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
            """;
    static final Pattern MOVE_PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    @SneakyThrows
    String readValue() {
        return IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/p5.txt")), StandardCharsets.UTF_8);
    }

    String finalSurfaceAfterRearranging(String input) {
        String[] rows = input.split("\\r?\\n");
        boolean parsingStacks = true;
        List<List<String>> stacks = new ArrayList<>();
        for (String row : rows) {
            if (parsingStacks) {
                if (!row.contains("[")) {
                    parsingStacks = false;
                    stacks.forEach(Collections::reverse);
                    continue;
                }

                int column = -1;
                var chars = row.toCharArray();
                for (int i = 1; i < chars.length; i += 4) {
                    column++;
                    if (chars[i] == ' ') continue;
                    while (stacks.size() < (column + 1))
                        stacks.add(new ArrayList<>());
                    stacks.get(column).add(String.valueOf(chars[i]));
                }
            } else {
                if (row.isBlank()) continue;
                System.out.println(row);
                Matcher matcher = MOVE_PATTERN.matcher(row);
                if (!matcher.find()) throw new IllegalArgumentException();
                int count = Integer.parseInt(matcher.group(1));
                int src = Integer.parseInt(matcher.group(2)) - 1;
                int dest = Integer.parseInt(matcher.group(3)) - 1;

                for (int i = 0; i < count; i++) {
                    stacks.get(dest).add(stacks.get(src).remove(stacks.get(src).size() - 1));
                }
            }
        }


        return stacks.stream().map(e -> e.get(e.size() - 1)).collect(Collectors.joining());
    }

    @Test
    void test_finalSurfaceAfterRearranging() {
        assertEquals("CMZ", finalSurfaceAfterRearranging(EXAMPLE_INPUT));
    }

    @Test
    void submit_finalSurfaceAfterRearranging() {
        assertEquals("ZSQVCCJLL", finalSurfaceAfterRearranging(readValue()));
    }
}
