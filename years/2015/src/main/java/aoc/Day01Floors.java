package aoc;

public class Day01Floors {
    int whatFloor(String directions) {
        int result = 0;
        for (char c : directions.toCharArray()) {
            if (c == '(')
                result += 1;
            else if (c == ')')
                result -= 1;
        }
        return result;
    }

    int firstBasementDirection(String directions) {
        int result = 0;
        char[] charArray = directions.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == '(')
                result += 1;
            else if (c == ')')
                result -= 1;
            if (result < 0)
                return i + 1;
        }
        return -1;
    }
}
