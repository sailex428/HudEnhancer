package io.sailex.gui.hud;

import io.sailex.config.ConfigElement;
import io.sailex.config.DefaultConfig;
import io.sailex.gui.hud.elements.PositionElement;
import io.sailex.gui.hud.elements.CPSElement;
import io.sailex.gui.hud.elements.FPSElement;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Manages and registers HUD elements based on configuration.
 *
 * @author sailex
 */
public class HudElementsManager {

    private final Map<String, ConfigElement> configElementMap;
    private static final List<IHudElement> hudElements = new ArrayList<>();

    /**
     * Constructs a new {@code HudElementsManager} with the specified configuration map.
     *
     * @param configElementMap a map of configuration elements keyed by their names
     */
    public HudElementsManager(Map<String, ConfigElement> configElementMap) {
        this.configElementMap = configElementMap;
    }

    /**
     * Registers HUD elements for rendering.
     */
    public void register() {
        ConfigElement cps = configElementMap.get(DefaultConfig.CPS);
        hudElements.add(new CPSElement(DefaultConfig.CPS, cps));

        ConfigElement fps = configElementMap.get(DefaultConfig.FPS);
        hudElements.add(new FPSElement(DefaultConfig.FPS, fps));

        ConfigElement posDisplay = configElementMap.get(DefaultConfig.POSITION);
        hudElements.add(new PositionElement(DefaultConfig.POSITION, posDisplay));

        getHudElements().forEach(HudRenderCallback.EVENT::register);
    }

    /**
     * Updates configuration elements based on the current HUD elements.
     */
    public void updateConfigElements() {
        hudElements.forEach(hudElement ->
                configElementMap.put(hudElement.getKey(), hudElement.createUpdatedConfigElement())
        );
    }

    /**
     * Gets the list of HUD elements.
     *
     * @return a list of {@link IHudElement} objects
     */
    public List<IHudElement> getHudElements() {
        return hudElements;
    }
}
