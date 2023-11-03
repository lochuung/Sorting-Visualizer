package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Helper {
    public static List<Integer> generateRandomNumbers(int size) {
        List<Integer> nums = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= size; i++) {
            nums.add(random.nextInt(100) + 1);
        }
        return nums;
    }

    public static List<Integer> parseStringToArray(String str, int size) {
        List<Integer> nums = new ArrayList<>();
        String[] arr = str.split(",");
        for (String s : arr) {
            if (nums.size() == size)
                break;
            nums.add(Integer.parseInt(s));
        }
        if (nums.size() < size) {
            for (int i = nums.size() + 1; i <= size; i++) {
                nums.add(0);
            }
        }
        return nums;
    }
}
