package util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListHelper {
    private static final int maxRandom = 1000;
    public static List<Integer> generateRandomNumbers(int size) {
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            nums.add((int) (Math.random() * maxRandom + 1));
        }
        return nums;
    }

    public static List<Integer> parseStringToList(String str) {
        List<Integer> nums = new ArrayList<>();
        String[] arr = str.split(",");
        for (String s : arr) {
            nums.add(Integer.parseInt(s));
        }
        return nums;
    }
}
