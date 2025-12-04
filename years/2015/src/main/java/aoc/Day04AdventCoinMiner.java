package aoc;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.HexFormat;
import java.util.stream.Collectors;

public class Day04AdventCoinMiner {
    int seedOf(String key, int zeroes) {
        int seed = 1;
        String hash = hash(key + seed);
        int attempts = 1_000_000_000;
        String zeroesString = String.join("", Collections.nCopies(zeroes, "0"));
        while (!hash.startsWith(zeroesString)) {
            seed++;
            hash = hash(key + seed);
            if (attempts-- < 0) {
                throw new IllegalStateException();
            }
        }
        return seed;
    }

    private String hash(String s) {
        try {
            return unsafeHash(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String unsafeHash(String s) throws Exception {
        return HexFormat.of().formatHex(MessageDigest.getInstance("MD5").digest(s.getBytes(StandardCharsets.UTF_8)));
    }
}
