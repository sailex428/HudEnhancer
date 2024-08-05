package io.sailex.util;

/**
 * Utility class for screen-related calculations.
 *
 * @author sailex
 */
public class ScreenUtil {

    private ScreenUtil() {}

    /**
     * Calculates the X / Y where the screen starts.
     *
     * @param windowLength the length of the mc client window
     * @param maxLength the maximal length of the screen
     * @return X / Y coordinate where the screen starts.
     */
    public static int calculateScreenSize(int windowLength, int maxLength) {
        return windowLength / 2 - maxLength / 2;
    }

}
