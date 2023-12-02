import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day06Tuning {
    @SneakyThrows
    String readValue() {
        return IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/p6.1.txt")), StandardCharsets.UTF_8);
    }

    int findMarker(String message) {
        var chars = message.toCharArray();
        var upTo = 4;

        LinkedHashSet<Character> c = new LinkedHashSet<>();
        for (int i = 0, length = chars.length; i < length; i++) {
            char aChar = chars[i];
            if (c.add(aChar)) {
                if (c.size() >= upTo) {
                    return i + 1;
                }
            } else {
                Iterator<Character> iterator = c.iterator();
                char next;
                do {
                    next = iterator.next();
                    iterator.remove();
                } while (next != aChar);
                c.add(aChar);
            }
        }
        throw new IllegalArgumentException();
    }

    @ParameterizedTest
    @CsvSource({
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb,7",
            "bvwbjplbgvbhsrlpgdmjqwftvncz,5",
            "nppdvjthqldpwncqszvftbrmjlhg,6",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,10",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,11",
    })
    void test_findMarker(String message, int expected) {
        assertEquals(expected, findMarker(message));
    }

    @Test
    void submit_findMarker() {
        assertEquals(1542, findMarker(readValue()));
    }
}
