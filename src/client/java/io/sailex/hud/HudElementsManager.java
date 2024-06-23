package io.sailex.hud;

import io.sailex.hud.elements.CPSDisplay;
import io.sailex.hud.elements.FPSDisplay;
import io.sailex.hud.elements.PositionDisplay;
import io.sailex.hud.util.IHudElement;
import io.sailex.hud.util.MouseInputHandler;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import java.util.ArrayList;
import java.util.List;

public class HudElementsManager {

    private final List<IHudElement> hudElements = new ArrayList<>();

    public void register() {
        hudElements.add(new PositionDisplay(7, 7, 123, 48));
        hudElements.add(new FPSDisplay(250, 7, 50, 17));
        hudElements.add(new CPSDisplay(150 , 7, 50, 17));

        hudElements.forEach(HudRenderCallback.EVENT::register);

        MouseInputHandler mouseInputHandler = new MouseInputHandler(hudElements);
        mouseInputHandler.register();
    }

}
