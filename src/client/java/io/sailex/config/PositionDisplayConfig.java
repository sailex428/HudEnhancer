package io.sailex.config;

import java.util.HashMap;
import java.util.Map;

public class PositionDisplayConfig {

    private Map<String, HudElement> positionMap;
    public static String FPS = "FPS";
    public static String CPS = "CPS";
    public static String POSITION_DISPLAY = "POSITION_DISPLAY";

    public PositionDisplayConfig() {
        this.positionMap = new HashMap<>();
    }

    public void register() {
        int color = 0xFFFFFF;
        int backgroundColor =  0x80000000;
        boolean shadow = true;
        positionMap.put(POSITION_DISPLAY, new HudElement(7, 7, 123, 48, color, backgroundColor, shadow));
        positionMap.put(FPS, new HudElement(250, 7, 50, 17, color, backgroundColor, shadow));
        positionMap.put(CPS, new HudElement(150 , 7, 50, 17, color, backgroundColor, shadow));
    }

    public Map<String, HudElement> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<String, HudElement> positionMap) {
        this.positionMap = positionMap;
    }

}
