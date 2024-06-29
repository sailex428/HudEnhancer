package io.sailex.gui.hud.elements;

import io.sailex.config.HudElement;
import io.sailex.util.AHudElement;
import io.sailex.util.PositionElementRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public class PositionElement extends AHudElement {

    public PositionElement(HudElement posDisplay) {
        this.elementX = posDisplay.x();
        this.elementY = posDisplay.y();
        this.elementWidth = posDisplay.width();
        this.elementHeight = posDisplay.height();
        this.color = posDisplay.color();
        this.backgroundColor = posDisplay.backgroundColor();
        this.shadow = posDisplay.shadow();
    }

    @Override
    public void drawElement(DrawContext drawContext, ClientPlayerEntity player) {
        PositionElementRenderer.render(
                drawContext, player,
                elementX, elementY, elementWidth, elementHeight,
                color, backgroundColor, shadow
        );
    }

}