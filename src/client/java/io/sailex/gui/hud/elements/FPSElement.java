package io.sailex.gui.hud.elements;

import io.sailex.config.ConfigElement;
import io.sailex.config.DefaultConfig;
import io.sailex.gui.hud.AHudElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public class FPSElement extends AHudElement {

    public FPSElement(String key, ConfigElement fps) {
        super(key, fps);
    }

    @Override
    public void drawElement(DrawContext context, ClientPlayerEntity player) {
        context.fill(elementX, elementY,
                elementX + elementWidth, elementY + elementHeight,
                background ? BACKGROUND_GRAY : BACKGROUND_TRANSPARENT
        );
        context.drawText(client.textRenderer, client.getCurrentFps() + " " + DefaultConfig.FPS,
                elementX + 5, elementY + 5,
                color, shadow
        );
    }

}
