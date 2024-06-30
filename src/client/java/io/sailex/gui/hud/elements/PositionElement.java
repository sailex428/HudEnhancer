package io.sailex.gui.hud.elements;

import io.sailex.config.HudElement;
import io.sailex.util.AHudElement;
import io.sailex.util.PositionElementRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public class PositionElement extends AHudElement {

    public PositionElement(HudElement posDisplay) {
        super(posDisplay);
    }

    @Override
    public void drawElement(DrawContext drawContext, ClientPlayerEntity player) {
        PositionElementRenderer.render(
                drawContext, player,
                elementX, elementY, elementWidth, elementHeight,
                color, background, shadow
        );
    }

}