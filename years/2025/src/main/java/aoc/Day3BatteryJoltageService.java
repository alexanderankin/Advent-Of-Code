package aoc;

public class Day3BatteryJoltageService {
    long sumMaxJoltage(String banks) {
        return banks.lines()
                .mapToLong(this::maxJoltage)
                .sum();
    }

    private long maxJoltage(String s) {
        var arr = s.toCharArray();

        int largestIndex = 0;
        int largest = arr[0];
        for (int i = 0; i < arr.length - 1; i++) {
            if (largest < arr[i]) {
                largest = arr[i];
                largestIndex = i;
            }
        }

        int largestSecondIndex = largestIndex + 1;
        int largestSecond = arr[largestSecondIndex];
        for (int i = largestSecondIndex + 1; i < arr.length; i++) {
            if (largestSecond < arr[i]) {
                largestSecond = arr[i];
                largestSecondIndex = i;
            }
        }

        return ((arr[largestIndex] - '0') * 10) + (arr[largestSecondIndex] - '0');
    }

    long sumMaxJoltage12(String banks) {
        return banks.lines()
                .mapToLong(this::maxJoltage12)
                .sum();
    }

    private long maxJoltage12(String s) {
        char[] arr = s.toCharArray();
        int[] indexes = new int[12];
        pickDigits(arr, 0, arr.length, 12, indexes, 0);

        int[] digitsArray = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            digitsArray[i] = arr[i] - '0';

        return intArrayToLong(digitsArray, indexes);
    }

    // gpt code - just this method lol
    private void pickDigits(char[] arr, int start, int end, int remaining,
                            int[] outIndexes, int outPos) {
        if (remaining == 0)
            return;

        // max allowed window is: [start, end - remaining]
        int maxIdx = indexOfMaxInSubArray(arr, start, end - remaining + 1);
        outIndexes[outPos] = maxIdx;

        // recurse to choose next digit after maxIdx
        pickDigits(arr, maxIdx + 1, end, remaining - 1, outIndexes, outPos + 1);
    }

    int indexOfMaxInSubArray(char[] arr, int start, int max) {
        int result = arr[start];
        int resultIndex = start;
        for (int i = start; i < max; i++) {
            if (result < arr[i]) {
                result = arr[i];
                resultIndex = i;
            }
        }
        return resultIndex;
    }

    long intArrayToLong(int[] arr, int[] indexes) {
        long result = 0;

        for (int index : indexes) {
            int j = arr[index];
            result = result * 10 + j;
        }

        return result;
    }
}
