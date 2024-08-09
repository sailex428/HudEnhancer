package io.sailex.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class RainbowTextUtil {

    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final float ANIMATION_SPEED = 0.001F;
    private static float hueOffset = 0.0F;

    private RainbowTextUtil() {}

    public static int getRainbowColor(float hue) {
        return Color.HSBtoRGB(hue % 1.0F, 1.0F, 1.0F);
    }

    public static void drawAnimatedRainbowText(DrawContext context, String text, int x, int y, boolean shadow) {
        int length = text.length();

        hueOffset += ANIMATION_SPEED;
        if (hueOffset > 1.0F) {
            hueOffset -= 1.0F;
        }

        for (int i = 0; i < length; i++) {
            float hue = (hueOffset + (float) i / length) % 1.0F;
            int color = getRainbowColor(hue);

            context.drawText(client.textRenderer, String.valueOf(text.charAt(i)), x, y, color, shadow);
            x += client.textRenderer.getWidth(String.valueOf(text.charAt(i)));
        }
    }

}
