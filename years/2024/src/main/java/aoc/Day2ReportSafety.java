package aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2ReportSafety {

    int countSafeReports(String stringReports) {
        return countSafeReports(stringReports, false, false);
    }

    int countSafeReports(String stringReports, boolean dampener) {
        return countSafeReports(stringReports, dampener, false);
    }

    int countSafeReports(String stringReports, boolean dampener, boolean fast) {
        var reports = stringReports.lines()
                .map(s -> Arrays.stream(s.split(" ")).map(Integer::parseInt).toList())
                .toList();

        int total = 0;
        for (List<Integer> report : reports) {
            if (fast) {
                total += (dampener ? isSafeDamp(report) : isSafe(report)) ? 1 : 0;
                continue;
            }

            boolean safe = isSafe(report);
            System.out.println("report " + report + " is " + (safe ? "Safe" : "Unsafe"));
            if (safe) {
                total++;
            } else if (dampener) {
                if (dampened(report).stream()
                        .anyMatch(this::isSafe))
                    total++;
            }
        }

        return total;
    }

    List<List<Integer>> dampened(List<Integer> report) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < report.size(); i++) {
            result.add(new ArrayList<>());
            for (int i1 = 0; i1 < report.size(); i1++) {
                if (i1 != i)
                    result.getLast().add(report.get(i1));
            }
        }
        return result;
    }

    boolean isSafe(List<Integer> report) {
        boolean safe = true;
        boolean increasing = report.get(1) > report.get(0);
        for (int i = 1; i < report.size(); i++) {
            Integer lastItem = report.get(i - 1);
            Integer reportItem = report.get(i);

            int abs = (reportItem - lastItem) * (increasing ? 1 : -1);
            if (!(abs >= 1 && abs <= 3)) {
                safe = false;
                break;
            }
        }
        return safe;
    }

    // todo figure this out
    boolean isSafeDamp(List<Integer> report) {
        boolean skipped = false;
        boolean safe = true;
        boolean increasing = report.get(1) > report.get(0);
        for (int i = 1; i < report.size(); i++) {
            Integer lastItem = report.get(i - 1);
            Integer reportItem = report.get(i);

            int abs = (reportItem - lastItem) * (increasing ? 1 : -1);
            if (!(abs >= 1 && abs <= 3)) {

                // if we can skip try it
                if (!skipped) {
                    skipped = true;
                    if (i == 1) {
                        // i += 1;
                        continue;
                    }

                    if (i == report.size() - 1) {
                        return true;
                    }

                    abs = (report.get(i + 1) - lastItem) * (increasing ? 1 : -1);
                    if (!(abs >= 1 && abs <= 3)) {
                        return false;
                    }

                    // i += 1;
                } else {
                    return false;
                }
            }
        }
        return safe;
    }
}
