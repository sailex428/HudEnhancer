package io.sailex.gui.hud.elements;

import io.sailex.config.ConfigElement;
import io.sailex.gui.hud.AHudElement;
import io.sailex.util.FPSElementRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public class FPSElement extends AHudElement {

    public FPSElement(String key, ConfigElement fps) {
        super(key, fps);
    }

    @Override
    public void drawElement(DrawContext drawContext, ClientPlayerEntity player) {
        FPSElementRenderer.render(
                drawContext,
                elementX, elementY, elementWidth, elementHeight,
                color, background, shadow, isActive
        );
    }
}
