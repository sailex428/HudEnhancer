package io.sailex.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;


public class FPSElementRenderer {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void render(DrawContext context,
                              int elementX, int elementY, int elementWidth, int elementHeight,
                              int color, int backgroundColor, boolean shadow) {
        context.fill(elementX, elementY, elementX + elementWidth, elementY + elementHeight, backgroundColor);
        context.drawText(client.textRenderer, client.getCurrentFps() + " FPS", elementX + 5, elementY + 5, color, shadow);
    }

}
