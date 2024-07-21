package io.sailex.util;

public class ScreenUtil {

    private ScreenUtil() {}

    public static int calculateScreenSize(int windowLength, int maxLength) {
        return windowLength / 2 - maxLength / 2;
    }

}
