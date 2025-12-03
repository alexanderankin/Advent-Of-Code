package aoc;

import java.util.*;

public class Day03DeliveryTracker {
    int housesNaive(String directions) {
        int[] xy = new int[2];
        Set<String> visited = new HashSet<>();
        visited.add(xy[0] + "/" + xy[1]);
        for (char c : directions.toCharArray()) {
            switch (c) {
                case '>' -> xy[0] += 1;
                case '<' -> xy[0] -= 1;
                case '^' -> xy[1] += 1;
                case 'v' -> xy[1] -= 1;
            }

            visited.add(xy[0] + "/" + xy[1]);
        }
        return visited.size();
    }

    int roboHelper(String directions) {
        int[] xy = new int[2];
        int[] xyHelper = new int[2];
        Set<String> visited = new HashSet<>();
        visited.add(xy[0] + "/" + xy[1]);
        visited.add(xyHelper[0] + "/" + xyHelper[1]);

        boolean isHelper = false;
        for (char c : directions.toCharArray()) {
            if (isHelper) {
                switch (c) {
                    case '>' -> xyHelper[0] += 1;
                    case '<' -> xyHelper[0] -= 1;
                    case '^' -> xyHelper[1] += 1;
                    case 'v' -> xyHelper[1] -= 1;
                }
                visited.add(xyHelper[0] + "/" + xyHelper[1]);
            } else {
                switch (c) {
                    case '>' -> xy[0] += 1;
                    case '<' -> xy[0] -= 1;
                    case '^' -> xy[1] += 1;
                    case 'v' -> xy[1] -= 1;
                }
                visited.add(xy[0] + "/" + xy[1]);
            }
            isHelper = !isHelper;
        }
        return visited.size();
    }
}
