package io.sailex.hud;

import io.sailex.config.Position;
import io.sailex.config.PositionDisplayConfig;
import io.sailex.hud.elements.CPSElement;
import io.sailex.hud.elements.FPSElement;
import io.sailex.hud.elements.PositionElement;
import io.sailex.util.IHudElement;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HudElementsManager {

    private final Map<String, Position> positionMap;
    private final List<IHudElement> hudElements = new ArrayList<>();

    public HudElementsManager(Map<String, Position> positionMap) {
        this.positionMap = positionMap;
    }

    public void register() {

        Position fps = positionMap.get(PositionDisplayConfig.FPS);
        hudElements.add(new FPSElement(fps.x(), fps.y(), fps.width(), fps.height()));

        Position positionDisplay = positionMap.get(PositionDisplayConfig.POSITION_DISPLAY);
        hudElements.add(new PositionElement(positionDisplay.x(), positionDisplay.y(), positionDisplay.width(), positionDisplay.height()));

        Position cps = positionMap.get(PositionDisplayConfig.CPS);
        hudElements.add(new CPSElement(cps.x(), cps.y(), cps.width(), cps.height()));

        hudElements.forEach(HudRenderCallback.EVENT::register);

    }

}
