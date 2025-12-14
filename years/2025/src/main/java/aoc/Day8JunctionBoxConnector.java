package aoc;

import java.util.*;

public class Day8JunctionBoxConnector {
    public JunctionBoxConfig connect(String boxesList, Settings settings) {
        return connect(boxesList.lines().toList(), settings);
    }

    public JunctionBoxConfig connect(List<String> boxes, Settings settings) {
        return connectBoxes(boxes.stream().map(Vec::parse).toList(), settings);
    }

    public JunctionBoxConfig connectBoxes(List<Vec> boxes, Settings settings) {
        Set<Distance> distancesSet = new HashSet<>();

        for (int i = 0; i < boxes.size(); i++) {
            for (int j = 0; j < boxes.size(); j++) {
                if (i == j)
                    continue;

                distancesSet.add(Distance.calculate(boxes.get(i), boxes.get(j)));
            }
        }

        var distances = distancesSet.stream().sorted(Comparator.comparing(Distance::distance)).toList();

        List<Set<Vec>> circuits = new ArrayList<>();

        for (Distance distance : distances.subList(0, settings.connectClosestN())) {
            List<Set<Vec>> containing = new ArrayList<>();
            for (Set<Vec> circuit : circuits) {
                if (circuit.contains(distance.a()) || circuit.contains(distance.b())) {
                    containing.add(circuit);
                }
            }

            List<Vec> newConnection = List.of(distance.a(), distance.b());
            if (containing.isEmpty()) {
                circuits.add(new HashSet<>(newConnection));
            } else if (containing.size() == 1) {
                containing.getFirst().addAll(newConnection);
            } else {
                List<Set<Vec>> rest = containing.subList(1, containing.size());
                circuits.removeIf(v -> {
                    for (Set<Vec> vecs : rest)
                        if (v == vecs) return true;
                    return false;
                });

                for (Set<Vec> vecs : rest) {
                    containing.getFirst().addAll(vecs);
                }
                containing.getFirst().addAll(newConnection);
            }
        }

        circuits.sort(Comparator.comparing(s -> -s.size()));

        var top3 = circuits.subList(0, 3);

        int multipliedSizeOfLargest3Circuits = top3.stream().mapToInt(Set::size).reduce((a, b) -> a * b).orElseThrow();

        Distance distance = null;
        List<Distance> rest = distances.subList(settings.connectClosestN() + 1, distances.size());
        for (var restIter = rest.iterator(); circuits.size() > 1 || circuits.getFirst().size() < boxes.size(); ) {
            distance = restIter.next();

            List<Set<Vec>> containing = new ArrayList<>();
            for (Set<Vec> circuit : circuits) {
                if (circuit.contains(distance.a()) || circuit.contains(distance.b())) {
                    containing.add(circuit);
                }
            }

            List<Vec> newConnection = List.of(distance.a(), distance.b());
            if (containing.isEmpty()) {
                circuits.add(new HashSet<>(newConnection));
            } else if (containing.size() == 1) {
                containing.getFirst().addAll(newConnection);
            } else {
                List<Set<Vec>> rest1 = containing.subList(1, containing.size());
                circuits.removeIf(v -> {
                    for (Set<Vec> vecs : rest1)
                        if (v == vecs) return true;
                    return false;
                });

                for (Set<Vec> vecs : rest1) {
                    containing.getFirst().addAll(vecs);
                }
                containing.getFirst().addAll(newConnection);
            }
        }

        return new JunctionBoxConfig(multipliedSizeOfLargest3Circuits, distance.a().x() * distance.b().x());
    }

    public record Settings(int connectClosestN) {
        public static final Settings TEST = new Settings(10);
        public static final Settings INPUT = new Settings(1000);
    }

    public record Vec(int x, int y, int z) {
        public static Vec parse(String box) {
            var coords = box.split(",");
            return new Vec(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
        }
    }

    public record Distance(Vec a, Vec b, int distance) {
        private static final Comparator<Vec> VEC_COMPARATOR = Comparator.comparing(Vec::x).thenComparing(Vec::y).thenComparing(Vec::z);

        public static Distance calculate(Vec a, Vec b) {
            if (VEC_COMPARATOR.compare(a, b) > 0) {
                var temp = b;
                b = a;
                a = temp;
            }
            return new Distance(a, b, (int) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2) + Math.pow(a.z - b.z, 2)));
        }
    }

    public record JunctionBoxConfig(int multipliedSizeOfLargest3Circuits,
                                    int multipliedXCoordsOfBoxesCreatingSingleCircuit) {
    }
}
