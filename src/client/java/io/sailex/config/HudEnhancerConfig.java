package io.sailex.config;

import java.util.HashMap;
import java.util.Map;

public class HudEnhancerConfig {

    private Map<String, HudElement> positionMap;
    public static String FPS = "FPS";
    public static String CPS = "CPS";
    public static String POSITION = "POSITION";

    public HudEnhancerConfig() {
        this.positionMap = new HashMap<>();
    }

    public void register() {
        int color = 0xFFFFFF;
        int hue = 0;
        boolean shadow = true;
        boolean background = true;

        positionMap.put(CPS, new HudElement(150 , 7, 50, 17, color, hue, background, shadow, false));
        positionMap.put(FPS, new HudElement(150, 38, 50, 17, color, hue, background, shadow, false));
        positionMap.put(POSITION, new HudElement(7, 7, 123, 48, color, hue, background, shadow, true));
    }

    public Map<String, HudElement> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<String, HudElement> positionMap) {
        this.positionMap = positionMap;
    }

}
