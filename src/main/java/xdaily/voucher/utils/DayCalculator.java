package xdaily.voucher.utils;

public class DayCalculator {
    public static int calculateTargetDay(String type, int number) {
        if (type.equals("week")) {
            return number * 7; // Week 1 = day 7, Week 2 = day 14, etc.
        }
        return number; // Day type returns the exact day number
    }

    public static boolean isWeekDay(int day) {
        return day % 7 == 0; // Returns true for days 7, 14, 21, etc.
    }
}