package io.sailex.util;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class CPSCalculator {

    private static final long CPS_UPDATE_INTERVAL = 1000;
    private long lastUpdateTime = System.currentTimeMillis();
    private int cps = 0;
    private int clickCount = 0;

    public CPSCalculator() {
        ClientTickEvents.END_CLIENT_TICK.register((client -> {
            if (client.mouse.wasLeftButtonClicked()) {
                clickCount++;
            }
            updateClicks();
        }));
    }

    private void updateClicks() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - lastUpdateTime;

        if (timeElapsed >= CPS_UPDATE_INTERVAL) {
            cps = calculateCPS();
            lastUpdateTime = currentTime;
            clickCount = 0;
        }
    }

    private int calculateCPS() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - lastUpdateTime;
        if (timeElapsed > 0) {
            double cps = ((double) clickCount / (timeElapsed / 1000.0));
            return (int) cps;
        } else {
            return 0;
        }
    }

    public int getCPS() {
        return cps;
    }
}
