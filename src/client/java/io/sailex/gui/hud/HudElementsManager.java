package io.sailex.gui.hud;

import io.sailex.config.HudElement;
import io.sailex.config.HudEnhancerConfig;
import io.sailex.gui.hud.elements.PositionElement;
import io.sailex.gui.widgets.AWidget;
import io.sailex.gui.widgets.PositionWidget;
import io.sailex.gui.hud.elements.CPSElement;
import io.sailex.gui.hud.elements.FPSElement;
import io.sailex.gui.widgets.CPSWidget;
import io.sailex.gui.widgets.FPSWidget;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class HudElementsManager {

    private final Map<String, HudElement> positionMap;
    private static final Map<AWidget, IHudElement> widgetToHudElement = new LinkedHashMap<>();

    public HudElementsManager(Map<String, HudElement> positionMap) {
        this.positionMap = positionMap;
    }

    public void register() {

        HudElement cps = positionMap.get(HudEnhancerConfig.CPS);
        widgetToHudElement.put(new CPSWidget(cps, widgetToHudElement, positionMap), new CPSElement(cps));

        HudElement fps = positionMap.get(HudEnhancerConfig.FPS);
        widgetToHudElement.put(new FPSWidget(fps, widgetToHudElement, positionMap), new FPSElement(fps));

        HudElement posDisplay = positionMap.get(HudEnhancerConfig.POSITION);
        widgetToHudElement.put(new PositionWidget(posDisplay, widgetToHudElement, positionMap), new PositionElement(posDisplay));

        getHudElements().forEach(HudRenderCallback.EVENT::register);

    }

    public List<IHudElement> getHudElements() {
        return new ArrayList<>(widgetToHudElement.values());
    }

    public List<AWidget> getHudWidgets() {
        return new ArrayList<>(widgetToHudElement.keySet());
    }

}
