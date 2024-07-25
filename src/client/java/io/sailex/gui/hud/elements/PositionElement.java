package io.sailex.gui.hud.elements;

import io.sailex.config.ConfigElement;
import io.sailex.gui.hud.AHudElement;
import io.sailex.util.PositionElementRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public class PositionElement extends AHudElement {

    public PositionElement(String key, ConfigElement posDisplay) {
        super(key, posDisplay);
    }

    @Override
    public void drawElement(DrawContext drawContext, ClientPlayerEntity player) {
        PositionElementRenderer.render(
                drawContext, player,
                elementX, elementY, elementWidth, elementHeight,
                color, background, shadow, isActive
        );
    }

}