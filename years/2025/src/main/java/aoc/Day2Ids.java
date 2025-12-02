package aoc;

public class Day2Ids {
    long sumInvalidIds(String ranges, boolean newInvalidId) {
        long total = 0;
        for (String s : ranges.split(",")) {
            String[] parts = s.split("-");
            var min = Long.parseLong(parts[0]);
            var max = Long.parseLong(parts[1]);

            for (var i = min; i <= max; i++) {
                if (newInvalidId) {
                    if (isInvalidIdNew(i))
                        total += i;
                } else {
                    if (isInvalidId(i))
                        total += i;
                }
            }
        }
        return total;
    }

    boolean isInvalidId(long value) {
        var stringValue = String.valueOf(value);
        if (stringValue.length() % 2 != 0) {
            return false;
        }

        return stringValue.substring(0, stringValue.length() / 2)
                .equals(stringValue.substring(stringValue.length() / 2));
    }

    boolean isInvalidIdNew(long value) {
        var stringValue = String.valueOf(value);
        for (int i = 1; i < stringValue.length(); i++) {
            if (stringValue.length() % i != 0) {
                continue;
            }

            String firstPart = stringValue.substring(0, i);
            var instances = stringValue.length() / i;
            boolean allMatch = true;
            for (int j = 0; j < instances; j++) {
                if (!stringValue.substring(j * i, (j + 1) * i).equals(firstPart)) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) {
                return true;
            }
        }
        return false;
    }
}
