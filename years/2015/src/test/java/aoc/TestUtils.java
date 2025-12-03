package aoc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

class TestUtils {
    static String read(String resource) {
        try (InputStream resourceAsStream = TestUtils.class.getResourceAsStream(resource)) {
            Objects.requireNonNull(resourceAsStream);
            return new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
