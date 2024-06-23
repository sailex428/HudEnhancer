package io.sailex.hud.util;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class CPSTracker {

    private long lastClickTime = 0L;
    private long lastUpdateTime = 0L;
    private int cps = 0;
    private int displayCps = 0;

    public void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.mouse.wasLeftButtonClicked()) {
                handleMouseClick();
            }
            updateCPS();
        });
    }

    private void handleMouseClick() {
        long currentTime = System.currentTimeMillis();
        if (lastClickTime == 0L) {
            lastClickTime = currentTime;
        } else {
            cps++;
        }
    }

    private void updateCPS() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime >= 1000L) {
            lastClickTime = currentTime;
            cps = 0;
        }

        if (currentTime - lastUpdateTime >= 2000L) {
            lastUpdateTime = currentTime;
            displayCps = cps;
        }
    }

    public int getCPS() {
        return displayCps;
    }
}
