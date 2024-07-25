package io.sailex.gui.hud.elements;

import io.sailex.config.ConfigElement;
import io.sailex.gui.hud.AHudElement;
import io.sailex.util.CPSElementRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public class CPSElement extends AHudElement {

    public CPSElement(String key, ConfigElement cps) {
        super(key, cps);
    }

    @Override
    public void drawElement(DrawContext drawContext, ClientPlayerEntity player) {
        CPSElementRenderer.render(
                drawContext,
                elementX, elementY, elementWidth, elementHeight,
                color, background, shadow, isActive
        );
    }

}
