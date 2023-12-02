import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day04Cleanup {
    static final Pattern PAIR_DESCRIPTION = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");
    static final String EXAMPLE_INPUT = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8""";

    @SneakyThrows
    private String readValue() {
        return IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/p4.txt")), StandardCharsets.UTF_8);
    }

    boolean doesPairHaveOneFullyContainsAnother(String pairDescription) {
        var m = PAIR_DESCRIPTION.matcher(pairDescription);
        if (!m.find()) throw new IllegalArgumentException();
        int a_start = Integer.parseInt(m.group(1));
        int a_end = Integer.parseInt(m.group(2));
        int b_start = Integer.parseInt(m.group(3));
        int b_end = Integer.parseInt(m.group(4));

        boolean a_lower = (a_start + a_end) < (b_start + b_end);
        
        int l_start = a_lower ? a_start : b_start;
        int l_end = a_lower ? a_end : b_end;
        int h_start = a_lower ? b_start : a_start;
        int h_end = a_lower ? b_end : a_end;

        if (l_start >= h_start) {
            //    bs..as ---- .....ae..be = 1; be..ae = 0
            return l_end <= h_end;
        } else {
            //    as..bs ---- .....ae..be = 0; be..ae = 1
            return h_end <= l_end;
        }
    }

    @Test
    void test_doesPairHaveOneFullyContainsAnother() {
        String[] split = EXAMPLE_INPUT.split("\\s");
        for (int i = 0; i < split.length; i++) {
            assertEquals(i == 3 || i == 4, doesPairHaveOneFullyContainsAnother(split[i]));
        }
    }

    int howManyPairsOverlap(List<String> pairs) {
        return (int) pairs.stream().filter(this::doesPairHaveOneFullyContainsAnother).count();
    }

    @Test
    void test_howManyPairsHaveOneFullyContainsAnother() {
        assertEquals(2, howManyPairsOverlap(Arrays.asList(EXAMPLE_INPUT.split("\\s"))));
    }
    
    @Test
    void submitPart1() {
        assertEquals(560, howManyPairsOverlap(Arrays.asList(readValue().split("\\s"))));
    }
}
