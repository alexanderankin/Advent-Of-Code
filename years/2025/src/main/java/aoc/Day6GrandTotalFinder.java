package aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6GrandTotalFinder {
    public long findGrandTotal(String input) {
        var lines = input.lines().toList();
        var numbers = lines.subList(0, lines.size() - 1)
                .stream()
                .map(l -> Arrays.stream(l.trim().split("\\s+")).map(Long::parseLong).toList())
                .toList();

        var ops = lines.getLast().split("\\s+");

        List<Long> totals = new ArrayList<>();
        for (int i = 0; i < ops.length; i++) {
            ops[i] = ops[i].trim();
            int finalI = i;
            if (ops[i].equals("*")) {
                totals.add(numbers.stream().mapToLong(row -> row.get(finalI)).reduce((a, b) -> a * b).orElseThrow());
            } else if (ops[i].equals("+")) {
                totals.add(numbers.stream().mapToLong(row -> row.get(finalI)).sum());
            }
        }

        return totals.stream().mapToLong(Long::longValue).sum();
    }

    public long findGrandTotalVertical(String input) {
        String[] lines = input.split("\n");
        char[][] charMatrix = new char[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            charMatrix[i] = lines[i].toCharArray();
        }

        int matrixWidth = charMatrix[0].length;

        List<Long> totals = new ArrayList<>();

        int lastBlank = -1;
        for (int i = 0; i < matrixWidth; i++) {
            boolean blank = true;
            for (int i1 = 0; i1 < charMatrix.length; i1++) {
                if (!Character.isWhitespace(charMatrix[i1][i])) {
                    blank = false;
                    break;
                }
            }

            if (blank || i == matrixWidth - 1) {
                List<Long> arguments = new ArrayList<>();
                StringBuilder num = new StringBuilder();
                for (int j = lastBlank + 1; j <= i; j++) {
                    num.setLength(0);
                    for (int i1 = 0; i1 < charMatrix.length - 1; i1++) {
                        num.append(charMatrix[i1][j]);
                    }

                    String trim = num.toString().trim();
                    if (!trim.isEmpty()) {
                        arguments.add(Long.parseLong(trim));
                    }
                }


                var op = new String(charMatrix[charMatrix.length - 1], lastBlank + 1, i - lastBlank).trim();
                // System.out.println("processing arguments: " + arguments + " with operation: " + op);
                if (op.equals("*")) {
                    totals.add(arguments.stream().mapToLong(Long::longValue).reduce((a, b) -> a * b).orElseThrow());
                } else if (op.equals("+")) {
                    totals.add(arguments.stream().mapToLong(Long::longValue).sum());
                }

                lastBlank = i;
            }
        }

        // System.out.println("adding up: " + totals);
        return totals.stream().mapToLong(Long::longValue).sum();
    }
}
