package io.sailex.config;

import java.util.HashMap;
import java.util.Map;

public class DefaultConfig {

    private Map<String, ConfigElement> configElementMap;
    public static String FPS = "FPS";
    public static String CPS = "CPS";
    public static String POSITION = "POSITION";

    public DefaultConfig() {
        this.configElementMap = new HashMap<>();
    }

    public void register() {
        int color = 0xFFFFFF;
        int hue = 0;
        boolean shadow = true;
        boolean background = true;

        configElementMap.put(CPS, new ConfigElement(150 , 7, 50, 17, color, hue, background, shadow, false));
        configElementMap.put(FPS, new ConfigElement(150, 38, 50, 17, color, hue, background, shadow, false));
        configElementMap.put(POSITION, new ConfigElement(7, 7, 123, 48, color, hue, background, shadow, true));
    }

    public Map<String, ConfigElement> getConfigElementMap() {
        return configElementMap;
    }

    public void setPositionMap(Map<String, ConfigElement> configElementMap) {
        this.configElementMap = configElementMap;
    }

}
