package io.sailex.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the default configuration settings for the HUD elements.
 *
 * @author sailex
 */
public class DefaultConfig {

    private Map<String, ConfigElement> configElementMap;
    public static final String FPS = "FPS";
    public static final String CPS = "CPS";
    public static final String POSITION = "POSITION";

    /**
     * Constructs a DefaultConfig with an empty configuration element map.
     */
    public DefaultConfig() {
        this.configElementMap = new HashMap<>();
    }

    /**
     * Registers the default configuration elements for the HUD.
     */
    public void register() {
        int color = 0xFFFFFF;
        int hue = 0;
        boolean shadow = true;
        boolean background = true;
        boolean isRainbow = false;

        configElementMap.put(CPS, new ConfigElement(150, 7, 50, 17, color, hue, isRainbow, background, shadow, false));
        configElementMap.put(FPS, new ConfigElement(150, 38, 50, 17, color, hue, isRainbow, background, shadow, false));
        configElementMap.put(POSITION, new ConfigElement(7, 7, 123, 48, color, hue, isRainbow, background, shadow, true));
    }

    /**
     * Returns the map of configuration elements.
     *
     * @return a map containing the configuration elements
     */
    public Map<String, ConfigElement> getConfigElementMap() {
        return configElementMap;
    }

    /**
     * Sets the map of configuration elements.
     *
     * @param configElementMap a map containing the configuration elements to set
     */
    public void setPositionMap(Map<String, ConfigElement> configElementMap) {
        this.configElementMap = configElementMap;
    }
}
