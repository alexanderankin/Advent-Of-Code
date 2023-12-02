import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day01CalorieCounting {
    String example = """
            1000
            2000
            3000
                        
            4000
                        
            5000
            6000
                        
            7000
            8000
            9000
                        
            10000
            """;

    int elfMostFood(List<List<Integer>> elfFood) {
        // int withMost = 0;
        int most = 0;
        for (List<Integer> foods : elfFood) {
            int current = foods.stream().mapToInt(Integer::intValue).sum();
            if (current > most) {
                // withMost = i;
                most = current;
            }
        }
        return most;
    }

    @Test
    void test_elfMostFood() {
        assertEquals(24000, elfMostFood(parseList(example)));
    }

    private List<List<Integer>> parseList(String example) {
        return Arrays.stream(example.split("\n\n")).map(e -> Arrays.stream(e.split("\n")).map(Integer::parseInt).toList()).toList();
    }

    @SneakyThrows
    String readValue() {
        return IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/p1.elf-foods.txt")), StandardCharsets.UTF_8);
    }

    @Test
    void submit_elfMostFood() {
        System.out.println(elfMostFood(parseList(readValue())));
    }
}
