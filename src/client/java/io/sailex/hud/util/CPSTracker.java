package io.sailex.hud.util;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class CPSTracker {

    private long lastClickTime = 0L;
    private int cps = 0;

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
        long CPS_UPDATE_INTERVAL = 1000L;
        if (currentTime + lastClickTime >= CPS_UPDATE_INTERVAL) {
            cps = 0;
            lastClickTime = currentTime;
        }
    }

    public int getCPS() {
        return cps;
    }
}
