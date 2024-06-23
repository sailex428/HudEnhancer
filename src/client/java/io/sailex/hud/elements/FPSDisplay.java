package io.sailex.hud.elements;

import io.sailex.hud.util.AHudElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public class FPSDisplay extends AHudElement {

    public FPSDisplay(int elementX, int elementY, int elementWidth, int elementHeight) {
        this.elementX = elementX;
        this.elementY = elementY;
        this.elementWidth = elementWidth;
        this.elementHeight = elementHeight;
    }

    @Override
    public void drawElement(DrawContext drawContext, ClientPlayerEntity player) {
        int textColor = 0xFFFFFF;

        drawContext.fill(elementX, elementY, elementX + elementWidth, elementY + elementHeight, 0x80000000);
        drawContext.drawText(client.textRenderer, client.getCurrentFps() + " FPS", elementX + 5, elementY + 5, textColor, true);
    }
}
