package io.sailex.gui.hud.elements;

import io.sailex.config.HudElement;
import io.sailex.util.AHudElement;
import io.sailex.util.FPSElementRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public class FPSElement extends AHudElement {

    public FPSElement(HudElement fps) {
        this.elementX = fps.x();
        this.elementY = fps.y();
        this.elementWidth = fps.width();
        this.elementHeight = fps.height();
        this.color = fps.color();
        this.backgroundColor = fps.backgroundColor();
        this.shadow = fps.shadow();
    }

    @Override
    public void drawElement(DrawContext drawContext, ClientPlayerEntity player) {
        FPSElementRenderer.render(
                drawContext,
                elementX, elementY, elementWidth, elementHeight,
                color, backgroundColor, shadow
        );
    }
}
