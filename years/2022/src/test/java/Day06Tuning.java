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

    int findMarkerPacket(String message) {
        return findMarker(message, 4);
    }

    int findMarkerMessage(String message) {
        return findMarker(message, 14);
    }

    int findMarker(String message, int upTo) {
        var chars = message.toCharArray();

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
        assertEquals(expected, findMarkerPacket(message));
    }

    @Test
    void submit_findMarker() {
        assertEquals(1542, findMarkerPacket(readValue()));
    }

    @ParameterizedTest
    @CsvSource({
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb,19",
            "bvwbjplbgvbhsrlpgdmjqwftvncz,23",
            "nppdvjthqldpwncqszvftbrmjlhg,23",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,29",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,26",
    })
    void test_findMarkerMessage(String message, int expected) {
        assertEquals(expected, findMarkerMessage(message));
    }

    @Test
    void submit_findMarkerMessage() {
        assertEquals(3153, findMarkerMessage(readValue()));
    }
}
