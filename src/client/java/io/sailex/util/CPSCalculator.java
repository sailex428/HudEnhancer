package io.sailex.util;

import java.util.ArrayList;
import java.util.List;

public class CPSCalculator {

    private static final CPSCalculator INSTANCE = new CPSCalculator();
    private static final long TIMES_SECOND = 1000L;

    private long lastUpdateTime = System.currentTimeMillis();
    private final List<Long> clicks = new ArrayList<>();
    private int clicksPerSecond;

    private CPSCalculator() {}

    public void onKeyPress() {
        this.clicks.add(System.currentTimeMillis());
    }

    private void updateCPS() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= 250L) {
            lastUpdateTime = currentTime;
            clicksPerSecond = clicks.size();
        }
        this.clicks.removeIf(click -> (click + TIMES_SECOND < currentTime));
    }

    public int getCPS() {
        updateCPS();
        return clicksPerSecond;
    }

    public static CPSCalculator getInstance() {
        return INSTANCE;
    }
}
