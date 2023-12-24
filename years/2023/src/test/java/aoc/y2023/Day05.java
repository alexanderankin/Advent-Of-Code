package aoc.y2023;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day05 {
    static final String EXAMPLE_INPUT = """
            seeds: 79 14 55 13
                        
            seed-to-soil map:
            50 98 2
            52 50 48
                        
            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15
                        
            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4
                        
            water-to-light map:
            88 18 7
            18 25 70
                        
            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13
                        
            temperature-to-humidity map:
            0 69 1
            1 0 69
                        
            humidity-to-location map:
            60 56 37
            56 93 4""";

    private static long lookupIntInData(long needle, long[][] dataNums) {
        for (long[] dataNum : dataNums) {
            long rel = needle - dataNum[1];
            if (rel >= 0 && rel < dataNum[2]) {
                return dataNum[0] + rel;
            }
        }
        return needle;
    }

    @SuppressWarnings("ALL")
    @SneakyThrows
    String readValue() {
        return new String(getClass()
                .getResourceAsStream("/p5.txt").readAllBytes(), StandardCharsets.UTF_8);
    }

    long seedSoilLookup(Data data, long seedNumber) {
        return lookupIntInData(seedNumber, data.getParsed_seedToSoil());
    }

    @ParameterizedTest
    @CsvSource({
            "79,81",
            "14,14",
            "55,57",
            "13,13",
    })
    void test_seedSoilLookup(int seedNumber, int expectedSoil) {
        assertEquals(expectedSoil, seedSoilLookup(new Data(EXAMPLE_INPUT), seedNumber));
    }

    long seedLocationLookup(Data data, long seedNumber) {
        long soil = lookupIntInData(seedNumber, data.getParsed_seedToSoil());
        long fertilizer = lookupIntInData(soil, data.getParsed_soilToFertilizer());
        long water = lookupIntInData(fertilizer, data.getParsed_fertilizerToWater());
        long light = lookupIntInData(water, data.getParsed_waterToLight());
        long temp = lookupIntInData(light, data.getParsed_lightToTemperature());
        long hum = lookupIntInData(temp, data.getParsed_temperatureToHumidity());
        return lookupIntInData(hum, data.getParsed_humidityToLocation());
    }

    @ParameterizedTest
    @CsvSource({
            "79,81,81,81,74,78,78,82",
            "14,14,53,49,42,42,43,43",
            "55,57,57,53,46,82,82,86",
            "13,13,52,41,34,34,35,35",
    })
    void test_seedLocationLookup_Implementation(
            int seed,
            int soil,
            int fertilizer,
            int water,
            int light,
            int temperature,
            int humidity,
            int location
    ) {
        var data = new Data(EXAMPLE_INPUT);
        long result = lookupIntInData(seed, data.getParsed_seedToSoil());
        assertEquals(soil, result);
        result = lookupIntInData(result, data.getParsed_soilToFertilizer());
        assertEquals(fertilizer, result);
        result = lookupIntInData(result, data.getParsed_fertilizerToWater());
        assertEquals(water, result);
        result = lookupIntInData(result, data.getParsed_waterToLight());
        assertEquals(light, result);
        result = lookupIntInData(result, data.getParsed_lightToTemperature());
        assertEquals(temperature, result);
        result = lookupIntInData(result, data.getParsed_temperatureToHumidity());
        assertEquals(humidity, result);
        result = lookupIntInData(result, data.getParsed_humidityToLocation());
        assertEquals(location, result);
    }

    @ParameterizedTest
    @CsvSource({
            "79,82",
            "14,43",
            "55,86",
            "13,35",
    })
    void test_seedLocationLookup(int seedNumber, int expectedLocation) {
        assertEquals(expectedLocation,
                seedLocationLookup(new Data(EXAMPLE_INPUT), seedNumber));
    }

    long part1(Data data) {
        long[] seeds = Arrays.stream(data.getLines()[0]
                        .split(": ")[1]
                        .split("\\s"))
                .mapToLong(Long::parseLong)
                .toArray();
        long lowestLocation = seedLocationLookup(data, seeds[0]);
        for (int i = 1; i < seeds.length; i++) {
            long next = seedLocationLookup(data, seeds[i]);
            if (next < lowestLocation) {
                lowestLocation = next;
            }
        }
        return lowestLocation;
    }

    @Test
    void test_part1() {
        assertEquals(35, part1(new Data(EXAMPLE_INPUT)));
    }

    @Test
    void submit_part1() {
        assertEquals(910845529, part1(new Data(readValue())));
    }

    long part2_naiveMemory(Data data) {
        long[] seedNums = Arrays.stream(data.getLines()[0]
                        .split(": ")[1]
                        .split("\\s"))
                .mapToLong(Long::parseLong)
                .toArray();
        // part 2 is now we consider this is as start/length ranges not 4 longs
        long[] seedsInRange0 = LongStream.range(seedNums[0], seedNums[0] + seedNums[1]).toArray();
        long[] seedsInRange2 = LongStream.range(seedNums[2], seedNums[2] + seedNums[3]).toArray();

        long[] seeds = new long[seedsInRange0.length + seedsInRange2.length];
        System.arraycopy(seedsInRange0, 0, seeds, 0, seedsInRange0.length);
        System.arraycopy(seedsInRange2, 0, seeds, seedsInRange0.length, seedsInRange2.length);

        long lowestLocation = seedLocationLookup(data, seeds[0]);
        for (int i = 1; i < seeds.length; i++) {
            long next = seedLocationLookup(data, seeds[i]);
            if (next < lowestLocation) {
                lowestLocation = next;
            }
        }
        return lowestLocation;
    }

    long part2(Data data) { // naive computationally
        long[] seeds = Arrays.stream(data.getLines()[0]
                        .split(": ")[1]
                        .split("\\s"))
                .mapToLong(Long::parseLong)
                .toArray();

        long lowestLocation = seedLocationLookup(data, seeds[0]);

        for (int i = 0; i < seeds.length; i+= 2) {
            for (long j = seeds[i]; j < seeds[i] + seeds[i + 1]; j++) {
                long next = seedLocationLookup(data, j);
                if (next < lowestLocation) lowestLocation = next;
            }
        }
        return lowestLocation;
    }

    @Test
    void test_part2() {
        assertEquals(46, part2(new Data(EXAMPLE_INPUT)));
        assertEquals(46, part2_naiveMemory(new Data(EXAMPLE_INPUT)));
    }

    @Test
    @Disabled("takes too long :) need to find a better solution")
    void submit_part2() {
        // 95461669 -- too high
        // 77435348
        assertEquals(77435348, part2(new Data(readValue())));
    }

    @lombok.Data
    @RequiredArgsConstructor
    static class Data {
        final String input;
        final String[] lines;
        final String seeds;
        final String seedToSoil;
        final String soilToFertilizer;
        final String fertilizerToWater;
        final String waterToLight;
        final String lightToTemperature;
        final String temperatureToHumidity;
        final String humidityToLocation;
        long[][] parsed_seeds;
        long[][] parsed_seedToSoil;
        long[][] parsed_soilToFertilizer;
        long[][] parsed_fertilizerToWater;
        long[][] parsed_waterToLight;
        long[][] parsed_lightToTemperature;
        long[][] parsed_temperatureToHumidity;
        long[][] parsed_humidityToLocation;

        Data(String input) {
            this(input, input.split("\r?\n\r?\n"));
            parse();
        }

        Data(String input, String[] lines) {
            this(input,
                    lines,
                    lines[0],
                    lines[1],
                    lines[2],
                    lines[3],
                    lines[4],
                    lines[5],
                    lines[6],
                    lines[7]
            );
        }

        void parse() {
            parse(Data::getSeeds, Data::setParsed_seeds);
            parse(Data::getSeedToSoil, Data::setParsed_seedToSoil);
            parse(Data::getSoilToFertilizer, Data::setParsed_soilToFertilizer);
            parse(Data::getFertilizerToWater, Data::setParsed_fertilizerToWater);
            parse(Data::getWaterToLight, Data::setParsed_waterToLight);
            parse(Data::getLightToTemperature, Data::setParsed_lightToTemperature);
            parse(Data::getTemperatureToHumidity, Data::setParsed_temperatureToHumidity);
            parse(Data::getHumidityToLocation, Data::setParsed_humidityToLocation);
        }

        void parse(Function<Data, String> getter, BiConsumer<Data, long[][]> setter) {
            String apply = getter.apply(this);

            String[] lines = apply.split("\r?\n");
            long[][] dataNums = new long[lines.length - 1][3];
            int i = 0;
            for (int j = 1; j < lines.length; j++) {
                String dataLine = lines[j];
                dataNums[i] = Arrays.stream(dataLine.split(" "))
                        .mapToLong(Long::parseLong)
                        .toArray();
                i++;
            }

            setter.accept(this, dataNums);
        }
    }
}
