package io.sailex.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class CPSElementRenderer {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void render(DrawContext context,
                              int elementX, int elementY, int elementWidth, int elementHeight,
                              int color, boolean background, boolean shadow) {
        context.fill(elementX, elementY, elementX + elementWidth, elementY + elementHeight, background ? 0x80000000 : 0x00FFFFFF);
        context.drawText(client.textRenderer,  CPSCalculator.getInstance().getCPS() + " CPS", elementX + 5, elementY + 5, color, shadow);
    }

}
