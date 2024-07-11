package io.sailex.gui.hud;

import io.sailex.config.HudElement;
import io.sailex.config.PositionDisplayConfig;
import io.sailex.gui.hud.elements.PositionElement;
import io.sailex.gui.widgets.PositionWidget;
import io.sailex.gui.hud.elements.CPSElement;
import io.sailex.gui.hud.elements.FPSElement;
import io.sailex.gui.widgets.CPSWidget;
import io.sailex.gui.widgets.FPSWidget;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.widget.ClickableWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HudElementsManager {

    private final Map<String, HudElement> positionMap;
    private static final Map<ClickableWidget, IHudElement> widgetToHudElement = new HashMap<>();

    public HudElementsManager(Map<String, HudElement> positionMap) {
        this.positionMap = positionMap;
    }

    public void register() {

        HudElement fps = positionMap.get(PositionDisplayConfig.FPS);
        widgetToHudElement.put(new FPSWidget(fps, widgetToHudElement, positionMap), new FPSElement(fps));

        HudElement posDisplay = positionMap.get(PositionDisplayConfig.POSITION_DISPLAY);
        widgetToHudElement.put(new PositionWidget(posDisplay, widgetToHudElement, positionMap), new PositionElement(posDisplay));

        HudElement cps = positionMap.get(PositionDisplayConfig.CPS);
        widgetToHudElement.put(new CPSWidget(cps, widgetToHudElement, positionMap), new CPSElement(cps));

        getHudElements().forEach(HudRenderCallback.EVENT::register);

    }

    public List<IHudElement> getHudElements() {
        return new ArrayList<>(widgetToHudElement.values());
    }

    public List<ClickableWidget> getHudWidgets() {
        return new ArrayList<>(widgetToHudElement.keySet());
    }

}
