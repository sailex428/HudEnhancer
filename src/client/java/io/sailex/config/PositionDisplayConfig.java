package io.sailex.config;

import java.util.HashMap;
import java.util.Map;

public class PositionDisplayConfig {

    private Map<String, Position> positionMap;
    public static String FPS = "FPS";
    public static String CPS = "CPS";
    public static String POSITION_DISPLAY = "POSITION_DISPLAY";

    public PositionDisplayConfig() {
        this.positionMap = new HashMap<>();
    }

    public void register() {
        positionMap.put(POSITION_DISPLAY, new Position(7, 7, 123, 48));
        positionMap.put(FPS, new Position(250, 7, 50, 17));
        positionMap.put(CPS, new Position(150 , 7, 50, 17));
    }

    public Map<String, Position> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<String, Position> positionMap) {
        this.positionMap = positionMap;
    }

}
